package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.engine.BaseActor;
import com.mygdx.game.engine.BaseScreen;

public class FinalScreen extends BaseScreen {

    Image btnp, btnExit;
    BaseActor backgroundTexture;
    @Override
    public void initialize() {
        backgroundTexture = new BaseActor(0, 0, epsStage);
        backgroundTexture.loadTexture("ui/credits.png");
        backgroundTexture.setSize(1920, 1080);
        btnExit = new Image(new Texture("ui/exitButton.png"));
        btnExit.setScale(0.7f);
        btnExit.addListener(

                new EventListener() {
                    @Override
                    public boolean handle(Event e) {
                        if (!(e instanceof InputEvent))
                            return false;

                        if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                            return false;

                        Gdx.app.exit();
                        return true;
                    }
                }
        );

        uiStage.addActor(btnExit);
        uiTable.center().bottom();
        uiTable.add(btnExit);
        uiTable.padBottom(150);



    }

    @Override
    public void update(float dt) {

    }
}
