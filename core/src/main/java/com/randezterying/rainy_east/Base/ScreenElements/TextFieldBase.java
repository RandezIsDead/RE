package com.randezterying.rainy_east.Base.ScreenElements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.randezterying.rainy_east.Base.BaseScreenElement;
import com.randezterying.rainy_east.GameCore.Assets;

public class TextFieldBase extends BaseScreenElement {

    public TextField field;
    private boolean clicked = false;

    public TextFieldBase(float x, float y, float width, float height) {
        super(x);

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("Atlas/textField.txt"));
        skin.addRegions(atlas);
        TextField.TextFieldStyle style = new TextField.TextFieldStyle();
        style.background = skin.getDrawable("tfBG");
        style.cursor = skin.getDrawable("cursor");
        style.selection = skin.getDrawable("selection");
        style.font = font;
        style.fontColor = Color.WHITE;

        field = new TextField("", style);
        field.setPosition(x, y * (Assets.h / Assets.w));
        field.setSize(width, height * (Assets.h / Assets.w));
        field.setAlignment(Align.center);

        stage.addActor(field);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        field.draw(batch, parentAlpha);
        stage.draw();

        field.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {}
        });
    }

    public void addToClose() {
        this.moveButton(1500, this.getButtonY());
    }

    public void open() {
        this.moveButton(this.xPos.get(0), this.getButtonY());
    }

    public float getButtonX() {
        return field.getX();
    }

    public float getOriginX() {
        return this.xPos.get(0);
    }

    public float getButtonY() {
        return field.getY();
    }

    public void moveButton(float x, float y) {
        field.setPosition(x, y);
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }
}
