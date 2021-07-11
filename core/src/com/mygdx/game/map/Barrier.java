package com.mygdx.game.map;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.engine.BaseActor;
import com.mygdx.game.engine.RepeatActor;

/**
 * Класс для создания стен
 */

public class Barrier extends RepeatActor {

    public Barrier(float x, float y, Stage s, float width, float height, String texture) {
        super(x, y, s, width, height, texture);
        setSize(width, height);
        setBoundaryRectangle();
    }

}
