package com.mygdx.game.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * рассширает класс экрана
 */
public abstract class BaseScreen implements Screen, InputProcessor {
    protected Stage mainStage;
    protected Stage uiStage;
    protected Stage loadStage;
    protected Stage backgrondStage;
    protected Stage frontStage;
    protected Stage epsStage;
    protected Table uiTable;
    protected Stage deadStage;


    public BitmapFont bf;
    public static LabelStyle lb;
    public InputMultiplexer im;
    public boolean gameOver=false;
    public BaseScreen()
    {

        bf=new BitmapFont( Gdx.files.internal("font/cooper.fnt") );
        lb = new LabelStyle();
        lb.font = bf;
        epsStage=new Stage(new StretchViewport(1920,1080));
        frontStage=new Stage(new ExtendViewport(1920,1080));
        backgrondStage = new Stage(new ExtendViewport(1920,1080));
        mainStage = new Stage(new ExtendViewport(1920,1080));
        uiStage = new Stage(new ExtendViewport(1920,1080));
        loadStage=new Stage(new ExtendViewport(1920,1080));
        deadStage=new Stage(new ExtendViewport(1920,1080));

        uiTable = new Table();
        uiTable.setFillParent(true);
        uiStage.addActor(uiTable);



        im = new InputMultiplexer();
        Gdx.input.setInputProcessor(im);
        initialize();
    }

    public abstract void initialize();

    public abstract void update(float dt);

    // Gameloop:
    // (1) process input (discrete handled by listener; continuous in update)
    // (2) update game logic
    // (3) render the graphics
    public void render(float dt)
    {
        // act methods
        epsStage.act(dt);
        uiStage.act(dt);
        if(!gameOver) {
            update(dt);
            backgrondStage.act(dt);
            mainStage.act(dt);
            frontStage.act();
            loadStage.act(dt);


            // defined by user

        }
        else
        {
            deadStage.act(dt);
        }
        // clear the screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // draw the graphics
        epsStage.draw();
        if(!gameOver) {
            backgrondStage.draw();
            mainStage.draw();
            frontStage.draw();
            uiStage.draw();
        }
        loadStage.draw();
        deadStage.draw();
    }

    // methods required by Screen interface
    public void resize(int width, int height) {

    }

    public void pause()   {  }

    public void resume()  {  }

    public void dispose() {  }

    public void show()    {

        im.addProcessor(this);
        im.addProcessor(uiStage);
        im.addProcessor(mainStage);

    }

    public void hide()   {

        im.removeProcessor(this);
        im.removeProcessor(uiStage);
        im.removeProcessor(mainStage);
        im.removeProcessor(deadStage);

    }


    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }
}