package com.mygdx.game.item;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Battery extends Item {
    public Battery(float x, float y, Stage s) {
        super(x, y, s, 1);
        loadTexture("Item/battery.png");
        textureName = "Item/battery.png";
        setScale(0.3f);
        nameItem = "Super Battery";
        descriptionItem = "Increases rate of fire but lowers fire rate";
    }

    @Override
    public void use() {
        weap.rate -= 0.2f;
        weap.dmg -= 30f;
    }

    @Override
    public void move() {
        super.move();
        moveBy(0, 15);
    }

}
