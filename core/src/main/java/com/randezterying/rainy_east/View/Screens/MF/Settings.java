package com.randezterying.rainy_east.View.Screens.MF;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.randezterying.rainy_east.Base.ScreenBase;
import com.randezterying.rainy_east.Base.ScreenElements.ButtonBase;
import com.randezterying.rainy_east.Base.ScreenElements.CheckBoxBase;
import com.randezterying.rainy_east.Base.ScreenElements.SliderBase;
import com.randezterying.rainy_east.GameCore.Data;
import com.randezterying.rainy_east.GameCore.Engine;

public class Settings extends ScreenBase {

    public ButtonBase exitButton;
    public SliderBase musicVol;
    public SliderBase sfxVol;
    public CheckBoxBase muteMusic;
    public CheckBoxBase muteSfx;
    public CheckBoxBase showFPS;

    public Settings(Engine engine) {
        super(engine);
        setBG(new Texture("screenAssets/settings.png"));

        exitButton = new ButtonBase("Atlas/buttons.txt", "cl", 850, 780, 100, 160);
        musicVol = new SliderBase(100, 400, 343, 200);
        sfxVol = new SliderBase(100, 230, 343, 200);
        muteMusic = new CheckBoxBase(366, 180, 39, 70);
        muteSfx = new CheckBoxBase(306, 100, 40, 70);
        showFPS = new CheckBoxBase(730, 550, 40, 70);
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(getMultiplexer());

        addObject(exitButton);
        addObject(musicVol);
        addObject(sfxVol);
        addObject(muteSfx);
        addObject(muteMusic);
        addObject(showFPS);

        musicVol.slider.setValue(Data.getFloat("musicVol"));
        muteSfx.setChecked(Data.getBoolean("sfxMuteIsChecked"));
        muteMusic.setChecked(Data.getBoolean("musicMuteIsChecked"));
        showFPS.setChecked(Data.getBoolean("showFPS"));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        actFinal(delta);
        Data.putFloat("musicVol", musicVol.getPos());
        Data.putFloat("sfxVol", sfxVol.getPos());
        Data.putBoolean("musicMuteIsChecked", muteMusic.isChecked());
        Data.putBoolean("sfxMuteIsChecked", muteSfx.isChecked());
        Data.putBoolean("showFPS", showFPS.isChecked());

        if (exitButton.isChecked()) {
            getEngine().setScreen(new Main(getEngine()));
            exitButton.setChecked(false);
        }
    }
}
