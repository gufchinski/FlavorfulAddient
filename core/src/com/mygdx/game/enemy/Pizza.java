package com.mygdx.game.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.HealthBar;
import com.mygdx.game.engine.BaseScreen;
import com.mygdx.game.engine.Name;
import com.mygdx.game.weapon.AutoBullet;
import com.mygdx.game.weapon.TomataBullet;
import com.mygdx.game.weapon.EnemyBullet;
import com.mygdx.game.weapon.TomataBulletDelay;
import com.mygdx.game.weapon.WizardBullet;

import java.util.ArrayList;
import java.util.Random;

import static com.mygdx.game.engine.BaseScreen.backBackgrondStage;
import static com.mygdx.game.engine.BaseScreen.frontStage;
import static com.mygdx.game.engine.BaseScreen.uiStage;

public class Pizza extends Enemy {

    Random random;
    boolean spawn = false;
    boolean tommatoat1;
    boolean stoy = false;
    boolean attackIsFinished;
    float time = 0;
    float tomatotime = 0;
    float secondtomatotime = 0;
    float tomatotimedelay = 0, tomatimedelay1 = 2.0f;
    float generatedtime = 0;
    Texture textureStay;
    HealthBar pizzaHB;
    Table pizzaTable;
    boolean deadTorch = false;
    Texture tomato;
    float rast;
    int ran;
    ArrayList<EnemyBullet> bul;
    float generatedFloat, delayattack1, supdelayattack1, delayattack2, supdelayattack2;
    boolean isCountFinish, isdelayattack1, isdelayattack2, sup, sup2;
    Texture cucumber;
    boolean spawn1;

    public Pizza(float x, float y, Stage s) {
        super(x, y, s);
        sup = true;
        sup2 = true;
        tommatoat1 = true;
        delayattack1 = 0;
        delayattack2 = 0;
        supdelayattack2 = 2;
        supdelayattack1 = 2;
        isdelayattack1 = false;
        spawn1 = false;

        setHp(5000);
        setOrigin(getX() + getWidth() / 2, getY() + getHeight() / 2);
        setSpeed(250);
        setMaxSpeed(250);
        setDeceleration(0);
        random = new Random();
        ran = random.nextInt(2);
        loadAnimationFromSheet("enemy/pizza/pizzaRun.png", 1, 7, 0.10f, true);
        attackIsFinished = true;
        setBoundaryRectangle();
        textureDeath = new Texture("enemy/pizza/pizzaDeath.png");
        countDeath = 25;
        setDmg(10f);
        scale = 0.9f;
        setScale(0.9f);
        pizzaHB = new HealthBar(700, 60);
        pizzaHB.setValue(getHp());
        pizzaHB.setRange(0, getHp());
        uiStage.addActor(pizzaHB);
        pizzaTable = new Table();
        pizzaTable.setFillParent(true);
        Label pizzalabel = new Label("Pizza", BaseScreen.bosslb);
        uiStage.addActor(pizzaTable);
        uiStage.addActor(pizzalabel);
        pizzaTable.add(pizzalabel).row();
        pizzaTable.add(pizzaHB).size(800, 60);
        pizzaTable.top().padTop(100f);
        name = Name.BOSS;
        tomato = new Texture("enemy/pizza/tomatoSpawn.png");
        cucumber = new Texture("enemy/pizza/pickleSpawn.png");
        bul = new ArrayList<EnemyBullet>();
        stoy = true;
        generatedFloat = 1.5f;
        isCountFinish = false;

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

        tomatotime += dt;

        secondtomatotime += dt;
        generatedtime += dt;


        rast = (float) ((float) random.nextInt(10) + Math.toDegrees(Math.atan2((person.getY() + person.getHeight() / 2) - (getY() + getHeight() / 2), (person.getX() + person.getWidth() / 2) - (getX() + getWidth() / 2))));

        if (generatedtime >= generatedFloat) {
            switch (ran) {
                case 0:
                    setAttack1(dt);
                    break;
                case 1:
                    setAttack2(dt);
                    break;
                case 2:
                    setAttack3(dt);
                    break;
            }
        } else {
            applyPhysics(dt);
        }

        if (attackIsFinished) {
            float leftLimit = 1F;
            float rightLimit = 5F;
            generatedFloat = leftLimit + new Random().nextFloat() * (rightLimit - leftLimit);
            generatedtime = 0;
            setMotionAngle(new Random().nextInt(360));
            if (getMotionAngle() > 90 && getMotionAngle() < 270)
                isRight = false;
            else
                isRight = true;
            ran = random.nextInt(3);
        }
        if (attackIsFinished) {
            attackIsFinished = false;
            secondtomatotime = 0;
        }


    }

