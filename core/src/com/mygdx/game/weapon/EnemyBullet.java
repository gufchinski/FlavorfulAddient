package com.mygdx.game.weapon;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.engine.BaseActor;

/**
 * Пули врагов
 */
public class EnemyBullet extends BaseActor
{
    boolean bomb=true;
    public EnemyBullet(float x, float y, Stage s)
    {
        super(x,y,s);

        dmg=20;


        setSpeed(400);
        setMaxSpeed(10000);
        setDeceleration(0);
    }

    public void act(float dt)
    {
        super.act(dt);
        if(!bomb)
        applyPhysics(dt);
        if(isAnimationFinished()&&bomb)
        {
            bomb=false;
            setMotionAngle((float) Math.toDegrees(Math.atan2((person.getY() + person.getHeight() / 2) - (getY() +getHeight() / 2), (person.getX() + person.getWidth() / 2) - (getX() + getWidth() / 2))));
            addAction( Actions.delay(1) );
            addAction( Actions.after( Actions.fadeOut(0.5f) ) );
            addAction( Actions.after( Actions.removeActor() ) );
        }
    }
     

    public float getDmg() {
        return dmg;
    }
}