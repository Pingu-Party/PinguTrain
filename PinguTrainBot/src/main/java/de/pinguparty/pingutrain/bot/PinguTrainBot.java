package de.pinguparty.pingutrain.bot;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class of the application.
 */
@EnableRabbit
@SpringBootApplication
public class PinguTrainBot {

    public static void main(String[] args) {
        SpringApplication.run(PinguTrainBot.class, args);
    }

}
