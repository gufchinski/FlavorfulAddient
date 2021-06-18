package com.mygdx.game.engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

public class DialogBox extends BaseActor {
    private Label dialogLabel, dialogLabel2;
    private float padding = 16, secondpadding = 50;

    public DialogBox(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("dialog-translucent.png");

        dialogLabel = new Label(" ", BaseScreen.lb);
        dialogLabel.setWrap(true);
        dialogLabel.setAlignment(Align.topLeft);
        dialogLabel.setPosition(padding, padding);
        dialogLabel2 = new Label(" ", BaseScreen.lb);
        dialogLabel2.setWrap(true);
        dialogLabel2.setAlignment(Align.topLeft);
        dialogLabel2.setPosition(padding, secondpadding);
        this.setDialogSize(getWidth(), getHeight());
        this.addActor(dialogLabel);
        this.addActor(dialogLabel2);
    }

    public void setDialogSize(float width, float height) {
        this.setSize(width, height);
        dialogLabel.setWidth(width - 2 * padding);
        dialogLabel.setHeight(height - 2 * padding);
        dialogLabel2.setWidth(width - 2 * padding);
        dialogLabel2.setHeight(height - 2 * secondpadding);
    }

    public void setText(String text) {
        dialogLabel.setText(text);
    }

    public void setSecondText(String text2) {
        dialogLabel2.setText(text2);
    }

    public void setFontScale(float scale) {
        dialogLabel.setFontScale(scale);
        dialogLabel2.setFontScale(scale * 0.7f);
    }

    public void setFontColor(Color color) {
        dialogLabel.setColor(color);
        dialogLabel2.setColor(color);
    }

    public void setBackgroundColor(Color color) {
        this.setColor(color);
    }

    public void alignTopLeft() {
        dialogLabel.setAlignment(Align.topLeft);
        dialogLabel2.setAlignment(Align.topLeft);
    }

    public void alignCenter() {
        dialogLabel.setAlignment(Align.center);
        dialogLabel.moveBy(0,30);
        dialogLabel2.setAlignment(Align.center);
        dialogLabel2.moveBy(0,-secondpadding);
    }
}