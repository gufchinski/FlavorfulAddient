package com.mygdx.game.item;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.engine.BaseActor;
import com.mygdx.game.engine.BaseScreen;

/**
 * Восстанавливает все жизни
 */
public class Shield extends Item {


    public Shield(float x, float y, Stage s) {
        super(x, y, s, 3);
        loadTexture("Item/shield.png");
        textureName = "Item/shield.png";
        nameItem = "Shield prototype";
        descriptionItem = "Creates a temporary barrier";
    }

    @Override
    public void use() {
        ActiveShield activeShield = new ActiveShield(person.getX(), person.getY(), BaseScreen.mainStage);
    }


}
