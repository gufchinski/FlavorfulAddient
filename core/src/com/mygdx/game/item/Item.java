package com.mygdx.game.item;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.engine.BaseActor;

import java.awt.Color;

/**
 * Предметы в игре
 */
public abstract class Item extends BaseActor {
    public boolean onFloor = true;
    public String textureName = "";
    public int type = 0;
    public String nameItem, descriptionItem;

    Stage mainStage;

    public Item(float x, float y, Stage s, int type) {
        super(x, y, s);
        this.type = type;
        loadTexture("Item/toxicBullets.png");
        textureName = "Item/toxicBullets.png";
        mainStage = s;

    }

    public abstract void use();


    public void move() {

    }

    @Override
    public void act(float dt) {
        super.act(dt);

    }
}
