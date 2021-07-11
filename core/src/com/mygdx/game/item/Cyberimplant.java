package com.mygdx.game.item;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Cyberimplant extends Item {
    public Cyberimplant(float x, float y, Stage s) {
        super(x, y, s, 1);
        loadTexture("Item/cyberimplant.png");
        textureName = "Item/cyberimplant.png";
        nameItem = "Cyberimplant";
        descriptionItem = "Increases maximum HP";
    }

    @Override
    public void use() {
        person.hp += person.maxHp / 2;
        person.maxHp = person.maxHp * 1.75f;

    }

    @Override
    public void move() {
        super.move();
        moveBy(0, 30);
    }


}
