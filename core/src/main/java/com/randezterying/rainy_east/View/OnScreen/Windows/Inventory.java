package com.randezterying.rainy_east.View.OnScreen.Windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.randezterying.rainy_east.Base.ScreenElements.ButtonBase;
import com.randezterying.rainy_east.GameCore.Assets;
import com.randezterying.rainy_east.Models.InventoryModel;
import com.randezterying.rainy_east.Models.Item;
import com.randezterying.rainy_east.View.OnScreen.Player;
import com.randezterying.rainy_east.View.OnScreen.WindowsBase;

public class Inventory extends WindowsBase {

    private final Image backpack, description, exception;
    private final ButtonBase bp, ok, notOk, errOk, closeBP;
    public InventoryModel inventory, inventoryUsing, bpInv;

    private int totalWeight = 0;

    private boolean isBpOpened = false;
    private boolean isDescOpened = false;
    private boolean isExcOpened = false;

    private int indexTemp = 0;

    public Inventory(Player player, Stage stage, InputMultiplexer windowMultiplexer) {
        super(player, Assets.assetManager.get(Assets.playerInv), 600, 230, 400, 620, stage, windowMultiplexer);

        inventory = new InventoryModel(48);
        inventoryUsing = new InventoryModel(6);
        bpInv = new InventoryModel(48);

        addItem(new Item(40, inventory.getFirstEmptyCell(), 0));
        addItem(new Item(41, inventory.getFirstEmptyCell(), 0));
        addItem(new Item(42, inventory.getFirstEmptyCell(), 0));
        addItem(new Item(43, inventory.getFirstEmptyCell(), 0));
        addItem(new Item(40, 0, 1));
        addItem(new Item(40, bpInv.getFirstEmptyCell(), 3));

        backpack = new Image(Assets.assetManager.get(Assets.playerBp));
        backpack.setPosition(posX-180, posY * Assets.h/Assets.w);
        backpack.setSize(180, 620 * Assets.h/Assets.w);

        description = new Image(Assets.assetManager.get(Assets.descWindow));
        description.setPosition(posX-180, posY * Assets.h/Assets.w);
        description.setSize(180, 620 * Assets.h/Assets.w);

        exception = new Image(Assets.assetManager.get(Assets.exception));
        exception.setPosition(posX-320, posY * Assets.h/Assets.w);
        exception.setSize(180, 620 * Assets.h/Assets.w);

        bp = new ButtonBase("Atlas/gameButtons.txt", "tr", posX+5, posY+10, 58, 50);
        notOk = new ButtonBase("Atlas/gameButtons.txt", "tr", posX-180, posY, 180, 60);
        ok = new ButtonBase("Atlas/gameButtons.txt", "tr", posX-180, posY+60, 180, 60);
        errOk = new ButtonBase("Atlas/gameButtons.txt", "tr", posX-320, posY, 180, 620);
        closeBP = new ButtonBase("Atlas/gameButtons.txt", "tr", posX-320, posY, 180, 70);

        buttonsList.add(bp);

//        if (Gdx.app.getPreferences("Rainy_East").getInteger("gameLaunches") != 0) {
//            inventory.loadInventory();
//            inventoryUsing.loadInventory();
//        }
    }

    public void addItem(Item item) {
        if (item.type == 1) {
            inventoryUsing.addItem(item);
            buttonsList.add(item.button);
        } else if (item.type == 0) {
            inventory.addItem(item);
            buttonsList.add(item.button);
        } else if (item.type == 3) bpInv.addItem(item);
    }

    @Override
    public void addToDraw() {
        super.addToDraw();
        if (bp.isChecked()) {
            openBackpack();
            bp.setChecked(false);
        }
        if (closeBP.isChecked()) {
            closeBackpack();
            closeBP.setChecked(false);
        }

        for (int i = 0; i < inventory.items.length; i++)
            if (inventory.items[i] != null) totalWeight += inventory.items[i].getWeight();
        for (int i = 0; i < inventoryUsing.items.length; i++)
            if (inventoryUsing.items[i] != null) totalWeight += inventoryUsing.items[i].getWeight();
        for (int i = 0; i < bpInv.items.length; i++)
            if (bpInv.items[i] != null) totalWeight += bpInv.items[i].getWeight();
        player.setTookWeight(totalWeight);
        totalWeight = 0;

        if (isOpened) {
            for (int i = 0; i < inventory.items.length; i++) {
                if (inventory.items[i] != null) {
                    if (inventory.items[i].button.isChecked()) {
                        showDescription();
                        indexTemp = i;
                        inventory.items[i].button.setChecked(false);
                    }
                    if (ok.isChecked()) {
                        closeDescription();
                        useItem(inventory.items[indexTemp]);
                        ok.setChecked(false);
                    }
                    if (notOk.isChecked()) {
                        closeDescription();
                        notOk.setChecked(false);
                    }
                    if (errOk.isChecked()) {
                        closeException();
                        errOk.setChecked(false);
                    }
                }
            }
            for (int i = 0; i < inventoryUsing.items.length; i++) {
                if (inventoryUsing.items[i] != null) {
                    if (inventoryUsing.items[i] != null) {
                        if (inventoryUsing.items[i].button.isChecked()) {
                            inventoryUsing.items[i].button.setChecked(false);
                            indexTemp = i;
                            removeFromUsing(inventoryUsing.items[i]);
                        }
                    }
                }
            }
        }
    }

