package com.mygdx.game.weapon;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class TomataBullet extends EnemyBullet {
    float time = 0, time1 = 1;
    boolean bomb = true;
    boolean ti = false;

    public TomataBullet(float x, float y, Stage s) {
        super(x, y, s);
    }

    @Override
    public void act(float dt) {
        time += dt;
        super.act(dt);
        if (!bomb)
            applyPhysics(dt);
        if (isAnimationFinished() && bomb) {
            bomb = false;

            addAction(Actions.delay(1));
            addAction(Actions.after(Actions.fadeOut(0.5f)));
            addAction(Actions.after(Actions.removeActor()));
        }

    }
}
