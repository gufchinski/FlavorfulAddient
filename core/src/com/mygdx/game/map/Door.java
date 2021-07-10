package com.mygdx.game.map;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.engine.BaseActor;

/**
 * Класс для установления типа дверей и коллизии для них
 */

public class Door extends BaseActor {
    float width, height;
    DoorType doorType;

    public Door(float x, float y, Stage s,float width, float height, DoorType doorType) {
        super(x, y, s);
        this.width = width;
        this.height = height;
        setBoundaryRectangle();
        setSize(width, height);
        this.doorType=doorType;
    }
    public void setPreventSize()
    {
        setSize(width,height);
    }
}
