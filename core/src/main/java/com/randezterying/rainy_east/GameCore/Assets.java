package com.randezterying.rainy_east.GameCore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class Assets {

    public static AssetManager assetManager = new AssetManager();

    public static float w = Gdx.graphics.getWidth();
    public static float h = Gdx.graphics.getHeight();

    public static final AssetDescriptor<Texture> bg = new AssetDescriptor<>("screenAssets/bg.png", Texture.class);

    public static final AssetDescriptor<Texture> playerInv = new AssetDescriptor<>("playerAssets/inv.png", Texture.class);
    public static final AssetDescriptor<Texture> playerPass = new AssetDescriptor<>("playerAssets/pass.png", Texture.class);
    public static final AssetDescriptor<Texture> descWindow = new AssetDescriptor<>("smart/descriptionWindow.png", Texture.class);
    public static final AssetDescriptor<Texture> exception = new AssetDescriptor<>("smart/exception.png", Texture.class);

    public static void load() {
        assetManager.load(bg);

        assetManager.load(playerInv);
        assetManager.load(playerPass);
        assetManager.load(descWindow);
        assetManager.load(exception);
    }

    public static void dispose() {
        assetManager.dispose();
    }
}