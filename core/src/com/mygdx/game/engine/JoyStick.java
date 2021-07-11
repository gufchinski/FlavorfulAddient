package com.mygdx.game.engine;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

/**
 * Создает джостик для управления персонажем и оружием
 */
public class JoyStick {
    private Skin joySkin = new Skin();
    private Touchpad.TouchpadStyle touchpadStyle = new Touchpad.TouchpadStyle();
    private Touchpad touchpad;

    public JoyStick(float Radius, String BackgroundImg, String knobImg) {
        joySkin.add("touchBackground", new Texture(BackgroundImg));
        joySkin.add("touchKnob", new Texture(knobImg));
        touchpadStyle.background = joySkin.getDrawable("touchBackground");
        touchpadStyle.knob = joySkin.getDrawable("touchKnob");
        touchpad = new Touchpad(Radius, touchpadStyle);
    }

    public JoyStick(float Radius, String BackgroundImg, String knobImg, int posX, int posY, float width, float height) {
        this(Radius, BackgroundImg, knobImg);
        setPosSize(posX, posY, width, height);

    }

    public Float getKnobx() {
        return touchpad.getKnobPercentX();
    }

    public Float getKnoby() {
        return touchpad.getKnobPercentY();
    }

    public Float getX() {
        return touchpad.getKnobX() - touchpad.getWidth() / 2;
    }

    public Float getY() {
        return touchpad.getKnobY() - touchpad.getHeight() / 2;
    }

    public boolean isTouch() {
        return touchpad.isTouched();
    }

    public Touchpad getTouchpad() {
        return touchpad;
    }

    public void setPosSize(int PosX, int posY, float width, float height) {
        touchpad.setBounds(PosX, posY, width, height);
    }
}