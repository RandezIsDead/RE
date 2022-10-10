package com.randezterying.rainy_east.Base.ScreenElements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.randezterying.rainy_east.Base.BaseScreenElement;
import com.randezterying.rainy_east.GameCore.Assets;

public class ButtonBase extends BaseScreenElement {

    public TextButton button;
    private final TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();

    public ButtonBase(String atlasPath, String drawable, float x, float y, float width, float height) {
        super(x);
        setButtonStyle(atlasPath, drawable, "", x, y, width, height);
    }

    public ButtonBase(String atlasPath, String text, String drawable, float x, float y, float width, float height) {
        super(x);
        setButtonStyle(atlasPath, drawable, text, x, y, width, height);
    }

    private void setButtonStyle(String atlasPath, String drawable, String text, float x, float y, float width, float height) {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(atlasPath));
        skin.addRegions(atlas);

        buttonStyle.font = font;
        buttonStyle.up = skin.getDrawable(drawable);
        button = new TextButton(text, buttonStyle);
        button.setPosition(x, y * Assets.h/Assets.w);
        button.setSize(width, height * Assets.h/Assets.w);

        stage.addActor(button);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        button.draw(batch, parentAlpha);
        stage.draw();
    }

    @Override
    public void moveBy(float x, float y) {
        super.moveBy(x, y);
        button.moveBy(x, y);
    }

    public boolean isChecked() {
        if (button.isChecked()) Gdx.audio.newSound(Gdx.files.internal("sounds/click.mp3")).play();
        return button.isChecked();
    }

    public void setChecked(boolean clicked) {button.setChecked(clicked);}
    public void setText(String text) {button.setText(text);}
    public void click(String drawable) {buttonStyle.down = skin.getDrawable(drawable);}
    public void setPosition(float x, float y) {button.setPosition(x, y);}
    public void addListener(DragListener dragListener) {button.addListener(dragListener);}
    public void removeListener() {button.removeListener(button.getClickListener());}
}