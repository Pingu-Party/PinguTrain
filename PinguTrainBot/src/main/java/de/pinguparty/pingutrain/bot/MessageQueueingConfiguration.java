package de.pinguparty.pingutrain.bot;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for message queueing.
 */
@Configuration
public class MessageQueueingConfiguration {

    @Value("${pingutrain.bot.queue.updates}")
    private String updatesQueueName;

    @Value("${pingutrain.bot.queue.actions}")
    private String actionsQueueName;

    /**
     * Creates a {@link Queue} for outgoing user updates that were received from the bot.
     *
     * @return The corresponding {@link Queue}
     */
    @Bean
    public Queue updatesQueue() {
        return new Queue(updatesQueueName, false);
    }

    /**
     * Creates a {@link Queue} for incoming actions that are supposed to be executed by the bot.
     *
     * @return The corresponding {@link Queue}
     */
    @Bean
    public Queue actionsQueue() {
        return new Queue(actionsQueueName, false);
    }
}
