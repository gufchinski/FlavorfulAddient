package com.mygdx.game.map;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.engine.BaseActor;
import com.mygdx.game.engine.BaseGame;
import com.mygdx.game.engine.BaseScreen;
import com.mygdx.game.item.Battery;
import com.mygdx.game.item.Cyberimplant;
import com.mygdx.game.item.ItemList;
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
    ItemList itemList;
    int num;

    public Chest(float x, float y, Stage s, Room room,Stage frontStage) {
        super(x, y, s);
        loadTexture("map/table1.png");
        BaseActor eps = new BaseActor(getX(), getY(), frontStage);
        eps.loadTexture("map/tableLamp.png");
        eps.setScale(1.2f);

        Random rand = new Random();
        setScale(1.2f);
        float[] vertices = {0, 194, 298, 194, 298, 240, 0, 240};
        setBoundaryRectangle(vertices);
        room.walls.add(this);
        if (BaseGame.itemPool.size() > 0)
            num = rand.nextInt(BaseGame.itemPool.size());
        itemList = BaseGame.itemPool.get(num);
        BaseGame.itemPool.remove(num);
        switch (itemList) {
            case PILL:
                item = new Pill(getX(), getY(), s);
                break;
            case POISON_BULLET:
                item = new PoisonBullet(getX(), getY(), s);
                break;
            case ENERGY_DRINK:
                item = new EnergyDrink(getX(), getY(), s);
                break;
            case CYBERIMPLANT:
                item = new Cyberimplant(getX(), getY(), s);
                break;
            case SHIELD:
                item = new Shield(getX(), getY(), s);
                break;
            case BATTERY:
                item = new Battery(getX(), getY(), s);
                break;


        }

        item.centerAtActor(this);
        item.moveBy(-55, 5);
        item.move();
        room.itemList.add(item);

    }

}
