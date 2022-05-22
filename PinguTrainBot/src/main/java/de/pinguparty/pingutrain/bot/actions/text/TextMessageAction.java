package de.pinguparty.pingutrain.bot.actions.text;

import de.pinguparty.pingutrain.bot.actions.BotAction;
import de.pinguparty.pingutrain.bot.telegram.TelegramBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * A {@link BotAction} that allows to send a text message to a certain chat, given by its chat ID.
 */
public class TextMessageAction extends BotAction {

    private String text = "";
    private boolean notify = true;
    private boolean isHTML = false;

    /**
     * Returns the type name of the {@link BotAction}.
     *
     * @return The type name
     */
    @Override
    public String getTypeName() {
        return "TextMessage";
    }

    /**
     * Executes the action on a given {@link TelegramBot}.
     *
     * @param bot The {@link TelegramBot} on which the action is supposed to be performed.
     */
    @Override
    public void execute(TelegramBot bot) {
        //Sanity check
        if (bot == null) return;

        //Create and configure object for sending a text message
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(Long.toString(this.chatID));
        sendMessage.setText(this.text);
        sendMessage.setDisableNotification(!notify);
        sendMessage.enableHtml(this.isHTML);

        //Execute the action
        bot.executeActionSafely(sendMessage);
    }

    /**
     * Returns the text that is supposed to be sent.
     *
     * @return The text to send
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text that is supposed to be sent.
     *
     * @param text The text to send
     * @return The {@link TextMessageAction}
     */
    public TextMessageAction setText(String text) {
        this.text = text;
        return this;
    }

    /**
     * Returns whether the receiver of the text is supposed to be notified.
     *
     * @return True, if the receiver is notified; false otherwise
     */
    public boolean isNotify() {
        return notify;
    }

    /**
     * Sets whether the receiver of the text is supposed to be notified.
     *
     * @param notify True, if the receiver should be notified; false otherwise
     * @return The {@link TextMessageAction}
     */
    public TextMessageAction setNotify(boolean notify) {
        this.notify = notify;
        return this;
    }

    /**
     * Returns whether HTML is supported within the given text.
     *
     * @return True, if HTML is supported; false otherwise
     */
    public boolean isHTML() {
        return isHTML;
    }

    /**
     * Sets hether HTML is supported within the given text.
     *
     * @param HTML True, if HTML should be supported; false otherwise
     * @return THe {@link TextMessageAction}
     */
    public TextMessageAction setHTML(boolean HTML) {
        isHTML = HTML;
        return this;
    }
}
