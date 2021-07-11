package com.mygdx.game.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Person;
import com.mygdx.game.enemy.Enemy;

import java.util.Random;

/**
 * Враг слайм желе
 */
public class Slime extends Enemy {

    Random random;
    float time = 0;
    float time1 = 0;
    float k;

    public Slime(float x, float y, Stage s) {
        super(x, y, s);
        setHp(600f);
        setOrigin(getX() + getWidth() / 2, getY() + getHeight() / 2);
        setSpeed(600);
        setMaxSpeed(600);
        setDeceleration(0);
        random = new Random();
        loadAnimationFromSheet("enemy/slime/slimeRun.png", 1, 15, 0.15f, true);

        setBoundaryRectangle();

        textureDeath = new Texture("enemy/slime/slimeDeath.png");
        countDeath = 6;
        setDmg(15f);


    }

    public void act(float dt) {
        super.act(dt);
        if (!death)
            applyPhysics(dt);
    }

    @Override
    public void behavior(float dt) {
        time += dt;
        time1 += dt;
        float rast;

        if (time >= 1.5f) {
            if (person.isWithinDistance(Gdx.graphics.getHeight() / 2, this))
                rast = (float) ((float) random.nextInt(10) + Math.toDegrees(Math.atan2((person.getY() + person.getHeight() / 2) - (getY() + getHeight() / 2), (person.getX() + person.getWidth() / 2) - (getX() + getWidth() / 2))));
            else
                rast = random.nextInt(360);
            setMotionAngle(rast);
            time = 0f;
        }
        time1 = 0f;

    }


}