    private void setAttack1(float dt) {

        if (!spawn) {
            spawn = true;
            loadAnimationFromSheet("enemy/pizza/pizzaSpawnAttack.png", 1, 13, 0.10f, false);
            setScale(0.9f);
            moveBy(-48, 0);
        }
        if (spawn && isAnimationFinished()) {
            spawn = false;
            loadAnimationFromSheet("enemy/pizza/pizzaRun.png", 1, 7, 0.10f, true);
            setScale(0.9f);
            moveBy(48, 0);
            addPiper();
            attackIsFinished = true;
        }
    }

    private void setAttack3(float dt) {

        if (!spawn) {
            spawn = true;
            loadAnimationFromSheet("enemy/pizza/pizzaPickleSpawn.png", 1, 27, 0.10f, false);
            setScale(0.9f);
            moveBy(-48, 0);
        }
        if (spawn && isAnimationFinished() && !spawn1) {

            spawn1 = true;
            loadAnimationFromSheet("enemy/pizza/pizzaPickleEnd.png", 1, 5, 0.10f, false);
            setScale(0.9f);
            picleAttck();

        }
        if (isAnimationFinished() && spawn1) {
            loadAnimationFromSheet("enemy/pizza/pizzaRun.png", 1, 7, 0.10f, true);
            moveBy(48, 0);
            setScale(0.9f);
            spawn = false;
            spawn1 = false;
            attackIsFinished = true;
        }
    }


    private void setAttack2(float dt) {
        tomatotimedelay += dt;

        if (!spawn) {
            spawn = true;
            loadAnimationFromSheet("enemy/pizza/pizzaTomatoAttack1.png", 1, 6, 0.10f, false);
            setScale(0.9f);
            moveBy(-48, 0);
            for (int i = 0; i < 5; i++)
                firstTomatoAttck(isRight, 0.1f * (i + 1));
        }
        //задержка перед началом 2 атаки
        if (tomatotimedelay >= tomatimedelay1) {
            isCountFinish = true;
            tomatotimedelay = 0;
        }

        if (isCountFinish && isAnimationFinished() && tommatoat1) {
            loadAnimationFromSheet("enemy/pizza/pizzaTomatoAttack2.png", 1, 6, 0.10f, false);
            setScale(0.9f);
            tommatoat1 = false;
        }

        if (!tommatoat1 && isAnimationFinished() && !isdelayattack1 && sup) {
            secondTomatoAttck();
            isdelayattack1 = true;
            sup = false;

        }
        if (isdelayattack1) {
            delayattack1 += dt;

        }

        if (delayattack1 >= supdelayattack1) {
            secondTomatoAttck2();
            isdelayattack1 = false;
            delayattack1 = 0;

            isdelayattack2 = true;
        }
        if (isdelayattack2) {
            delayattack2 += dt;

        }

        if (delayattack2 >= supdelayattack2 && sup2) {
            loadAnimationFromSheet("enemy/pizza/pizzaTomatoAttack3.png", 1, 6, 0.10f, false);
            setScale(0.9f);
            //moveBy(48, 0);
            sup2 = false;

        }
        if (isAnimationFinished() && !sup2) {
            loadAnimationFromSheet("enemy/pizza/pizzaRun.png", 1, 7, 0.10f, true);
            setScale(0.9f);
            moveBy(48, 0);
            spawn = false;
            isCountFinish = false;
            attackIsFinished = true;
            tommatoat1 = true;
            isdelayattack2 = false;
            delayattack2 = 0;
            sup = true;
            sup2 = true;
        }


    }

