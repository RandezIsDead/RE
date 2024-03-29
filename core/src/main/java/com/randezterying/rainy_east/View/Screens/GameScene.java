package com.randezterying.rainy_east.View.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.randezterying.rainy_east.Base.ScreenBase;
import com.randezterying.rainy_east.Base.ScreenElements.ButtonBase;
import com.randezterying.rainy_east.GameCore.Assets;
import com.randezterying.rainy_east.GameCore.Engine;
import com.randezterying.rainy_east.View.OnScreen.Player;
import com.randezterying.rainy_east.View.OnScreen.Windows.Craft;
import com.randezterying.rainy_east.View.OnScreen.Windows.Inventory;
import com.randezterying.rainy_east.View.OnScreen.Windows.Stats;

public class GameScene extends ScreenBase {

    private final InputMultiplexer windowsMultiplexer;
    private final Player player;
    private final Inventory inventory;
    private final Stats stats;
    private final Craft craft;

    private final ButtonBase invButton, statsButton, craftButton;
    private final ButtonBase plus, minus, scaleText, movePointTouch;
    private int scale = 100;

    private float movePointX = 0;
    private float movePointY = 0;

    public GameScene(Engine engine) {
        super(engine);
        setBG(Assets.assetManager.get(Assets.bg));

        windowsMultiplexer = new InputMultiplexer();

        player = new Player();
        inventory = new Inventory(player, getStage(), windowsMultiplexer);
        stats = new Stats(player, getStage(), windowsMultiplexer);
        craft = new Craft(player, getStage(), windowsMultiplexer);

        invButton = new ButtonBase("Atlas/gameButtons.txt", "bp", 940, 10, 50, 100);
        statsButton = new ButtonBase("Atlas/gameButtons.txt", "stats", 940, 120, 50, 100);
        craftButton = new ButtonBase("Atlas/gameButtons.txt", "craft", 940, 230, 50, 100);
        scaleText = new ButtonBase("Atlas/gameButtons.txt", "tr", 10, 390, 50, 100);
        plus = new ButtonBase("Atlas/gameButtons.txt", "plus", 10, 500, 50, 100);
        minus = new ButtonBase("Atlas/gameButtons.txt", "minus", 10, 610, 50, 100);
        movePointTouch = new ButtonBase("Atlas/gameButtons.txt", "tr", 0, 0, 1000, 1000);

        scaleText.setText(scale + "%");
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(getMultiplexer());

        getStage().addActor(player);
        getStage().addActor(inventory);
        getStage().addActor(stats);
        getStage().addActor(craft);

        addObject(invButton);
        addObject(statsButton);
        addObject(craftButton);
        addObject(plus);
        addObject(minus);
        addObject(scaleText);
        addObject(movePointTouch);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        super.actFinal(delta);
        player.camFreeze(getCamera());

        movePointTouch.addListener(new DragListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                float cX = 0;
                float cY = 0;

                if (x < 500) cX = (500 - x) * -1;
                if (x > 500) cX = x - 500;
                if (y < 500 * Assets.h/Assets.w) cY = ((500 * Assets.h/Assets.w) - y) * -1;
                if (y > 500 * Assets.h/Assets.w) cY = y - (500 * Assets.h/Assets.w);
                movePointX = (int) cX + player.getX();
                movePointY = (int) cY + player.getY();
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {}

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }
        });

        if (!player.isDead()) {
            player.setMovePointX(movePointX);
            player.setMovePointY(movePointY);
        }

        if (plus.isChecked()) {
            getCamera().viewportWidth += 100;
            getCamera().viewportHeight += 100 * Assets.h/Assets.w;
            getCamera().update();
            scale += 10;
            scaleText.setText(scale + "%");
            plus.setChecked(false);
        }
        if (minus.isChecked()) {
            getCamera().viewportWidth -= 100;
            getCamera().viewportHeight -= 100 * Assets.h/Assets.w;
            getCamera().update();
            scale -= 10;
            scaleText.setText(scale + "%");
            minus.setChecked(false);
        }

        if (invButton.isChecked() || Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
            Gdx.input.setInputProcessor(windowsMultiplexer);
            inventory.setOpened(true);
            invButton.setChecked(false);
        }
        if (statsButton.isChecked()) {
            Gdx.input.setInputProcessor(windowsMultiplexer);
            stats.setOpened(true);
            statsButton.setChecked(false);
        }
        if (craftButton.isChecked()) {
            Gdx.input.setInputProcessor(windowsMultiplexer);
            craft.setOpened(true);
            craftButton.setChecked(false);
        }
        if (!stats.isOpened && !inventory.isOpened && !craft.isOpened) Gdx.input.setInputProcessor(getMultiplexer());
    }
}