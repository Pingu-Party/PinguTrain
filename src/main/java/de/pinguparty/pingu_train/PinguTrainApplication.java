package de.pinguparty.pingu_train;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class PinguTrainApplication {

	public static void main(String[] args) {
		SpringApplication.run(PinguTrainApplication.class, args);
	}

}
