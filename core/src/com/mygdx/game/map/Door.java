package com.mygdx.game.map;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.engine.BaseActor;

public class Door extends BaseActor {
    float width, height;
    int doorType;
    public Door(float x, float y, Stage s,float width, float height, int doorType) {
        super(x, y, s);
        this.width = width;
        this.height = height;
        this.doorType = doorType; // 1 - верхняя дверь, 2 - нижняя, 3 - левая, 4 - правая 5-нижная доп дверь
        setSize(width, height);
        setBoundaryRectangle();
    }
    public void setPreventSize()
    {
        setSize(width,height);
    }
}
