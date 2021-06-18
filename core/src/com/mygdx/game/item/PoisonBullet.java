package com.mygdx.game.item;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.effect.Effect;
import com.mygdx.game.effect.Poison;

public class PoisonBullet extends Item {
    Effect effect;
    public PoisonBullet(float x, float y, Stage s) {
        super(x, y, s, 1);
        effect=new Poison();
        nameItem="Poison";
        descriptionItem="Create poison bullet";
    }
    @Override
    public  void use()
    {
        screen.bulletEffect = effect;
    }
}
