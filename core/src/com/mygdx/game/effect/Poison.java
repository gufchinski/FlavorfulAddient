package com.mygdx.game.effect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.effect.Effect;
import com.mygdx.game.engine.BaseActor;

/**
 * Эффект отравления
 */
public class Poison extends Effect {
    BaseActor baseActor;
    int sitch = 0;

    @Override
    public void play(BaseActor baseActor) {
        baseActor.addAction(Actions.color(Color.GREEN));



        baseActor.hp *= 0.9;
        timeDelay = 1.5f;
        this.baseActor = baseActor;
        sitch = 1 - sitch;
    }

    @Override
    public void effectFinish() {
        baseActor.setColor(Color.WHITE);

    }
}

