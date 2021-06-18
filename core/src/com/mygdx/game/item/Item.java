package com.mygdx.game.item;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.engine.BaseActor;

public  class Item extends BaseActor {
    public boolean onFloor=true;
    public String textureName="";
    public int type=0;
    public String nameItem,descriptionItem;
    public Item(float x, float y, Stage s,int type) {
        super(x, y, s);
        this.type=type;
        loadTexture("Item/toxicBullets.png");
        textureName="Item/toxicBullets.png";
    }
    public  void  use()
    {

    }
}
