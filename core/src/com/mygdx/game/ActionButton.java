package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class ActionButton extends Image {
    public boolean isUse=false;
    public String nameAction;
    public SpriteDrawable  textnotUse,textUse;
    public ActionButton(String textureName, float x, float y, float width, float height)
    {
        super(new Texture(textureName));
        setPosition( x , y);
        setSize(width,height);
        textnotUse= new SpriteDrawable(new Sprite(new Texture("ui/joy_background.png")));
        textUse= new SpriteDrawable(new Sprite(new Texture("ui/itemPickButton.png")));

    }

    public void setUse(boolean use, String nameClass ) {
        isUse=use;
        nameAction=nameClass;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

    }

}
