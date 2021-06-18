package com.mygdx.game.item;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class HealthUp extends Item {
    public HealthUp(float x, float y, Stage s) {
        super(x, y, s,0);
        loadTexture("Item/pill.png");
        setScale(0.5f);
        textureName="Item/pill.png";
    }
    @Override
    public  void use()
    {
        person.hp=person.maxHp;
    }
}