    private void addPiper() {
        if (room.roomCheck(getY() + 100, getX() - 64, 64, 64)) {
            Peperonka eps = new Peperonka(getX(), getY(), backBackgrondStage);
            eps.moveBy(-eps.getWidth(), 0);
            room.enemyList.add(eps);
        }
        if (room.roomCheck(getY() + 100, getX() + getWidth(), 64, 64)) {
            Peperonka eps = new Peperonka(getX() + getWidth(), getY(), backBackgrondStage);
            room.enemyList.add(eps);
        }
    }

    private void firstTomatoAttck(boolean rig, float delay) {
        EnemyBullet bullet = new TomataBulletDelay(0, 0, getStage(), delay);
        bullet.loadAnimationFromSheet(tomato, 1, 9, 0.1f, false);
        bullet.centerAtActor(this);
        if (!rig)
            bullet.moveBy(250, 50);
        else
            bullet.moveBy(-250, 50);
        bullet.setSpeed(1500);
        bullet.setMotionAngle(rast);
    }

    private void secondTomatoAttck() {

        EnemyBullet bullet = new TomataBullet(0, 0, getStage());
        bullet.loadAnimationFromSheet(tomato, 1, 9, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(250, 260);
        bullet.setSpeed(1200);
        bullet.setMotionAngle(0);

        bullet = new TomataBullet(0, 0, getStage());
        bullet.loadAnimationFromSheet(tomato, 1, 9, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(-250, 260);
        bullet.setSpeed(1200);
        bullet.setMotionAngle(180);


        bullet = new TomataBullet(0, 0, getStage());
        bullet.loadAnimationFromSheet(tomato, 1, 9, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(250, 130);
        bullet.setSpeed(1200);
        bullet.setMotionAngle(0);

        bullet = new TomataBullet(0, 0, getStage());
        bullet.loadAnimationFromSheet(tomato, 1, 9, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(-250, 130);
        bullet.setSpeed(1200);
        bullet.setMotionAngle(180);


        bullet = new TomataBullet(0, 0, getStage());
        bullet.loadAnimationFromSheet(tomato, 1, 9, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(250, 0);
        bullet.setSpeed(1200);
        bullet.setMotionAngle(0);

        bullet = new TomataBullet(0, 0, getStage());
        bullet.loadAnimationFromSheet(tomato, 1, 9, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(-250, 0);
        bullet.setSpeed(1200);
        bullet.setMotionAngle(180);


        bullet = new TomataBullet(0, 0, getStage());
        bullet.loadAnimationFromSheet(tomato, 1, 9, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(250, -130);
        bullet.setSpeed(1200);
        bullet.setMotionAngle(0);

        bullet = new TomataBullet(0, 0, getStage());
        bullet.loadAnimationFromSheet(tomato, 1, 9, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(-250, -130);
        bullet.setSpeed(1200);
        bullet.setMotionAngle(180);


        bullet = new TomataBullet(0, 0, getStage());
        bullet.loadAnimationFromSheet(tomato, 1, 9, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(250, -260);
        bullet.setSpeed(1200);
        bullet.setMotionAngle(0);

        bullet = new TomataBullet(0, 0, getStage());
        bullet.loadAnimationFromSheet(tomato, 1, 9, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(-250, -260);
        bullet.setSpeed(1200);
        bullet.setMotionAngle(180);


    }

    private void secondTomatoAttck2() {

        EnemyBullet bullet = new TomataBullet(0, 0, getStage());
        bullet.loadAnimationFromSheet(tomato, 1, 9, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(260, 250);
        bullet.setSpeed(1200);
        bullet.setMotionAngle(90);

        bullet = new TomataBullet(0, 0, getStage());
        bullet.loadAnimationFromSheet(tomato, 1, 9, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(260, -250);
        bullet.setSpeed(1200);
        bullet.setMotionAngle(270);


        bullet = new TomataBullet(0, 0, getStage());
        bullet.loadAnimationFromSheet(tomato, 1, 9, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(130, 250);
        bullet.setSpeed(1200);
        bullet.setMotionAngle(90);

        bullet = new TomataBullet(0, 0, getStage());
        bullet.loadAnimationFromSheet(tomato, 1, 9, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(130, -250);
        bullet.setSpeed(1200);
        bullet.setMotionAngle(270);


        bullet = new TomataBullet(0, 0, getStage());
        bullet.loadAnimationFromSheet(tomato, 1, 9, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(0, 250);
        bullet.setSpeed(1200);
        bullet.setMotionAngle(90);

        bullet = new TomataBullet(0, 0, getStage());
        bullet.loadAnimationFromSheet(tomato, 1, 9, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(0, -250);
        bullet.setSpeed(1200);
        bullet.setMotionAngle(270);


        bullet = new TomataBullet(0, 0, getStage());
        bullet.loadAnimationFromSheet(tomato, 1, 9, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(-130, 250);
        bullet.setSpeed(1200);
        bullet.setMotionAngle(90);

        bullet = new TomataBullet(0, 0, getStage());
        bullet.loadAnimationFromSheet(tomato, 1, 9, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(-130, -250);
        bullet.setSpeed(1200);
        bullet.setMotionAngle(270);


        bullet = new TomataBullet(0, 0, getStage());
        bullet.loadAnimationFromSheet(tomato, 1, 9, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(-260, 250);
        bullet.setSpeed(1200);
        bullet.setMotionAngle(90);

        bullet = new TomataBullet(0, 0, getStage());
        bullet.loadAnimationFromSheet(tomato, 1, 9, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(-260, -250);
        bullet.setSpeed(1200);
        bullet.setMotionAngle(270);


    }

    private void picleAttck() {

        EnemyBullet bullet = new AutoBullet(0, 0, getStage());
        bullet.loadAnimationFromSheet(cucumber, 1, 9, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(0, 250);
        bullet.setSpeed(1);
        bullet.setMotionAngle(0);

        bullet = new AutoBullet(0, 0, getStage());
        bullet.loadAnimationFromSheet(cucumber, 1, 9, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(0, -250);
        bullet.setSpeed(1);
        bullet.setMotionAngle(0);

        bullet = new AutoBullet(0, 0, getStage());
        bullet.loadAnimationFromSheet(cucumber, 1, 9, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(250, 0);
        bullet.setSpeed(1);
        bullet.setMotionAngle(0);

        bullet = new AutoBullet(0, 0, getStage());
        bullet.loadAnimationFromSheet(cucumber, 1, 9, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(-250, 0);
        bullet.setSpeed(1);
        bullet.setMotionAngle(0);


        bullet = new AutoBullet(0, 0, getStage());
        bullet.loadAnimationFromSheet(cucumber, 1, 9, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(-176, -176);
        bullet.setSpeed(1);
        bullet.setMotionAngle(0);


        bullet = new AutoBullet(0, 0, getStage());
        bullet.loadAnimationFromSheet(cucumber, 1, 9, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(176, -176);
        bullet.setSpeed(1);
        bullet.setMotionAngle(0);

        bullet = new AutoBullet(0, 0, getStage());
        bullet.loadAnimationFromSheet(cucumber, 1, 9, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(176, 176);
        bullet.setSpeed(1);
        bullet.setMotionAngle(0);

        bullet = new AutoBullet(0, 0, getStage());
        bullet.loadAnimationFromSheet(cucumber, 1, 9, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(-176, 176);
        bullet.setSpeed(1);
        bullet.setMotionAngle(0);
    }
}