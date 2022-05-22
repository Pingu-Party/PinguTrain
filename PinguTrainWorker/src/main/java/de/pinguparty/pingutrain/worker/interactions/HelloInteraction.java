package de.pinguparty.pingutrain.worker.interactions;

import de.pinguparty.pingutrain.bot.actions.send_text.SendTextAction;
import de.pinguparty.pingutrain.bot.messages.UserMessage;
import de.pinguparty.pingutrain.worker.commands.BotCommander;
import de.pinguparty.pingutrain.worker.exception.UserNotFoundException;
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
     * Returns whether the interaction is stateful, i.e. needs to persist a state during the interaction.
     *
     * @return True, if the interaction is stateful; false otherwise
     */
    @Override
    public boolean isStateful() {
        return false;
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

            //Create action for sending text
            SendTextAction sendTextAction = (SendTextAction) new SendTextAction()
                    .setText("Howdy, " + userMessage.getFirstName() + ", where are you living?")
                    .setChatID(userMessage.getChatID());
            botCommander.issueBotAction(sendTextAction);
            return;
        }

        //Reset interaction
        userInteractionManager.resetCurrentUserInteraction(userMessage.getUserID());

        //Create action for sending text
        SendTextAction sendTextAction = (SendTextAction) new SendTextAction()
                .setText("It's cool in " + userMessage.getText() + "!")
                .setChatID(userMessage.getChatID());
        botCommander.issueBotAction(sendTextAction);
    }
}
