package com.mygdx.game.engine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.item.ItemList;
import com.mygdx.game.map.MapCreator;

import java.util.ArrayList;


/**
 *  Created when program is launched;
 *  manages the screens that appear during the game.
 */
public abstract class BaseGame extends Game
{
    /**
     *  Stores reference to game; used when calling setActiveScreen method.
     */
    private static BaseGame game;
    public static Label.LabelStyle labelStyle;
    public static TextButton.TextButtonStyle textButtonStyle;
    public static ArrayList<ItemList> itemPool = new ArrayList<>();

    /**
     *  Called when game is initialized; stores global reference to game object.
     */
    public BaseGame()
    {
        game = this;
    }
    public void create()
    {
        // prepare for multiple classes/stages/actors to receive discrete input

    }

    /**
     *  Used to switch screens while game is running.
     *  Method is static to simplify usage.
     */
    public static void setActiveScreen(BaseScreen s)
    {
        game.setScreen(s);
    }
}