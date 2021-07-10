package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.engine.BaseActor;

/**
 * Главный персонаж
 */
public class Person extends BaseActor {
    public float hp = 10;
    public float maxHp=100;
    public float timeImmortal=0;

    public Person(float x, float y, Stage s) {
        super(x, y, s);

        setSpeed(3.5f);

        loadAnimationFromSheet("person.png",1,8,0.1f,true);
        setScale(1.5f);
        //float[] vertices = {50, 0, getWidth(), 0,getWidth(),getHeight(), 50,getHeight()};
       // setBoundaryRectangle(vertices);



        alignCamera();

    }

    public void act(float dt) {
        super.act(dt);
    }



}
