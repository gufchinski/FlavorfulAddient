package com.mygdx.game.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.mygdx.game.engine.BaseActor;
import com.mygdx.game.item.HealthUp;
import com.mygdx.game.item.Item;
import com.mygdx.game.item.PoisonBullet;
import com.mygdx.game.screen.LevelScreen;

import java.util.ArrayList;
import java.util.Random;

import static com.mygdx.game.engine.BaseGame.setActiveScreen;

public class Chest extends BaseActor {
    Item item;
    public Chest(float x, float y, Stage s,Room room) {
        super(x, y, s);
        loadTexture("map/table.png");

        Random rand=new Random();
        setScale(1.2f);
        float[] vertices = {0, 194, 298, 194, 298, 240, 0,240};
        setBoundaryRectangle(vertices);
        room.walls.add(this);
        switch (rand.nextInt(2)) {
            case 0:
                 item=new HealthUp(getX(),getY(),s);
                break;
            case 1:
                item=new PoisonBullet(getX(),getY(),s);
                break;

        }

        item.centerAtActor(this);
        item.moveBy(-55,5);
        room.itemList.add(item);

    }

}
