package com.mygdx.game.engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
/**
 * расширенная версия актера, позволяющая риссовать повторяющие эллементы
 */
public class RepeatActor extends BaseActor {
    float drawingWidth, drawingHeight;
    Texture img;
     public RepeatActor(float x, float y, Stage s, float drawingWidth, float drawingHeight, String texture){
         super(x,y,s);
            this.drawingHeight=drawingHeight;
            this.drawingWidth=drawingWidth;
         img = new Texture(texture);

         img.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
     }

    public void draw(Batch batch, float parentAlpha) {



        Color c = getColor(); // used to apply tint color effect

        batch.setColor(c.r, c.g, c.b, c.a);
        batch.setProjectionMatrix(this.getStage().getCamera().combined);
        batch.draw(img, getX(), getY(), 0, 0,(int)drawingWidth, (int)drawingHeight);

    }

}
