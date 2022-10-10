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
    public static final AssetDescriptor<Texture> playerBp = new AssetDescriptor<>("playerAssets/bp.png", Texture.class);
    public static final AssetDescriptor<Texture> playerPass = new AssetDescriptor<>("playerAssets/pass.png", Texture.class);
    public static final AssetDescriptor<Texture> playerCraft = new AssetDescriptor<>("playerAssets/craftW.png", Texture.class);
    public static final AssetDescriptor<Texture> descWindow = new AssetDescriptor<>("playerAssets/dw.png", Texture.class);
    public static final AssetDescriptor<Texture> exception = new AssetDescriptor<>("playerAssets/exception.png", Texture.class);

    public static void load() {
        assetManager.load(bg);

        assetManager.load(playerInv);
        assetManager.load(playerBp);
        assetManager.load(playerPass);
        assetManager.load(playerCraft);
        assetManager.load(descWindow);
        assetManager.load(exception);
    }

    public static void dispose() {
        assetManager.dispose();
    }
}