package de.pinguparty.pingu_train.service;

import de.pinguparty.pingu_train.PinguTrainBot;
import de.pinguparty.pingu_train.domain.interactions.Interaction;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class MessageDispatcher {

    private static final String INTERACTIONS_PACKAGE = "de.pinguparty.pingu_train.domain.interactions";
    private static final Map<String, Class<? extends Interaction>> INTERACTION_TYPES = new HashMap<>();

    @Value("telegram.command.prefix")
    private String commandPrefix;

    static {
        Reflections reflections = new Reflections(INTERACTIONS_PACKAGE);
        Set<Class<? extends Interaction>> interactionClasses = reflections.getSubTypesOf(Interaction.class);
        for(Class<? extends Interaction> interactionClass : interactionClasses) {
            if(interactionClass.isInterface()) continue;

            try{
                INTERACTION_TYPES.put(interactionClass.getDeclaredConstructor().newInstance().getStartCommand(), interactionClass);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void dispatch(PinguTrainBot pinguTrainBot, Message message) {
        if(message == null) {
            return;
        }

        String messageText = message.getText();

        if(messageText.startsWith(commandPrefix)) {
            if(!INTERACTION_TYPES.containsKey(messageText)) {
                //TODO Needs to look whether it is an answer
                return;
            }

            try {
                Interaction interaction = INTERACTION_TYPES.get(messageText).getDeclaredConstructor().newInstance();
                interaction.execute(pinguTrainBot);
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