    private void useItem(Item item) {
        int index = -1;
        if (item.id == 40) index = 0;
        if (item.id == 41) index = 1;
        if (item.id == 42) index = 2;
        if (item.id == 43) index = 3;
        if (item.id == 0) index = 4;

        if (inventoryUsing.items[index] != null) showException();
        else {
            Item rec = new Item(item.id, index, 1, deltaX, deltaY);
            inventoryUsing.addItem(rec);
            addButton(rec.button);
            inventory.removeItem(indexTemp);
            removeButton(item.button);
            Gdx.input.setInputProcessor(windowMultiplexer);
            indexTemp = -1;

//            if (rec.getStatsID() == 0) player.;
//            if (rec.getStatsID() == 1) data.putString("strength", Integer.valueOf(Integer.parseInt(data.getString("strength")) + rec.getBuff()).toString());
//            if (rec.getStatsID() == 6) data.putString("healing", Integer.valueOf(Integer.parseInt(data.getString("healing")) + rec.getBuff()).toString());
//            if (rec.getStatsID() == 7) data.putString("agility", Integer.valueOf(Integer.parseInt(data.getString("agility")) + rec.getBuff()).toString());

//            inventory.saveInventory();
//            inventoryUsing.saveInventory();
        }
    }

    private void removeFromUsing(Item item) {
        int index = inventory.getFirstEmptyCell();

        if (index != -1) {
            Item rec = new Item(item.id, index, 0, deltaX, deltaY);
            inventory.addItem(rec);
            addButton(rec.button);
            inventoryUsing.removeItem(indexTemp);
            removeButton(item.button);
            Gdx.input.setInputProcessor(windowMultiplexer);
            indexTemp = -1;

//            if (rec.getStatsID() == 0) data.putString("intelligence", Integer.valueOf(Integer.parseInt(data.getString("intelligence")) - rec.getBuff()).toString());
//            if (rec.getStatsID() == 1) data.putString("strength", Integer.valueOf(Integer.parseInt(data.getString("strength")) - rec.getBuff()).toString());
//            if (rec.getStatsID() == 6) data.putString("healing", Integer.valueOf(Integer.parseInt(data.getString("healing")) - rec.getBuff()).toString());
//            if (rec.getStatsID() == 7) data.putString("agility", Integer.valueOf(Integer.parseInt(data.getString("agility")) - rec.getBuff()).toString());

//            inventory.saveInventory();
//            inventoryUsing.saveInventory();
        } else {
            closeException();
        }
    }

    private void openBackpack() {
        if (!isBpOpened) {
            backpack.setPosition(posX-180, posY);
            closeBP.setPosition(posX-180, posY);
            actors.add(backpack);
            addButton(closeBP);
            getStage().addActor(backpack);
            for (int i = 0; i < bpInv.items.length; i++)
                if (bpInv.items[i] != null) addButton(bpInv.items[i].button);
            isBpOpened = !isBpOpened;
        }
    }

    private void closeBackpack() {
        if (isBpOpened) {
            actors.remove(backpack);
            removeButton(closeBP);
            getStage().addAction(Actions.removeActor(backpack));
            for (int i = 0; i < bpInv.items.length; i++)
                if (bpInv.items[i] != null) removeButton(bpInv.items[i].button);
            isBpOpened = !isBpOpened;
        }
    }

    private void showDescription() {
        if (!isDescOpened) {
            description.setPosition(posX-180, posY);
            ok.setPosition(posX-180, posY+(60*Assets.h/Assets.w));
            notOk.setPosition(posX-180, posY);
            actors.add(description);
            buttonsList.add(ok);
            buttonsList.add(notOk);
            getStage().addActor(description);
            getStage().addActor(ok);
            getStage().addActor(notOk);
            windowMultiplexer.addProcessor(ok.getStage());
            windowMultiplexer.addProcessor(notOk.getStage());
            isDescOpened = !isDescOpened;
        }
    }

    private void closeDescription() {
        if (isDescOpened) {
            actors.remove(description);
            buttonsList.remove(ok);
            buttonsList.remove(notOk);
            getStage().addAction(Actions.removeActor(description));
            getStage().addAction(Actions.removeActor(ok));
            getStage().addAction(Actions.removeActor(notOk));
            windowMultiplexer.removeProcessor(ok.getStage());
            windowMultiplexer.removeProcessor(notOk.getStage());
            isDescOpened = !isDescOpened;
        }
    }

    private void showException() {
        if (!isExcOpened) {
            exception.setPosition(posX-320, posY);
            errOk.setPosition(posX-320, posY);
            actors.add(exception);
            buttonsList.add(errOk);
            getStage().addActor(exception);
            getStage().addActor(errOk);
            windowMultiplexer.addProcessor(errOk.getStage());
            isExcOpened = !isExcOpened;
        }
    }

    private void closeException() {
        if (isExcOpened) {
            actors.remove(exception);
            buttonsList.remove(errOk);
            getStage().addAction(Actions.removeActor(exception));
            getStage().addAction(Actions.removeActor(errOk));
            windowMultiplexer.removeProcessor(errOk.getStage());
            isExcOpened = !isExcOpened;
        }
    }

    @Override
    public void addToClose() {
        super.addToClose();
        closeBackpack();
        closeDescription();
        closeException();
    }
}