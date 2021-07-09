package com.mygdx.game.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Person;
import com.mygdx.game.engine.Name;

import java.util.Random;

public class Donut extends Enemy {

    Random random;
    boolean stoy=false,stay1=false;
    float time =0;
    float time1 =0.15f;
    float k;
    Texture textureStay;


    public Donut(float x, float y, Stage s) {
        super(x, y, s);
        setHp(700);
        setOrigin(getX() + getWidth()/2,getY()+getHeight()/2);
        setSpeed(350);
        setMaxSpeed(350);
        setDeceleration(0);
        random=new Random();
        loadAnimationFromSheet("enemy/donutRun.png",1,4,0.10f,true);

        setBoundaryRectangle();
        textureStay =new Texture("enemy/donutIdle.png");
        textureDeath=new Texture("enemy/donutDeath.png");
        countDeath=9;
        setDmg(0f);
        setScale(1.2f);

    }
    public void act(float dt) {
        super.act(dt);
        if(!death&&!stoy)
            applyPhysics(dt);
    }

    @Override
    public void behavior(float dt) {
        time+=dt;
        float rast;
            if(person.isWithinDistance(Gdx.graphics.getHeight()/3,this)) {
                    stoy=false;

                rast = (float) ((float) random.nextInt(10) + Math.toDegrees(Math.atan2((person.getY() + person.getHeight() / 2) - (getY() + getHeight() / 2), (person.getX() + person.getWidth() / 2) - (getX() + getWidth() / 2))));
                setMotionAngle(rast-180);
            }
            else {
                stoy = true;

            }
        if(time>=time1)
        {
            time=0;
            jamAdd();
        }
        if(getMotionAngle()>90&&getMotionAngle()<=270) {
            isRight = false;
        }
        else
            isRight=true;
        if(stoy==false&&stay1!=stoy)
        {
            loadAnimationFromSheet("enemy/donutRun.png", 1, 4, 0.10f, true);
            setScale(1.2f);
        }
        else if(stoy==true&&stay1!=stoy){
            loadAnimationFromSheet("enemy/donutIdle.png", 1, 12, 0.10f, true);
            setScale(1.2f);
        }
        stay1=stoy;

    }
    private void jamAdd()
    {
        DonutJam dj= new DonutJam(getX()+getWidth()/2,getY(),getStage());
    }


}