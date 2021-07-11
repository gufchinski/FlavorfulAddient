package com.mygdx.game.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.weapon.EnemyBullet;
import com.mygdx.game.Person;
import com.mygdx.game.weapon.WizardBullet;

import java.util.Random;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Враг коробка с хлопьями
 */
public class Wizard extends Enemy {

    Random random;
    float time = 0;
    int rastm = 500;
    float x, y;
    float speed = 300;
    boolean boom = false, boom1 = false;
    Texture glavnoe, izchezn, poiavleny, ye, bl, red;
    float bulletSpeed = 1500;

    public Wizard(float x, float y, Stage s) {
        super(x, y, s);
        setHp(500f);

        setOrigin(getX() + getWidth() / 2, getY() + getHeight() / 2);
        setSpeed(speed);
        setMaxSpeed(speed);
        setDeceleration(0);
        random = new Random();
        ye = new Texture("enemy/cereal/yellowCerealAppear.png");
        bl = new Texture("enemy/cereal/blueCerealAppear.png");
        red = new Texture("enemy/cereal/redCerealAppear.png");
        glavnoe = new Texture("enemy/cereal/cerealMage.png");
        izchezn = new Texture("enemy/cereal/cerealDissapear.png");
        poiavleny = new Texture("enemy/cereal/cerealAppear.png");
        textureDeath = new Texture("enemy/cereal/cerealDeath.png");
        countDeath = 7;
        loadAnimationFromSheet(glavnoe, 1, 12, 0.1f, true);
        setBoundaryRectangle();
        setDmg(0);
    }

    public void act(float dt) {
        super.act(dt);

    }

    @Override
    public void behavior(float dt) {
        time += dt;
        if (time >= 2 && !boom && !boom1) {
            loadAnimationFromSheet(izchezn, 1, 9, 0.1f, false);
            boom = true;


        }
        if (boom && isAnimationFinished()) {
            boom = false;
            float rand = random.nextInt(360);
            x = person.getX() + person.getWidth() / 2 - getWidth() / 2 + rastm * (float) cos(rand);
            y = person.getY() + person.getHeight() / 2 - getHeight() / 2 + rastm * (float) sin(rand);
            if (room.roomCheck(y, x, getWidth(), getHeight())) {
                setPosition(x, y);
            }
            loadAnimationFromSheet(poiavleny, 1, 9, 0.1f, false);
            boom1 = true;

        }
        if (boom1 && isAnimationFinished()) {
            loadAnimationFromSheet(glavnoe, 1, 12, 0.1f, true);
            attack();
            time = 0;
            boom1 = false;
        }
        if (person.getX() < getX())
            isRight = false;
        else {
            isRight = true;
        }
    }

    public void attack() {
        EnemyBullet bullet = new WizardBullet(0, 0, this.getStage());
        bullet.loadAnimationFromSheet(ye, 1, 9, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(150, 0);
        bullet.setSpeed(bulletSpeed);
        bullet = new WizardBullet(0, 0, this.getStage());
        bullet.loadAnimationFromSheet(red, 1, 9, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(-150, 0);
        bullet.setSpeed(bulletSpeed);
        bullet = new WizardBullet(0, 0, this.getStage());
        bullet.loadAnimationFromSheet(bl, 1, 9, 0.1f, false);
        bullet.centerAtActor(this);
        bullet.moveBy(0, 150);
        bullet.setSpeed(bulletSpeed);
    }
}
