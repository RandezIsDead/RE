package com.randezterying.rainy_east.View.OnScreen.Windows;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.randezterying.rainy_east.Base.ScreenElements.ButtonBase;
import com.randezterying.rainy_east.GameCore.Assets;
import com.randezterying.rainy_east.GameCore.Data;
import com.randezterying.rainy_east.View.OnScreen.Player;
import com.randezterying.rainy_east.View.OnScreen.WindowsBase;

public class Stats extends WindowsBase {

    private static final ButtonBase name = new ButtonBase("Atlas/tr.txt", "", "tr", 680, 625, 250, 20);

    private static final ButtonBase maxHP = new ButtonBase("Atlas/tr.txt", "", "tr", 610, 530, 130, 20);
    private static final ButtonBase maxSP = new ButtonBase("Atlas/tr.txt", "", "tr", 610, 500, 130, 20);
    private static final ButtonBase damage = new ButtonBase("Atlas/tr.txt", "", "tr", 610, 470, 130, 20);
    private static final ButtonBase defence = new ButtonBase("Atlas/tr.txt", "", "tr", 610, 440, 130, 20);
    private static final ButtonBase healing = new ButtonBase("Atlas/tr.txt", "", "tr", 610, 410, 130, 20);
    private static final ButtonBase agility = new ButtonBase("Atlas/tr.txt", "", "tr", 610, 380, 130, 20);

    public Stats(Player player, Stage stage, InputMultiplexer windowMultiplexer) {
        super(player, Assets.assetManager.get(Assets.playerPass), stage, windowMultiplexer);
        this.windowMultiplexer = windowMultiplexer;
        this.player = player;

        name.button.getLabel().setText("Name: Jason Brody" + Data.getString("playerUsername"));

        buttonsList.add(name);
        buttonsList.add(maxHP);
        buttonsList.add(maxSP);
        buttonsList.add(damage);
        buttonsList.add(defence);
        buttonsList.add(healing);
        buttonsList.add(agility);

        for (int i = 0; i < buttonsList.size(); i++) {
            buttonsList.get(i).button.getLabel().setAlignment(Align.left);
        }
    }

    @Override
    public void addToDraw() {
        maxHP.setText("MaxHP: " + player.getMaxHp());
        maxSP.setText("MaxSP: " + player.getMaxSp());
        damage.setText("Damage: " + player.getDamage());
        defence.setText("Defence: " + player.getDefence());
        healing.setText("Healing: " + player.getHealing());
        agility.setText("Agility: " + player.getAgility());

        if (openInv.isChecked()) {
            setOpened(false);
            needOpenInv = true;
            openInv.setChecked(false);
        }
    }
}