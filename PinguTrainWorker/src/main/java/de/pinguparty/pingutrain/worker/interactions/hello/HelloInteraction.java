package de.pinguparty.pingutrain.worker.interactions.hello;

import de.pinguparty.pingutrain.bot.actions.text.TextMessageAction;
import de.pinguparty.pingutrain.bot.messages.UserMessage;
import de.pinguparty.pingutrain.worker.commands.BotCommander;
import de.pinguparty.pingutrain.worker.exception.UserNotFoundException;
import de.pinguparty.pingutrain.worker.interactions.Interaction;
import de.pinguparty.pingutrain.worker.user.UserInteractionManager;

public class HelloInteraction implements Interaction {

    private boolean isFirstTime = true;

    /**
     * Returns a descriptive name of the interaction.
     *
     * @return The name
     */
    @Override
    public String getName() {
        return "hello";
    }

    /**
     * Returns the description of the interaction.
     *
     * @return The description
     */
    @Override
    public String getDescription() {
        return "Greets you.";
    }

    /**
     * Returns the prefix-less command with which the interaction can be started.
     *
     * @return The start command
     */
    @Override
    public String getStartCommand() {
        return "hello";
    }

    /**
     * Handles incoming user messages by involving a {@link BotCommander} and/or a {@link UserInteractionManager}.
     *
     * @param botCommander           The {@link BotCommander} to use
     * @param userInteractionManager The {@link UserInteractionManager} to use
     * @param userMessage            The {@link UserMessage} to handle
     */
    @Override
    public void handleMessage(BotCommander botCommander, UserInteractionManager userInteractionManager, UserMessage userMessage) {

        if (isFirstTime) {
            isFirstTime = false;
            try {
                userInteractionManager.setCurrentUserInteraction(userMessage.getUserID(), this);
            } catch (UserNotFoundException e) {
                return;
            }

            //Create action for sending a text message
            TextMessageAction textMessageAction = (TextMessageAction) new TextMessageAction()
                    .setText("Howdy, " + userMessage.getFirstName() + ", where are you living?")
                    .setChatID(userMessage.getChatID());
            botCommander.issueBotAction(textMessageAction);
            return;
        }

        //Reset interaction
        userInteractionManager.resetCurrentUserInteraction(userMessage.getUserID());

        //Create action for a sending text message
        TextMessageAction textMessageAction = (TextMessageAction) new TextMessageAction()
                .setText("It's cool in " + userMessage.getText() + "!")
                .setChatID(userMessage.getChatID());
        botCommander.issueBotAction(textMessageAction);
    }
}
