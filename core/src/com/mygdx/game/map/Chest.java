package com.mygdx.game.map;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.engine.BaseActor;
import com.mygdx.game.item.Cyberimplant;
import com.mygdx.game.item.Pill;
import com.mygdx.game.item.Item;
import com.mygdx.game.item.EnergyDrink;
import com.mygdx.game.item.PoisonBullet;
import com.mygdx.game.item.Shield;

import java.util.Random;

/**
 * класс для генерации предметов
 */

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
        switch (rand.nextInt(1)+4) {
            case 0:
                 item=new Pill(getX(),getY(),s);
                break;
            case 1:
                item=new PoisonBullet(getX(),getY(),s);
                break;
            case 2:
                item=new EnergyDrink(getX(),getY(),s);
                break;
            case 3:
                item=new Cyberimplant(getX(),getY(),s);
                break;
            case 4:
                item=new Shield(getX(),getY(),s);
                break;


        }

        item.centerAtActor(this);
        item.moveBy(-55,5);
        item.move();
        room.itemList.add(item);

    }

}
