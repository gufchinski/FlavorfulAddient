package com.mygdx.game.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.engine.BaseActor;

public class Portal extends BaseActor {
    Texture main,start;
    boolean starting=true;
    public Portal(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("person.png");
        main = new Texture("map/portal.png");
        start= new Texture("map/portalOpen.png");
        loadAnimationFromSheet(start, 1, 8, 0.125f, false);


    }

    @Override
    public void act(float dt) {
        super.act(dt);
        if(starting&&isAnimationFinished())
        {
            starting=false;
            loadAnimationFromSheet(main, 1, 8, 0.125f, true);

        }
    }
}
