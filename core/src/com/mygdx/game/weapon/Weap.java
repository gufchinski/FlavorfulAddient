package com.mygdx.game.weapon;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.engine.BaseActor;
import com.mygdx.game.engine.JoyStick;

/**
 * Реализация выстрела и вращения оружия
 */
public class Weap extends BaseActor {
    JoyStick joystick;
    Vector2 vecJoy;
    Float time = 0f;
    public String textureBullet = "bullet.png", textureWeapon = "weapon.png";
    public float rate, speed, btScale, wpScale, dist, width = 300, height = 300;
    public float wwidth=0,hheight=0;

    public Weap(float x, float y, Stage s, JoyStick weaponJoystick, Vector2 weapJoy) {
        super(x, y, s);


        loadAnimationFromSheet(textureWeapon, 1, 8, 0.1f, true);
        setScale(1.5f);
        setOrigin(0, getHeight() / 2 - 8);
        wwidth=getWidth()*getScaleX();
        hheight=getHeight()*getScaleY();
        joystick = weaponJoystick;
        vecJoy = weapJoy;

        dmg = 1000f;
        rate = 0.1f;
        speed = 1000;
    }
/**
 * Выстрел из оружия
 */
    public void shoot() {
         if(time >= rate) {
            Bullet bullet = new Bullet(0, 0, this.getStage(), textureBullet, dmg);
            if(isRight) {
                bullet.setPosition(getX()+getWidth()-25, getY() + 25);
                bullet.setOrigin(-getWidth()+25 , getHeight() / 2 - 29);
                bullet.setMotionAngle(getRotation());
            }
            else
            {
                 bullet.setPosition(getX()-bullet.getWidth()+25,getY()+ 25);
                 bullet.setOrigin(bullet.getWidth()+this.getWidth()-25,getHeight() / 2 - 29);

                 bullet.setMotionAngle(180+getRotation());
            }
            bullet.setRotation(getRotation());
            bullet.setSpeed(speed);
            bullet.setMaxSpeed(speed);
            time = 0f;
             }
        }

    public void act(float dt) {
        super.act(dt);
        time += dt;


    }
}


