package com.mygdx.game.weapon;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.engine.BaseActor;

/**
 * Пули врагов
 */
public class EnemyBullet extends BaseActor
{

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
        addAction( Actions.delay(1) );
        addAction( Actions.after( Actions.fadeOut(0.5f) ) );
        addAction( Actions.after( Actions.removeActor() ) );
    }
     

    public float getDmg() {
        return dmg;
    }
}