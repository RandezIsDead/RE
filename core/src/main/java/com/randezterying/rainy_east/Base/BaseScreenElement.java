package com.randezterying.rainy_east.Base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.randezterying.rainy_east.GameCore.Assets;

import java.util.ArrayList;

public class BaseScreenElement extends Actor implements Disposable {

    public Stage stage;
    protected final Skin skin;
    protected BitmapFont font;

    protected final ArrayList<Float> xPos = new ArrayList<>();

    public BaseScreenElement(float x) {
        xPos.add(x);

        stage = new Stage(new StretchViewport(1000, 1000 * Assets.h/Assets.w));
        Gdx.input.setInputProcessor(stage);

        font = new BitmapFont();
        skin = new Skin();

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\\\/?-+=()*&.;:,{}\\\"´`'<>";
        String FONT_PATH = "Fonts/eww.ttf";
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(FONT_PATH));
        font = generator.generateFont(parameter);
        font.getData().setScale(1.5f);
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        font.dispose();
    }
}