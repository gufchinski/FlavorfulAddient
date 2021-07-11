package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.mygdx.game.item.Item;
import com.mygdx.game.screen.LevelScreen;

/**
 * Кнопка для использование предметов
 */
public class ActionButton extends Image {
    public boolean isUse = false;
    public String nameAction;

    public SpriteDrawable textPickUp, textnotUse, textExit, textActReady, textActNotReady;


    public ActionButton(String textureName, float x, float y, float width, float height) {
        super(new Texture(textureName));
        setPosition(x, y);
        setSize(width, height);

        textnotUse = new SpriteDrawable(new Sprite(new Texture("ui/emptyButton.png")));
        textPickUp = new SpriteDrawable(new Sprite(new Texture("ui/pickUpButton.png")));
        textExit = new SpriteDrawable(new Sprite(new Texture("ui/ExitActButton.png")));
        textActReady = new SpriteDrawable(new Sprite(new Texture("ui/itemFullButton.png")));
        textActNotReady = new SpriteDrawable(new Sprite(new Texture("ui/itemEmptyButton.png")));
    }


    @Override
    public void act(float delta) {
        super.act(delta);

    }

   

}
