package com.randezterying.rainy_east.View.Screens.MF;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.randezterying.rainy_east.Base.ScreenBase;
import com.randezterying.rainy_east.Base.ScreenElements.ButtonBase;
import com.randezterying.rainy_east.GameCore.Data;
import com.randezterying.rainy_east.GameCore.Engine;

public class Main extends ScreenBase {

    private final ButtonBase startButton;
    private final ButtonBase optionButton;
    private final ButtonBase quitButton;
    private final ButtonBase credits;
    private final ButtonBase achievement;
    private final Music bgMusic;

    public Main(Engine engine) {
        super(engine);
        setBG(new Texture("screenAssets/main.png"));

        startButton = new ButtonBase("Atlas/buttons.txt", "startButton", 640, 200, 300, 600);
        startButton.click("startButtonPressed");
        optionButton = new ButtonBase("Atlas/buttons.txt", "settings", 480, 200, 150, 600);
        optionButton.click("settingsPressed");
        credits = new ButtonBase("Atlas/buttons.txt", "credits", 320, 200, 150, 200);
        credits.click("creditsPressed");
        achievement = new ButtonBase("Atlas/buttons.txt", "achievement", 320, 420, 150, 380);
        achievement.click("achievementPressed");
        quitButton = new ButtonBase("Atlas/buttons.txt", "exit", 10, 200, 300, 600);
        quitButton.click("exitPressed");

        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Zombie.mp3"));
    }

    @Override
    public void show() {
        bgMusic.play();
        bgMusic.setLooping(true);

        super.show();
        Gdx.input.setInputProcessor(getMultiplexer());

        addObject(startButton);
        addObject(optionButton);
        addObject(credits);
        addObject(achievement);
        addObject(quitButton);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        actFinal(delta);
        if (Data.getBoolean("musicMuteIsChecked")) bgMusic.setVolume(0);
        else bgMusic.setVolume(Data.getFloat("musicVol"));

        if (startButton.isChecked()) {
            bgMusic.dispose();
            getEngine().setScreen(new Load(getEngine(), 0));
        }
        if (quitButton.isChecked()) {
            bgMusic.dispose();
            Gdx.app.exit();
        }
        if (optionButton.isChecked()) {
            bgMusic.dispose();
            getEngine().setScreen(new Settings(getEngine()));
            optionButton.setChecked(false);
        }
    }
}
