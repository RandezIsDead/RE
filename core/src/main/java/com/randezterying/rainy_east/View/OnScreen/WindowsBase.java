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
    public final ButtonBase drag;

    public boolean isOpened = false;

    public float deltaX = 0;
    public float deltaY = 0;
    public float posX;
    public float posY;
    public float x, y, width, height;

    public WindowsBase(Player player, Texture texture, float x, float y, float w, float h,
                       Stage stage, InputMultiplexer windowMultiplexer) {
        this.player = player;
        this.stage = stage;
        this.windowMultiplexer = windowMultiplexer;
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        updatePos(x, y);

        image = new Image(texture);
        image.setSize(w, h * Assets.h/Assets.w);
        image.setPosition(x, y * Assets.h/Assets.w);
        drag = new ButtonBase("Atlas/gameButtons.txt", "tr", x, y+h-90, 360, 90);
        close = new ButtonBase("Atlas/gameButtons.txt", "tr", (x+w)-40, y+h-90, 40, 90);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isOpened) {
            for (int i = 0; i < buttonsList.size(); i++) buttonsList.get(i).act(parentAlpha);
            for (int i = 0; i < actors.size(); i++) actors.get(i).act(parentAlpha);
        }
        close.act(parentAlpha);
        drag.act(parentAlpha);
        updatePos(image.getX(), image.getY());
        deltaX = posX - x;
        deltaY = (posY / (Assets.h/Assets.w)) - y;

        if (close.isChecked()) {
            setOpened(false);
            close.setChecked(false);
        }
        addToDraw();
    }

    private void updatePos(float x, float y) {
        posX = x;
        posY = y;
    }

    public void addToDraw() {}

    public void moveByPos(float deltaX, float deltaY) {
        image.moveBy(deltaX, deltaY);
        close.moveBy(deltaX, deltaY);
        drag.moveBy(deltaX, deltaY);
        for (int i = 0; i < buttonsList.size(); i++) buttonsList.get(i).moveBy(deltaX, deltaY);
        for (int i = 0; i < actors.size(); i++) actors.get(i).moveBy(deltaX, deltaY);
    }

    public void setOpened(boolean isOpened) {
        this.isOpened = isOpened;
        if (isOpened) {
            stage.addActor(image);
            stage.addActor(close);
            stage.addActor(drag);
            windowMultiplexer.addProcessor(close.getStage());
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
            stage.addAction(Actions.removeActor(drag));
            windowMultiplexer.removeProcessor(close.getStage());
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
                    moveByPos(delta.x, delta.y);
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