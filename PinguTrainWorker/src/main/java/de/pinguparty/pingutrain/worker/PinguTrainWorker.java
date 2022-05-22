package de.pinguparty.pingutrain.worker;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class of the application.
 */
@EnableRabbit
@SpringBootApplication
public class PinguTrainWorker {
    public static void main(String[] args) {
        SpringApplication.run(PinguTrainWorker.class, args);
    }
}
