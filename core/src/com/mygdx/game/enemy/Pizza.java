package com.mygdx.game.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.HealthBar;
import com.mygdx.game.engine.BaseScreen;
import com.mygdx.game.engine.Name;
import com.mygdx.game.weapon.BossBullet;
import com.mygdx.game.weapon.EnemyBullet;

import java.util.Random;

import static com.mygdx.game.engine.BaseScreen.frontStage;
import static com.mygdx.game.engine.BaseScreen.uiStage;

public class Pizza extends Enemy {

    Random random;
    boolean spawn = false;
    boolean stoy = false, stay1 = false;
    float time = 0;
    float pipertime = 0, pipertime1 = 15;
    float tomatotime = 0, tomatotime1 = 2;
    float secondtomatotime = 0, secondtomatotime1 = 5;
    int tomatocount = 0, tomatocount1 = 5;
    float time1 = 0.15f;
    float tomatotimedelay = 0, tomatimedelay1 = 0.1f, tomatotimeattack = 0.5f;
    float k;
    Texture textureStay;
    HealthBar pizzaHB;
    Table pizzaTable;
    boolean deadTorch = false;
    Texture tomato;
    float rast;
    boolean rigg;

    public Pizza(float x, float y, Stage s) {
        super(x, y, s);
        setHp(700);
        setOrigin(getX() + getWidth() / 2, getY() + getHeight() / 2);
        setSpeed(350);
        setMaxSpeed(350);
        setDeceleration(0);
        random = new Random();
        loadAnimationFromSheet("enemy/pizza/pizzaRun.png", 1, 7, 0.10f, true);

        setBoundaryRectangle();
        textureDeath = new Texture("enemy/pizza/pizzaDeath.png");
        countDeath = 26;
        setDmg(10f);
        scale = 0.9f;
        setScale(0.9f);
        pizzaHB = new HealthBar(700, 60);
        pizzaHB.setValue(getHp());
        pizzaHB.setRange(0, getHp());
        uiStage.addActor(pizzaHB);
        pizzaTable = new Table();
        pizzaTable.setFillParent(true);
        Label pizzalabel = new Label(" Pizzaa", BaseScreen.bosslb);
        uiStage.addActor(pizzaTable);
        uiStage.addActor(pizzalabel);
        pizzaTable.add(pizzalabel).row();
        pizzaTable.add(pizzaHB).size(800, 60);
        pizzaTable.top().padTop(100f);
        name = Name.BOSS;
        tomato = new Texture("enemy/pizza/tomato.png");

    }

    public void act(float dt) {
        super.act(dt);
        if (!death && !stoy)
            applyPhysics(dt);
        pizzaHB.setValue(getHp());
        if (death && !deadTorch) {
            deadTorch = true;
            pizzaTable.remove();
        }
    }

    @Override
    public void behavior(float dt) {
        time += dt;
        pipertime += dt;
        tomatotime += dt;
        secondtomatotime+=dt;


        rast = (float) ((float) random.nextInt(10) + Math.toDegrees(Math.atan2((person.getY() + person.getHeight() / 2) - (getY() + getHeight() / 2), (person.getX() + person.getWidth() / 2) - (getX() + getWidth() / 2))));
        if (person.isWithinDistance(Gdx.graphics.getHeight() / 3, this)) {


            setMotionAngle(rast - 180);
        } else {
            stoy = true;

        }
        if (getMotionAngle() > 90 && getMotionAngle() <= 270) {
            isRight = true;
        } else
            isRight = false;
        //томат аттака 1
//        if(tomatotime>=tomatotime1)
//        {
//            tomatotimedelay+=dt;
//            if(tomatocount==0)
//                rigg=isRight;
//
//        }
//        if(tomatotimedelay>=tomatimedelay1)
//        {
//            firsttomatoAttck(rigg);
//            tomatocount++;
//            tomatotimedelay=0;
//        }
//        if(tomatocount==tomatocount1)
//        {
//            tomatocount=0;
//            tomatotime=0;
//
//        }

        //Спавн пиперони
//        if (pipertime >= pipertime1) {
//            spawn = true;
//            loadAnimationFromSheet("enemy/pizza/pizzaSpawnAttack.png", 1, 13, 0.10f, false);
//            setScale(0.9f);
//            if (isRight)
//                moveBy(-48, 0);
//            else
//                moveBy(-48, 0);
//
//
//            pipertime = 0;
//
//        }
//        if (spawn && isAnimationFinished()) {
//            spawn = false;
//            loadAnimationFromSheet("enemy/pizza/pizzaRun.png", 1, 7, 0.10f, true);
//            setScale(0.9f);
//            if (isRight)
//                moveBy(48, 0);
//            else
//                moveBy(48, 0);
//            addPiper();
//        }

        //томат аттака 2
        if(secondtomatotime>=secondtomatotime1)
        {
            secondTomatoAttck();
            secondtomatotime=0;
        }



    }

