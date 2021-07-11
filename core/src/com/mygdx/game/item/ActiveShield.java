package com.mygdx.game.item;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.engine.BaseActor;

public class ActiveShield extends BaseActor {
    public ActiveShield(float x, float y, Stage s) {
        super(x, y, s);

        loadAnimationFromSheet("Item/actShield.png", 1, 20, 0.05f, true);
        setScale(2);

        addAction(Actions.delay(2.5f));
        addAction(Actions.after(Actions.fadeOut(0.5f)));
        addAction(Actions.after(Actions.removeActor()));

    }

    @Override
    public void act(float dt) {
        super.act(dt);
        centerAtActor(person);
    }
}