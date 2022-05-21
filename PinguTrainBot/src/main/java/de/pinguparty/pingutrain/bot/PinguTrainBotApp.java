package de.pinguparty.pingutrain.bot;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class PinguTrainBotApp {

	public static void main(String[] args) {
		SpringApplication.run(PinguTrainBotApp.class, args);
	}

}
