package com.randezterying.rainy_east.View.OnScreen.Windows;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.randezterying.rainy_east.GameCore.Assets;
import com.randezterying.rainy_east.View.OnScreen.Player;
import com.randezterying.rainy_east.View.OnScreen.WindowsBase;

public class Craft extends WindowsBase {

    public Craft(Player player, Stage stage, InputMultiplexer windowMultiplexer) {
        super(player, Assets.assetManager.get(Assets.playerCraft), 600, 250, 400, 600, stage, windowMultiplexer);
        this.windowMultiplexer = windowMultiplexer;
        this.player = player;
    }
}
