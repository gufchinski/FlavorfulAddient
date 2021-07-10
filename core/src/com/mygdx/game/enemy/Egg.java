package com.mygdx.game.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Person;
import com.mygdx.game.engine.Name;

import java.util.Random;

/**
 * Враг живое яйцо
 */
public class Egg extends Enemy {

    Random random;
    boolean boom = false;
    Polygon polygon;

    public Egg(float x, float y, Stage s) {
        super(x, y, s);
       setHp(400f);
        random = new Random();
        setSpeed(500);
        setMaxSpeed(500);
        setDeceleration(0);
        scale = 3f;
        loadAnimationFromSheet("enemy/egg/eggRun.png", 1, 4, 0.05f, true);
        setScale(scale);
        setOrigin(getWidth() / 2, getHeight() / 2);
        setBoundaryRectangle();
        polygon=getBoundaryPolygon();
        textureDeath=new Texture("enemy/egg/eggDeath.png");
        countDeath=7;
        setDmg(0);
        name=Name.BOOM;
    }

    public void act(float dt) {
        super.act(dt);
        if(!death)
        applyPhysics(dt);

    }

    @Override
    public void behavior(float dt) {

        setMotionAngle((float) Math.toDegrees(Math.atan2((person.getY() + person.getHeight() / 2) - (getY() + getHeight() / 2), (person.getX() + person.getWidth() / 2) - (getX() + getWidth() / 2))));

        if (person.getX() < getX())
            isRight = true;
        else {
            isRight = false;
        }
        if (isWithinDistance(10, person) && !boom) {
            person.hp-= 25f;
            person.isImmortal = true;
            person.timeImmortal = 0;
            isImmortal=true;
            loadAnimationFromSheet("enemy/egg/eggExplodet.png", 1, 14, 0.05f, false);
            setScale(scale);
            setBoundaryRectangle(polygon);
            boom = true;
        }
        if (boom && isAnimationFinished()) {
            room.enemyList.remove(this);
            remove();
        }


    }

}

