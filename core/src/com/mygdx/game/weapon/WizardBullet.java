package com.mygdx.game.weapon;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class WizardBullet extends EnemyBullet {
    boolean bomb = true, bomb1 = true;
    float delay;

    public WizardBullet(float x, float y, Stage s) {
        super(x, y, s);
        delay = 0;
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        if (!bomb && bomb1)
            applyPhysics(dt);
        if (isAnimationFinished() && bomb) {
            bomb = false;
            setMotionAngle((float) Math.toDegrees(Math.atan2((person.getY() + person.getHeight() / 2) - (getY() + getHeight() / 2), (person.getX() + person.getWidth() / 2) - (getX() + getWidth() / 2))));
            addAction(Actions.delay(1));
            addAction(Actions.after(Actions.fadeOut(0.5f)));
            addAction(Actions.after(Actions.removeActor()));
        }
    }
}
