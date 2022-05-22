package de.pinguparty.pingutrain.worker.interactions;

import de.pinguparty.pingutrain.bot.messages.UserMessage;
import de.pinguparty.pingutrain.bot.util.JSONConverter;
import de.pinguparty.pingutrain.worker.commands.BotCommander;
import de.pinguparty.pingutrain.worker.exception.CommandNotFoundException;
import de.pinguparty.pingutrain.worker.exception.InternalException;
import de.pinguparty.pingutrain.worker.exception.UserNotFoundException;
import de.pinguparty.pingutrain.worker.user.UserInteractionManager;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Service for retrieving incoming {@link UserMessage}s from the corresponding queue and dispatching them to the
 * corresponding {@link Interaction}s that are responsible for handling them.
 */
@Service
public class InteractionsDispatcher {
    private static final Set<Class<? extends Interaction>> INTERACTION_CLASSES = Set.of(HelloInteraction.class);
    private static final Map<String, Class<? extends Interaction>> INTERACTION_MAP = new HashMap<>();

    static {
        //Iterate over all Interaction subclasses
        for (Class<? extends Interaction> interactionClass : INTERACTION_CLASSES) {
            //Skip class if interface or abstract
            if (interactionClass.isInterface() || Modifier.isAbstract(interactionClass.getModifiers())) continue;

            //Add class to map of interactions
            try {
                INTERACTION_MAP.put(interactionClass.getDeclaredConstructor().newInstance().getStartCommand().toLowerCase(Locale.ROOT), interactionClass);
            } catch (Exception ignored) {
            }
        }
    }

    @Value("${pingutrain.worker.command.prefix}")
    private String commandPrefix;

    @Autowired
    private BotCommander botCommander;

    @Autowired
    private UserInteractionManager userInteractionManager;

    @RabbitListener(queues = {"${pingutrain.worker.queue.messages}"})
    public void receiveMessage(@Payload String payload) {
        System.out.println(payload);

        //Sanity checks
        if ((payload == null) || payload.isEmpty()) {
            return;
        }

        //Create user message from payload
        UserMessage userMessage = JSONConverter.fromJSON(payload, UserMessage.class);

        //Sanity check
        if (userMessage == null) return;

        //Update user information
        userInteractionManager.updateUserInfo(userMessage.getUserID(), userMessage.getChatID(), userMessage.getFirstName(), userMessage.getLastName());

        //Dispatch the user message
        try {
            dispatchUserMessage(userMessage);
        } catch (CommandNotFoundException e) {
            //TODO handle
            System.err.println(e.getMessage());
        }
    }

    /**
     * Dispatches a given {@link UserMessage} by forwarding it to the {@link Interaction} that is responsible
     * for handling it.
     *
     * @param userMessage The {@link UserMessage} to dispatch
     */
    private void dispatchUserMessage(UserMessage userMessage) throws CommandNotFoundException {
        //Sanity check
        if ((userMessage == null)) return;

        //Affected interaction
        Interaction interaction;

        //Get message text
        String messageText = userMessage.getText().trim();

        //Check whether the message constitutes a command
        if (userMessage.hasText() && messageText.startsWith(commandPrefix)) {
            //Extract command from message text
            String command = getCommand(messageText);

            //Check if command exists
            if (!INTERACTION_MAP.containsKey(command))
                throw new CommandNotFoundException(String.format("The command %s does not exist!", command));

            //Retrieve interaction class
            Class<? extends Interaction> interactionClass = INTERACTION_MAP.get(command);

            //Create instance of the interaction matching the command
            try {
                interaction = interactionClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new InternalException(String.format("Interaction %s appears to be invalid: %s", interactionClass.getSimpleName(), e.getMessage()));
            }

            //Reset stored interaction for the affected user
            resetUserInteraction(userMessage);

            //Let the interaction handle the message
            interaction.handleMessage(botCommander, userInteractionManager, userMessage);
            return;
        }

        /*
        Message does not represent a command, so an existing interaction may want to handle the message
        */
        Optional<Interaction> userInteractionOptional = getCurrentUserInteraction(userMessage);

        //Check if a current user interaction exists
        if (userInteractionOptional.isEmpty()) return;

        //Let the current user interaction handle the message
        userInteractionOptional.get().handleMessage(botCommander, userInteractionManager, userMessage);
    }

    private Optional<Interaction> getCurrentUserInteraction(UserMessage userMessage) {
        //Sanity check
        if (userMessage == null) return Optional.empty();

        //Get current user interaction
        return userInteractionManager.getCurrentUserInteraction(userMessage.getUserID());
    }

    private void resetUserInteraction(UserMessage userMessage) {
        //Sanity check
        if (userMessage == null) return;

        //Reset the interaction
        userInteractionManager.resetCurrentUserInteraction(userMessage.getUserID());
    }

    private String getCommand(String text) {
        return text.replaceAll("\\s+", " ") //Replace multiple spaces
                .split(" ")[0] //Split at spaces to retrieve the first word
                .replaceFirst(this.commandPrefix, "") //Replace trailing prefix
                .toLowerCase(Locale.ROOT); //Convert to lowercase
    }
}
