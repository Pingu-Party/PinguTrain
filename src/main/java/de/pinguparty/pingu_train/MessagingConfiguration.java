package de.pinguparty.pingu_train;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfiguration {
    @Bean
    public Queue queue(){
        return new Queue("queue", false);
    }
}
