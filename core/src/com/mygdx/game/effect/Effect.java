package com.mygdx.game.effect;

import com.mygdx.game.engine.BaseActor;

public abstract class Effect {
    public float timePlay = 6.1f, timeDelay = 1.5f;

    public abstract void play(BaseActor baseActor);
    public abstract void effectFinish();
}
