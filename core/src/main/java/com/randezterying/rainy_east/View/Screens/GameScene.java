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
import com.randezterying.rainy_east.View.OnScreen.Windows.Inventory;
import com.randezterying.rainy_east.View.OnScreen.Windows.Stats;

public class GameScene extends ScreenBase {

    private final InputMultiplexer windowsMultiplexer;
    private final Player player;
    private final Inventory inventory;
    private final Stats stats;

    private final ButtonBase invButton;
    private final ButtonBase movePointTouch;

    private float movePointX = 0;
    private float movePointY = 0;

    public GameScene(Engine engine) {
        super(engine);
        setBG(Assets.assetManager.get(Assets.bg));

        windowsMultiplexer = new InputMultiplexer();

        player = new Player();
        inventory = new Inventory(player, getStage(), windowsMultiplexer);
        stats = new Stats(player, getStage(), windowsMultiplexer);

        invButton = new ButtonBase("Atlas/buttons.txt", "smartButton", 950, 50, 50, 200);
        movePointTouch = new ButtonBase("Atlas/tr.txt", "tr", 0, 0, 1000, 1000);
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(getMultiplexer());

        getStage().addActor(player);
        getStage().addActor(inventory);
        getStage().addActor(stats);

        addObject(invButton);
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
                if (y < 500 * (Assets.h / Assets.w)) cY = ((500 * (Assets.h / Assets.w)) - y) * -1;
                if (y > 500 * (Assets.h / Assets.w)) cY = y - (500 * (Assets.h / Assets.w));
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

        if (invButton.isChecked() || Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
            Gdx.input.setInputProcessor(windowsMultiplexer);
            inventory.setOpened(true);
            invButton.setChecked(false);
        }
        if (inventory.needOpenPass) {
            stats.setPos(inventory.posX - stats.posX, inventory.posY - stats.posY);
            stats.setOpened(true);
            inventory.needOpenPass = false;
        }
        if (stats.needOpenInv) {
            inventory.setPos(stats.posX - inventory.posX, stats.posY - inventory.posY);
            inventory.setOpened(true);
            stats.needOpenInv = false;
        }
        if (!stats.isOpened && !inventory.isOpened) Gdx.input.setInputProcessor(getMultiplexer());
    }
}