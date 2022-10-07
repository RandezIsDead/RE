package com.randezterying.rainy_east.Base.ScreenElements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.randezterying.rainy_east.Base.BaseScreenElement;
import com.randezterying.rainy_east.GameCore.Assets;

public class SliderBase extends BaseScreenElement {

    public Slider slider;

    public SliderBase(float x, float y, float width, float height) {
        super(x);
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("Atlas/sliderStyle.txt"));
        skin.addRegions(atlas);
        Slider.SliderStyle sliderStyle = new Slider.SliderStyle();
        sliderStyle.background = skin.getDrawable("slider");
        sliderStyle.knob = skin.getDrawable("forSlider");

        slider = new Slider(0, 1, 0.01f, false, sliderStyle);
        slider.setPosition(x, y*(Assets.h/Assets.w));
        slider.setSize(width, height*(Assets.h/Assets.w));

        stage.addActor(slider);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        slider.draw(batch, parentAlpha);
        stage.draw();
    }

    public float getPos() {
        return slider.getValue();
    }

    public void addToClose() {
        this.moveButton(1500, this.getButtonY());
    }

    public void open() {
        this.moveButton(this.xPos.get(0), this.getButtonY());
    }

    public float getButtonX() {
        return slider.getX();
    }

    public float getOriginX() {
        return this.xPos.get(0);
    }

    public float getButtonY() {
        return slider.getY();
    }

    public void moveButton(float x, float y) {
        slider.setPosition(x, y);
    }
}
