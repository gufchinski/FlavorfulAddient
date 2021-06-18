package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.mygdx.game.engine.BaseActor;
import com.mygdx.game.engine.BaseScreen;

import static com.mygdx.game.engine.BaseGame.setActiveScreen;

public class MenuScreen extends BaseScreen {
    TextButton btnPlay;

    Image btnp,btnExit;
    BaseActor backgroundTexture;


    @Override
    public void initialize() {
        Table tax=new Table();
        Skin mySkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        backgroundTexture=new BaseActor(0,0,epsStage);
        backgroundTexture.loadTexture("ui/mainMenuScreen.png");
        backgroundTexture.setSize(1920,1080);

        btnp=new Image(new Texture("ui/playButton.png"));

        Label.LabelStyle lb=new Label.LabelStyle();
        BitmapFont mainfont=new BitmapFont( Gdx.files.internal("font/da.fnt"));
        mainfont.getData().setScale(5);
            lb.font=mainfont;

        btnp.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setActiveScreen(new LevelScreen());
                return true;
            }
        });


        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();





        bf.getData().setScale(3);
        textButtonStyle.font  =bf;


        btnExit =new Image(new Texture("ui/exitButton.png"));
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


        uiTable.pad(30);
        uiTable.padBottom(155);

        uiTable.add(btnp).padTop(160).expandX();
        //uiTable.add(btnPlay);
        uiTable.add( btnExit).expandX().padTop(60);
        uiTable.left().bottom();
        //uiStage.setDebugAll(true);
    }


    @Override
    public void update(float dt) {

    }
}
