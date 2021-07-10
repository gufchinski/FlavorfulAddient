package com.mygdx.game.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.engine.Name;

import java.util.Random;

public class Peperonka extends Enemy {

    Random random;
    boolean boom = false;
    boolean pov = false;
    float timepov = 0, timepov1 = 1;
    Polygon polygon;

    public Peperonka(float x, float y, Stage s) {
        super(x, y, s);
        setHp(400f);
        random = new Random();
        setSpeed(500);
        setMaxSpeed(500);
        setDeceleration(0);

        loadAnimationFromSheet("enemy/pizza/peperonkaSpawn.png", 1, 10, 0.1f, false);
        setOrigin(getWidth() / 2, getHeight() / 2);
        setBoundaryRectangle();
        polygon = getBoundaryPolygon();
        textureDeath = new Texture("enemy/pizza/peperonkaDeath.png");
        countDeath = 5;
        setDmg(10);
        scale = 1.5f;
        setScale(1.5f);
    }

    public void act(float dt) {
        super.act(dt);
        if (!death&&pov)
            applyPhysics(dt);

    }

    @Override
    public void behavior(float dt) {

        if(!pov&&isAnimationFinished())
        {
            loadAnimationFromSheet("enemy/pizza/peperonkaRun.png", 1, 4, 0.05f, true);
            setScale(1.5f);
            pov=true;
        }
        setMotionAngle((float) Math.toDegrees(Math.atan2((person.getY() + person.getHeight() / 2) - (getY() + getHeight() / 2), (person.getX() + person.getWidth() / 2) - (getX() + getWidth() / 2))));

        if (person.getX() < getX())
            isRight = true;
        else {
            isRight = false;
        }


    }

}
