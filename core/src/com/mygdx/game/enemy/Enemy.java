package com.mygdx.game.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.engine.BaseActor;
import com.mygdx.game.Person;

/**
 * Абстрактный класс врагов
 */
public  abstract class Enemy extends BaseActor {


    public Person pers;
    public   Vector2 pos;
    public Texture textureDeath;
    public int countDeath;
    public float durationDeath=0.1f;
    boolean death=false;
    float scale=1f,timerEffect=0,timerEffect2=0;

    public Enemy(float x, float y, Stage s,Person personage) {
        super(x, y, s);
        pers = personage;

        setBoundaryRectangle();
        hp = 500;
        dmg=0;
        pos = new Vector2(getX(), getY());

    }

    /**
     * Обновление логики
     * @param dt
     */
    public void act(float dt)
    {

        if(!death) {
            behavior(dt);
        }


        super.act(dt);


    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp)
    {
        this.hp=hp;
    }

    /**
     * Поведение врагов
     * @param dt
     */
    public abstract void behavior(float dt);
    /**
     * Смерть врагов
     */
   @Override
    public  void death()
    {
        loadAnimationFromSheet(textureDeath,1,countDeath,durationDeath,false);
        setScale(scale);
        room.enemyList.remove(this);
        death=true;
    }


}
