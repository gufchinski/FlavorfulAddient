package com.mygdx.game.map;


import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.engine.BaseActor;

public class Solid extends BaseActor {
    public Solid(float x, float y, Stage s,String textureNAme){
        super(x,y,s);
        loadTexture(textureNAme);
        setBoundaryRectangle();
    }
}
