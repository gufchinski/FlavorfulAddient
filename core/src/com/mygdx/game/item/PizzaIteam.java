package com.mygdx.game.item;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.engine.BaseActor;
import com.mygdx.game.screen.FinalScreen;

import static com.mygdx.game.engine.BaseGame.setActiveScreen;

public class PizzaIteam extends BaseActor {


    public PizzaIteam(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("Item/pizzaButton.png");
        getBoundaryPolygon();
    }

}
