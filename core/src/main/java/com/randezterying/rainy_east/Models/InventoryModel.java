package com.randezterying.rainy_east.Models;

public class InventoryModel {

    public Item[] items;

    public InventoryModel(int slots) {items = new Item[slots];}

//    public void saveInventory() {
//        int[] idw = new int[INV_SLOTS];
//        int[] indexw = new int[INV_SLOTS];
//        for (int i = 0; i < INV_SLOTS; i++) {
//            idw[i] = id[i];
//            indexw[i] = index[i];
//        }
//        data.saveInv(idw, indexw, param, INV_SLOTS);
//    }
//
//    public void loadInventory() {
//        int[] idss = new int[INV_SLOTS];
//        int[] indxs = new int[INV_SLOTS];
//        for (int i = 0; i < INV_SLOTS; i++) {
//            idss = data.loadInvIDs(param, INV_SLOTS);
//            indxs = data.loadInvIndx(param, INV_SLOTS);
//        }
//        for (int i = 0; i < INV_SLOTS; i++) {
//            if (idss[i] != -1) {
//                items[i] = new Item(idss[i], indxs[i], param);
//                id[i] = idss[i];
//                index[i] = indxs[i];
//            }
//        }
//    }

    public void addItem(Item item) {
//        if (item.index == -1) {
//            int index = getFirstEmptyCell();
//            item = new Item(item.id, index, item.type);
//            if (index != -1) items[index] = item;
//        } else {
//            if (isFreeSlot(item.index)) items[item.index] = item;
//        }
        items[item.index] = item;
    }

    public void removeItem(int index) {
        if (items[index] != null) {
            items[index] = null;
        }
    }

    public boolean isFull() {
        for (Item item : items) if (item == null) return false;
        return true;
    }

    public int getFirstEmptyCell() {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) return i;
        }
        return -1;
    }

    public Item getItem(int index) {return items[index];}
    private boolean isFreeSlot(int index) {return items[index] == null;}
    public void clear() {for (int i = 0; i < items.length; i++) removeItem(i);}
}