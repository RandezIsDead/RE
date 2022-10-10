package com.randezterying.rainy_east.Base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.randezterying.rainy_east.GameCore.Assets;
import com.randezterying.rainy_east.GameCore.Engine;

import java.util.ArrayList;

public class ScreenBase implements Screen {

    protected Engine engine;
    private final SpriteBatch batch = new SpriteBatch();
    private final OrthographicCamera camera;
    private final Stage stage;
    private final InputMultiplexer multiplexer;
    protected BitmapFont font = new BitmapFont();

    private final ArrayList<Actor> screenElements = new ArrayList<>();

    private Image bg;

    public ScreenBase(Engine engine) {
        this.engine = engine;
        stage = new Stage(new StretchViewport(1000, 1000 * Assets.h/Assets.w));
        multiplexer = new InputMultiplexer();
        camera = new OrthographicCamera(1000, 1000 * Assets.h/Assets.w);
    }

    @Override
    public void show() {
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        stage.getViewport().setCamera(camera);
        Gdx.input.setInputProcessor(multiplexer);
        stage.addActor(bg);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
    }

    protected void actFinal(float delta) {
        for (int i = 0; i < screenElements.size(); i++) screenElements.get(i).act(delta);
        stage.act(delta);
        stage.draw();
    }

    protected void setBG(Texture texture) {
        bg = new Image(texture);
        bg.setPosition(0, 0);
        bg.setSize(1000, 1000 * Assets.h/Assets.w);
    }

    protected void draw(Texture texture, float x, float y, float width, float height) {
        batch.draw(texture, x, y, width, height);
    }

    protected void addObject(Actor screenElement) {
        screenElements.add(screenElement);
        stage.addActor(screenElement);
        multiplexer.addProcessor(screenElement.getStage());
    }

    protected void removeObject(Actor screenElement) {
        screenElements.remove(screenElement);
        stage.addAction(Actions.removeActor(screenElement));
        multiplexer.removeProcessor(screenElement.getStage());
    }

    public Engine getEngine() {return engine;}
    public SpriteBatch getBatch() {return batch;}
    public OrthographicCamera getCamera() {return camera;}
    public Stage getStage() {return stage;}
    public InputMultiplexer getMultiplexer() {return multiplexer;}

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        font.dispose();
        batch.dispose();
        stage.dispose();
        engine.dispose();
        screenElements.clear();
    }
}
