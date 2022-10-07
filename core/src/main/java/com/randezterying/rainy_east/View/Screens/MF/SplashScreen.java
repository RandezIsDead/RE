package com.randezterying.rainy_east.View.Screens.MF;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.randezterying.rainy_east.Base.ScreenBase;
import com.randezterying.rainy_east.GameCore.Engine;

public class SplashScreen extends ScreenBase {

    private float x = 0;

    public SplashScreen(Engine engine) {
        super(engine);
        setBG(new Texture(Gdx.files.internal("screenAssets/splash.png")));
        firstLaunchInitialize();
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        actFinal(delta);
        x += Gdx.graphics.getDeltaTime();
        if (x >= 2) getEngine().setScreen(new Main(getEngine()));
    }

    private void firstLaunchInitialize() {
//        if (Data.getInteger("gameLaunches") == 0) {
//            InventoryModel inventory = new InventoryModel(getStage(), getMultiplexer(), 0);
//            InventoryModel chips = new InventoryModel(getStage(), getMultiplexer(), 10);
//            InventoryModel invUsing = new InventoryModel(getStage(), getMultiplexer(), 1);
//            InventoryModel invChipsUsing = new InventoryModel(getStage(), getMultiplexer(), 2);
//            inventory.saveInventory();
//            chips.saveInventory();
//            invChipsUsing.saveInventory();
//            invUsing.saveInventory();
//
//            Data.putString("intelligence", Integer.valueOf(10).toString());
//            Data.putString("maxHP", Integer.valueOf(100).toString());
//            Data.putString("maxSP", Integer.valueOf(100).toString());
//            Data.putString("realHP", Integer.valueOf(100).toString());
//            Data.putString("realSP", Integer.valueOf(100).toString());
//            Data.putString("healing", Integer.valueOf(10).toString());
//            Data.putString("strength", Integer.valueOf(10).toString());
//            Data.putString("damage", Integer.valueOf(10).toString());
//            Data.putString("defence", Integer.valueOf(10).toString());
//            Data.putString("agility", Integer.valueOf(10).toString());
//            Data.putString("skOp", Integer.valueOf(0).toString());
//            Data.putString("luck", Integer.valueOf(10).toString());
//        }
    }
}
