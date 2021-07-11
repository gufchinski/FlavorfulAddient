package com.mygdx.game.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.engine.BaseActor;
import com.mygdx.game.Person;
import com.mygdx.game.engine.BaseScreen;
import com.mygdx.game.engine.Name;

/**
 * Абстрактный класс врагов
 */
public abstract class Enemy extends BaseActor {


    public Vector2 pos;
    public Texture textureDeath;
    public int countDeath;
    public float durationDeath = 0.1f;
    boolean death = false;
    float scale, timerEffect = 0, timerEffect2 = 0;


    public Enemy(float x, float y, Stage s) {
        super(x, y, s);

        scale = 1f;
        setBoundaryRectangle();
        hp = 500;
        dmg = 0;
        pos = new Vector2(getX(), getY());

    }

    /**
     * Обновление логики
     *
     * @param dt
     */
    public void act(float dt) {

        if (!death) {
            behavior(dt);
        }


        super.act(dt);


    }

    public float getHp() {
        return hp;
    }


    /**
     * Поведение врагов
     *
     * @param dt
     */
    public abstract void behavior(float dt);

    /**
     * Смерть врагов
     */
    @Override
    public void death() {
        loadAnimationFromSheet(textureDeath, 1, countDeath, durationDeath, false);
        if(this.name== Name.BOSS) {
            moveBy(-64, -54);
        }
        setScale(scale);
        room.enemyList.remove(this);
        death = true;
    }

    protected void setHp(float hp) {
        this.hp = hp * BaseScreen.complexity * 1.2f;
    }

    protected void setDmg(float damage) {
        dmg = damage * BaseScreen.complexity * 1.1f;
    }


}
