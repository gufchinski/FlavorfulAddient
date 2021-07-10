package com.mygdx.game.weapon;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class BossBullet extends EnemyBullet {
    public BossBullet(float x, float y, Stage s) {
        super(x, y, s);
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        applyPhysics(dt);
    }
}
