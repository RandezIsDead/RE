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

    private final Image description, exception;
    private final ButtonBase ok, notOk, close;
    private final ButtonBase bp, augs;
    public InventoryModel inventory;
    public InventoryModel inventoryUsing;
    public InventoryModel backpackInv;

    private boolean isDescOpened = false;
    private boolean isExcOpened = false;

    private int indexTemp = 0;

    public Inventory(Player player, Stage stage, InputMultiplexer windowMultiplexer) {
        super(player, Assets.assetManager.get(Assets.playerInv), stage, windowMultiplexer);

        inventory = new InventoryModel(48);
        inventoryUsing = new InventoryModel(6);
        backpackInv = new InventoryModel(48);

        addItem(new Item(40, inventory.getFirstEmptyCell(), 0));
        addItem(new Item(41, inventory.getFirstEmptyCell(), 0));
        addItem(new Item(42, inventory.getFirstEmptyCell(), 0));
        addItem(new Item(43, inventory.getFirstEmptyCell(), 0));

        addItem(new Item(40, 0, 1));

        description = new Image(Assets.assetManager.get(Assets.descWindow));
        description.setPosition(400, 250 * (Assets.h / Assets.w));
        description.setSize(200, 520 * (Assets.h / Assets.w));

        exception = new Image(Assets.assetManager.get(Assets.exception));
        exception.setPosition(200, 250 * (Assets.h / Assets.w));
        exception.setSize(200, 520 * (Assets.h / Assets.w));

        ok = new ButtonBase("Atlas/smart.txt", "use", 410, 300, 180, 50);
        notOk = new ButtonBase("Atlas/smart.txt", "cancel", 410, 250, 180, 50);
        close = new ButtonBase("Atlas/buttons.txt", "errOk", 210, 710, 180, 50);

        bp = new ButtonBase("Atlas/tr.txt", "tr", 605, 300, 58, 50);
        augs = new ButtonBase("Atlas/tr.txt", "tr", 605, 240, 58, 50);

        buttonsList.add(bp);
        buttonsList.add(augs);

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
        }
    }

    @Override
    public void addToDraw() {
        super.addToDraw();
        if (openPass.isChecked()) {
            setOpened(false);
            needOpenPass = true;
            openPass.setChecked(false);
        }
        if (bp.isChecked()) {
            //
            bp.setChecked(false);
        }
        if (augs.isChecked()) {
            //
            augs.setChecked(false);
        }

//        for (int i = 0; i < inventory.items.length; i++) {
//            if (inventory.items[i] != null) System.out.print(inventory.items[i].id + "-" + inventory.items[i].index + " x:" +
//                    inventory.items[i].button.getX() + " y:" + inventory.items[i].button.getY() + " ");
//        }
//        System.out.print(" ----- ");
//        for (int i = 0; i < inventoryUsing.items.length; i++) {
//            if (inventoryUsing.items[i] != null) System.out.print(inventoryUsing.items[i].id + "-" + inventoryUsing.items[i].index + " x:" +
//                    inventoryUsing.items[i].button.getX() + " y:" + inventoryUsing.items[i].button.getY() + " ");
//        }
//        System.out.println();

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
                    if (close.isChecked()) {
                        closeException();
                        close.setChecked(false);
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
            Item rec = new Item(item.id, index, 1);
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
            Item rec = new Item(item.id, index, 0);
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

    private void showDescription() {
        if (!isDescOpened) {
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
            actors.add(exception);
            buttonsList.add(close);
            getStage().addActor(exception);
            getStage().addActor(close);
            windowMultiplexer.addProcessor(close.getStage());
            isExcOpened = !isExcOpened;
        }
    }

    private void closeException() {
        if (isExcOpened) {
            actors.remove(exception);
            buttonsList.remove(close);
            getStage().addAction(Actions.removeActor(exception));
            getStage().addAction(Actions.removeActor(close));
            windowMultiplexer.removeProcessor(close.getStage());
            isExcOpened = !isExcOpened;
        }
    }

    @Override
    public void addToClose() {
        super.addToClose();
        closeDescription();
        closeException();
    }
}