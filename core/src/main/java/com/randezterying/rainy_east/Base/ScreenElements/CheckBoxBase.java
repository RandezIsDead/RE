package com.randezterying.rainy_east.Base.ScreenElements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.randezterying.rainy_east.Base.BaseScreenElement;
import com.randezterying.rainy_east.GameCore.Assets;

public class CheckBoxBase extends BaseScreenElement {

    public TextButton button;
    private boolean checked = false;

    public CheckBoxBase(float x, float y, float width, float height) {
        super(x);

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("Atlas/cbStyle.txt"));
        skin.addRegions(atlas);
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;
        style.up = skin.getDrawable("off");
        style.checked = skin.getDrawable("on");
        button = new TextButton("", style);
        button.setPosition(x, y * (Assets.h / Assets.w));
        button.setSize(width, height * (Assets.h / Assets.w));

        stage.addActor(button);

        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isChecked()) {
                    setChecked(false);
                    button.setChecked(isChecked());
                } else if (!isChecked()) {
                    setChecked(true);
                    button.setChecked(isChecked());
                }
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        button.draw(batch, parentAlpha);
        stage.draw();
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        button.setChecked(checked);
    }

    public void addToClose() {
        this.move(1500, this.getObjY());
    }

    public void open() {
        this.move(this.xPos.get(0), this.getObjY());
    }

    public float getObjX() {
        return button.getX();
    }

    public float getOriginX() {
        return this.xPos.get(0);
    }

    public float getObjY() {
        return button.getY();
    }

    public void move(float x, float y) {
        button.setPosition(x, y);
    }
}
