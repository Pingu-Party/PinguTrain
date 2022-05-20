package de.pinguparty.pingu_train.domain.interactions;

import de.pinguparty.pingu_train.PinguTrainBot;

public interface Interaction {
    public String getName();

    public String getDescription();

    public String getStartCommand();

    public void execute(PinguTrainBot pinguTrainBot);
}
