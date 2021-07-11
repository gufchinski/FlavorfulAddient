package com.mygdx.game.weapon;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class AutoBullet extends EnemyBullet {
    float time = 0, time1 = 1;
    boolean ti = false;

    public AutoBullet(float x, float y, Stage s) {
        super(x, y, s);

    }

    @Override
    public void act(float dt) {
        time += dt;
        super.act(dt);
        applyPhysics(dt);
        setMotionAngle((float) Math.toDegrees(Math.atan2((person.getY() + person.getHeight() / 2) - (getY() + getHeight() / 2), (person.getX() + person.getWidth() / 2) - (getX() + getWidth() / 2))));
        if (time >= time1 && !ti) {
            setSpeed(600);
            addAction(Actions.delay(3));
            addAction(Actions.after(Actions.fadeOut(0.5f)));
            addAction(Actions.after(Actions.removeActor()));
            ti = true;
        }


    }
}
