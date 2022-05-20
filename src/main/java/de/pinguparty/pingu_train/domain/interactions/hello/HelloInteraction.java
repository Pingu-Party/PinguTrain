package de.pinguparty.pingu_train.domain.interactions.hello;

import de.pinguparty.pingu_train.PinguTrainBot;
import de.pinguparty.pingu_train.domain.interactions.Interaction;

public class HelloInteraction implements Interaction {
    @Override
    public String getName() {
        return "Hello Test";
    }

    @Override
    public String getDescription() {
        return "Nur ein kleiner Test";
    }

    @Override
    public String getStartCommand() {
        return "hello";
    }

    @Override
    public void execute(PinguTrainBot pinguTrainBot) {

    }
}
