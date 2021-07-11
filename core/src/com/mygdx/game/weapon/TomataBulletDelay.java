package com.mygdx.game.weapon;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class TomataBulletDelay extends WizardBullet {
    float timer = 0, delay;


    public TomataBulletDelay(float x, float y, Stage s, float delay) {
        super(x, y, s);
        this.delay = delay;
        bomb1 = false;
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        if (isAnimationFinished())
            timer += dt;
        if (timer >= delay) {
            bomb1 = true;
        }
    }
}
