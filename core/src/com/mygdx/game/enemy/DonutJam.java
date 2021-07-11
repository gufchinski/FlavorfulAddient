package com.mygdx.game.enemy;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.engine.BaseActor;
import com.mygdx.game.engine.BaseScreen;

public class DonutJam extends BaseActor {
    public DonutJam(float x, float y, Stage s) {
        super(x, y, BaseScreen.backBackgrondStage);

        loadTexture("enemy/donut/donutJam.png");
        //setScale(2);
        moveBy(-getWidth() / 2, 0);
        addAction(Actions.delay(2));
        addAction(Actions.after(Actions.fadeOut(1.5f)));
        addAction(Actions.after(Actions.removeActor()));
        dmg = 30;

    }
}
