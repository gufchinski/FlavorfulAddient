package com.mygdx.game.item;

import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Восстанавливает все жизни
 */
public class Pill extends Item {
    public Pill(float x, float y, Stage s) {
        super(x, y, s, 0);
        loadTexture("Item/pill.png");
        setScale(0.5f);
        textureName = "Item/pill.png";
        nameItem = "500";
        descriptionItem = "Fully restores HP";
    }

    @Override
    public void use() {
        person.hp = person.maxHp;
    }
}
