package de.pinguparty.pingutrain.worker.commands;

import de.pinguparty.pingutrain.bot.actions.BotAction;
import de.pinguparty.pingutrain.bot.util.JSONConverter;
import org.json.JSONObject;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BotCommander {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue botActionsQueue;

    public void issueBotAction(BotAction botAction) {
        //Sanity check
        if (botAction == null) throw new IllegalArgumentException("Bot action must not be null!");

        //Transform action to JSON
        JSONObject actionJSON = JSONConverter.toJSON(botAction);

        //Insert action into queue
        rabbitTemplate.convertAndSend(botActionsQueue.getName(), actionJSON.toString());
    }
}
