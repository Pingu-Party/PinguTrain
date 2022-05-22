package de.pinguparty.pingutrain.bot.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.pinguparty.pingutrain.bot.telegram.TelegramBot;
import de.pinguparty.pingutrain.bot.messages.UserMessage;
import de.pinguparty.pingutrain.bot.actions.BotAction;

/**
 * Configuration for messaging-related aspects.
 */
@Configuration
public class MessagingConfig {

    @Value("${pingutrain.bot.queue.messages}")
    private String userMessagesQueueName;

    @Value("${pingutrain.bot.queue.actions}")
    private String botActionsQueueName;

    /**
     * Creates a {@link Queue} for outgoing {@link UserMessage}s that were received by a {@link TelegramBot}.
     *
     * @return The corresponding {@link Queue}
     */
    @Bean
    public Queue userMessagesQueue() {
        return new Queue(userMessagesQueueName, false);
    }

    /**
     * Creates a {@link Queue} for incoming {@link BotAction}s that are supposed to be executed by a
     * {@link TelegramBot}.
     *
     * @return The corresponding {@link Queue}
     */
    @Bean
    public Queue botActionsQueue() {
        return new Queue(botActionsQueueName, false);
    }
}
