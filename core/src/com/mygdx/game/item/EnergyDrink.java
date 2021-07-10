package com.mygdx.game.item;

import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Восстанавливает все жизни
 */
public class EnergyDrink extends Item {
    public EnergyDrink(float x, float y, Stage s) {
        super(x, y, s,1);
        loadTexture("Item/energyDrink.png");
        setScale(0.5f);
        textureName="Item/energyDrink.png";
        nameItem = "Energy Drink";
        descriptionItem = "Increases character speed ";
    }
    @Override
    public  void use()
    {
        person.setSpeed(person.getSpeed()+1.5f);
    }

    @Override
    public void move() {
        super.move();
        moveBy(0,30);
    }
}
