package com.randezterying.rainy_east.View.OnScreen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.randezterying.rainy_east.GameCore.Assets;
import com.randezterying.rainy_east.Models.Entity;

public class Player extends Entity {

    private final Texture movePoint;

    public Player() {
        super(0, 0, 0.5f);
        movePoint = new Texture("playerAssets/movePoint.png");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!isStanding) batch.draw(movePoint, getMovePointX()-12.5f, getMovePointY(), 50, 50*Assets.h/Assets.w);
        super.draw(batch, parentAlpha);
    }

    public void camFreeze(OrthographicCamera camera) {
        if (camera.position.x < getX() + getWidth()/2)
            camera.position.set(getX() + getWidth()/2, camera.position.y,0);
        if (camera.position.x > getX() + getWidth()/2)
            camera.position.set(getX() + getWidth()/2, camera.position.y,0);
        if (camera.position.y < getY()) camera.position.set(camera.position.x, getY(),0);
        if (camera.position.y > (getY())) camera.position.set(camera.position.x, getY(),0);
    }
}