package com.randezterying.rainy_east.View.Screens.MF;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.randezterying.rainy_east.Base.ScreenBase;
import com.randezterying.rainy_east.GameCore.Assets;
import com.randezterying.rainy_east.GameCore.Engine;
import com.randezterying.rainy_east.View.Screens.GameScene;

public class Load extends ScreenBase {

    private final int param;

    public Load(Engine engine, int param) {
        super(engine);
        this.param = param;
        setBG(new Texture(Gdx.files.internal("screenAssets/bg.png")));

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\\\/?-+=()*&.;:,{}\\\"´`'<>";
        parameter.size = (int) (Gdx.graphics.getWidth() * .03f);
        String FONT_PATH = "Fonts/eww.ttf";
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(FONT_PATH));
        font = generator.generateFont(parameter);
    }

    @Override
    public void show() {
        super.show();
        Assets.load();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (param == 0) {
            load();
        }
    }

    private void load() {
        float a = Assets.assetManager.getProgress() * 100;
        int p = (int) a;
        String x =  Integer.toString(p);

        getBatch().begin();
        font.setColor(Color.WHITE);
        font.draw(getBatch(), x + "%", Assets.w - Assets.w/2, Assets.h - 9.5f*Assets.h/10);
        getBatch().end();

        if(Assets.assetManager.update()){
            getEngine().setScreen(new GameScene(getEngine()));
            Assets.assetManager.finishLoading();
        }
    }
}
