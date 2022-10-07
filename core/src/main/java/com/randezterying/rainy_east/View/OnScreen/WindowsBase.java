package com.randezterying.rainy_east.View.OnScreen;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.randezterying.rainy_east.Base.ScreenElements.ButtonBase;
import com.randezterying.rainy_east.GameCore.Assets;

import java.util.ArrayList;

public class WindowsBase extends Actor {

    protected Player player;
    public Vector2 lastTouch = new Vector2();
    public Vector2 newTouch;

    public Stage stage;
    public InputMultiplexer windowMultiplexer;

    protected ArrayList<ButtonBase> buttonsList = new ArrayList<>();
    protected ArrayList<Actor> actors = new ArrayList<>();
    protected final Image image;
    public final ButtonBase close;
    public final ButtonBase openPass;
    public final ButtonBase openInv;
    public final ButtonBase drag;

    public boolean isOpened = false;
    public boolean needOpenPass = false;
    public boolean needOpenInv = false;

    public float posX = 600;
    public float posY = 150;

    public WindowsBase(Player player, Texture texture, Stage stage, InputMultiplexer windowMultiplexer) {
        this.player = player;
        this.stage = stage;
        this.windowMultiplexer = windowMultiplexer;

        image = new Image(texture);
        image.setSize(400, 700 * (Assets.h / Assets.w));
        image.setPosition(posX, posY * (Assets.h / Assets.w));
        close = new ButtonBase("Atlas/tr.txt", "tr", posX + 360, posY, 40, 80);
        openPass = new ButtonBase("Atlas/tr.txt", "tr", posX, posY, 120, 80);
        openInv = new ButtonBase("Atlas/tr.txt", "tr", posX + 120, posY, 120, 80);
        drag = new ButtonBase("Atlas/tr.txt", "tr", posX, posY + 620, 400, 80);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isOpened) {
            for (int i = 0; i < buttonsList.size(); i++) buttonsList.get(i).act(parentAlpha);
            for (int i = 0; i < actors.size(); i++) actors.get(i).act(parentAlpha);
        }
        close.act(parentAlpha);
        openPass.act(parentAlpha);
        openInv.act(parentAlpha);
        drag.act(parentAlpha);

        posX = image.getX();
        posY = image.getY();

        if (close.isChecked()) {
            setOpened(false);
            close.setChecked(false);
        }
        addToDraw();
    }

    public void addToDraw() {}

    public void setPos(float x, float y) {
        image.moveBy(x, y);
        close.moveBy(x, y);
        openPass.moveBy(x, y);
        openInv.moveBy(x, y);
        drag.moveBy(x, y);
        for (int i = 0; i < buttonsList.size(); i++) buttonsList.get(i).moveBy(x, y);
        for (int i = 0; i < actors.size(); i++) actors.get(i).moveBy(x, y);
    }

    public void setOpened(boolean isOpened) {
        this.isOpened = isOpened;
        if (isOpened) {
            stage.addActor(image);
            stage.addActor(close);
            stage.addActor(openPass);
            stage.addActor(openInv);
            stage.addActor(drag);
            windowMultiplexer.addProcessor(close.getStage());
            windowMultiplexer.addProcessor(openPass.getStage());
            windowMultiplexer.addProcessor(openInv.getStage());
            windowMultiplexer.addProcessor(drag.getStage());
            for (int i = 0; i < buttonsList.size(); i++) {
                stage.addActor(buttonsList.get(i));
                windowMultiplexer.addProcessor(buttonsList.get(i).getStage());
            }
            for (int i = 0; i < actors.size(); i++) {
                stage.addActor(actors.get(i));
                windowMultiplexer.addProcessor(actors.get(i).getStage());
            }
            addToOpen();
            setTouchDragged(true);
        } else {
            stage.addAction(Actions.removeActor(image));
            stage.addAction(Actions.removeActor(close));
            stage.addAction(Actions.removeActor(openPass));
            stage.addAction(Actions.removeActor(openInv));
            stage.addAction(Actions.removeActor(drag));
            windowMultiplexer.removeProcessor(close.getStage());
            windowMultiplexer.removeProcessor(openPass.getStage());
            windowMultiplexer.removeProcessor(openInv.getStage());
            windowMultiplexer.removeProcessor(drag.getStage());
            for (int i = 0; i < buttonsList.size(); i++) {
                stage.addAction(Actions.removeActor(buttonsList.get(i)));
                windowMultiplexer.removeProcessor(buttonsList.get(i).getStage());
            }
            for (int i = 0; i < actors.size(); i++) {
                stage.addAction(Actions.removeActor(actors.get(i)));
                windowMultiplexer.removeProcessor(actors.get(i).getStage());
            }
            addToClose();
            setTouchDragged(false);
        }
    }

    public void addToOpen() {}
    public void addToClose() {}

    public void addButton(ButtonBase button) {
        buttonsList.add(button);
        stage.addActor(button);
        windowMultiplexer.addProcessor(button.getStage());
    }

    public void removeButton(ButtonBase actor) {
        stage.addAction(Actions.removeActor(actor));
        windowMultiplexer.removeProcessor(actor.getStage());
        buttonsList.remove(actor);
    }

    public void setTouchDragged(boolean isSet) {
        if (isSet) {
            drag.addListener(new DragListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    lastTouch.set(x, y);
                    return true;
                }

                @Override
                public void touchDragged(InputEvent event, float x, float y, int pointer) {
                    newTouch = new Vector2(x, y);
                    Vector2 delta = newTouch.cpy().sub(lastTouch.x, lastTouch.y);

                    drag.moveBy(delta.x, delta.y);
                    openPass.moveBy(delta.x, delta.y);
                    openInv.moveBy(delta.x, delta.y);
                    close.moveBy(delta.x, delta.y);
                    image.moveBy(delta.x, delta.y);
                    for (int i = 0; i < actors.size(); i++) actors.get(i).moveBy(delta.x, delta.y);
                    for (int i = 0; i < buttonsList.size(); i++) buttonsList.get(i).moveBy(delta.x, delta.y);
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    if (newTouch != null) {
                        lastTouch = newTouch;
                        posX = newTouch.x;
                        posY = newTouch.y;
                    }
                }
            });
        } else {
            drag.removeListener();
        }
    }
}