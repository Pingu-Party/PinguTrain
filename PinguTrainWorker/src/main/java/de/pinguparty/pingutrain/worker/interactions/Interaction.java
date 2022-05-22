package de.pinguparty.pingutrain.worker.interactions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.pinguparty.pingutrain.bot.messages.UserMessage;
import de.pinguparty.pingutrain.worker.commands.BotCommander;
import de.pinguparty.pingutrain.worker.user.UserInteractionManager;

/**
 * Base interface for all interactions with the bot.
 */
public interface Interaction {
    /**
     * Returns a descriptive name of the interaction.
     *
     * @return The name
     */
    String getName();

    /**
     * Returns the description of the interaction.
     *
     * @return The description
     */
    @JsonIgnore
    String getDescription();

    /**
     * Returns the prefix-less command with which the interaction can be started.
     *
     * @return The start command
     */
    @JsonIgnore
    String getStartCommand();

    /**
     * Returns whether the interaction is stateful, i.e. needs to persist a state during the interaction.
     *
     * @return True, if the interaction is stateful; false otherwise
     */
    @JsonIgnore
    boolean isStateful();

    /**
     * Handles incoming user messages by involving a {@link BotCommander} and/or a {@link UserInteractionManager}.
     *
     * @param botCommander           The {@link BotCommander} to use
     * @param userInteractionManager The {@link UserInteractionManager} to use
     * @param userMessage            The {@link UserMessage} to handle
     */
    @JsonIgnore
    void handleMessage(BotCommander botCommander, UserInteractionManager userInteractionManager, UserMessage userMessage);
}
