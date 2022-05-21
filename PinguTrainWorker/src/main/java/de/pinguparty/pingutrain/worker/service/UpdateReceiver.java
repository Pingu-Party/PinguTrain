package de.pinguparty.pingutrain.worker.service;

import de.pinguparty.pingutrain.bot.domain.ReceivedMessage;
import de.pinguparty.pingutrain.worker.util.JSONConverter;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class UpdateReceiver {
    @Autowired
    private Queue updatesQueue;

    public UpdateReceiver() {

    }

    @RabbitListener(queues = {"${pingutrain.worker.queue.updates}"})
    public void receiveUpdate(@Payload String payload) {
        System.out.println("Message " + payload);

        //Sanity checks
        if ((payload == null) || payload.isEmpty()) {
            return;
        }

        //Create update message object from payload
        ReceivedMessage updateMessage = JSONConverter.fromJSON(payload, ReceivedMessage.class);

        //Sanity check
        if (updateMessage == null) return;
    }
}