    private void addPiper() {
        Peperonka eps = new Peperonka(getX(), getY(), getStage());
        eps.moveBy(-eps.getWidth(), 0);
        room.enemyList.add(eps);
        eps = new Peperonka(getX() + getWidth(), getY(), getStage());
        eps.moveBy(eps.getWidth(), 0);
        room.enemyList.add(eps);

    }

    private void tomatoAttack() {
        //firsttomatoAttck();
    }

    private void firstTomatoAttck(boolean rig) {
        EnemyBullet bullet = new EnemyBullet(0, 0, frontStage);
        bullet.loadAnimationFromSheet(tomato, 1, 1, 0.1f, false);
        bullet.centerAtActor(this);
        if (rig)
            bullet.moveBy(250, 0);
        else
            bullet.moveBy(-250, 0);
        bullet.setSpeed(1500);
        bullet.setMotionAngle(rast);
    }

    private void secondTomatoAttck() {
        EnemyBullet bullet = new BossBullet(0, 0, frontStage);
        bullet.loadAnimationFromSheet(tomato, 1, 1, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(250, 220);
        bullet.setSpeed(900);
        bullet.setMotionAngle(0);
        bullet = new BossBullet(0, 0, frontStage);
        bullet.loadAnimationFromSheet(tomato, 1, 1, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(-250, 220);
        bullet.setSpeed(900);
        bullet.setMotionAngle(180);


        bullet = new BossBullet(0, 0, frontStage);
        bullet.loadAnimationFromSheet(tomato, 1, 1, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(250, 110);
        bullet.setSpeed(900);
        bullet.setMotionAngle(0);

        bullet = new BossBullet(0, 0, frontStage);
        bullet.loadAnimationFromSheet(tomato, 1, 1, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(-250, 110);
        bullet.setSpeed(900);
        bullet.setMotionAngle(180);


        bullet = new BossBullet(0, 0, frontStage);
        bullet.loadAnimationFromSheet(tomato, 1, 1, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(250, 0);
        bullet.setSpeed(900);
        bullet.setMotionAngle(0);

        bullet = new BossBullet(0, 0, frontStage);
        bullet.loadAnimationFromSheet(tomato, 1, 1, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(-250, 0);
        bullet.setSpeed(900);
        bullet.setMotionAngle(180);


        bullet = new BossBullet(0, 0, frontStage);
        bullet.loadAnimationFromSheet(tomato, 1, 1, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(250, -110);
        bullet.setSpeed(900);
        bullet.setMotionAngle(0);

        bullet = new BossBullet(0, 0, frontStage);
        bullet.loadAnimationFromSheet(tomato, 1, 1, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(-250, -110);
        bullet.setSpeed(900);
        bullet.setMotionAngle(180);


        bullet = new BossBullet(0, 0, frontStage);
        bullet.loadAnimationFromSheet(tomato, 1, 1, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(250, -220);
        bullet.setSpeed(900);
        bullet.setMotionAngle(0);

        bullet = new BossBullet(0, 0, frontStage);
        bullet.loadAnimationFromSheet(tomato, 1, 1, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(-250, -220);
        bullet.setSpeed(900);
        bullet.setMotionAngle(180);


    }
}