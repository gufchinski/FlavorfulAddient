package com.mygdx.game.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.enemy.Enemy;
import com.mygdx.game.engine.BaseActor;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Пули от персонажа
 */
public class Bullet extends BaseActor {

    int type;

    public Bullet(float x, float y, Stage s, String textureBullet, float damage) {
        super(x, y, s);


        dmg = damage;

        loadTexture(textureBullet);

        addAction(Actions.delay(1));
        addAction(Actions.after(Actions.fadeOut(0.5f)));
        addAction(Actions.after(Actions.removeActor()));
        setScale(2);
        setSpeed(400);
        setMaxSpeed(400);
        setDeceleration(0);
    }


    public void act(float dt) {
        super.act(dt);
        applyPhysics(dt);
    }

}