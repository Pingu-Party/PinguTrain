package de.pinguparty.pingutrain.worker.service;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UpdateReceiver {
    @Autowired
    private Queue updatesQueue;

    public UpdateReceiver() {

    }

    @RabbitListener(queues = {"${pingutrain.worker.queue.updates}"})
    public void receiveUpdate(@Payload String payload) {
        //Sanity checks
        if ((payload == null) || payload.isEmpty()) {
            return;
        }



        System.out.println("Message " + payload);
    }
}
