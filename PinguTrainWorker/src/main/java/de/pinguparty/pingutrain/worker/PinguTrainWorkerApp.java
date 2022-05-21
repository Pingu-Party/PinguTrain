package de.pinguparty.pingutrain.worker;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class PinguTrainWorkerApp {
    public static void main(String[] args) {
        SpringApplication.run(PinguTrainWorkerApp.class, args);
    }
}
