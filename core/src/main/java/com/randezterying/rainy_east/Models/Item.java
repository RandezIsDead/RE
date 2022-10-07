package com.randezterying.rainy_east.Models;

import com.randezterying.rainy_east.Base.ScreenElements.ButtonBase;

public class Item {

    public ButtonBase button;
    public String drawable;
    public int id;
    public int index;
    public int type;
    public float x;
    public float y;
    public float width;
    public float height;
    private int cost;
    private int buff;
    private int statsID;

    private final float[] gameInvX = {802, 834, 866, 898, 930, 963};
    private final float[] gameInvY = {695, 633, 569, 506, 442, 378, 314, 250};
    private final float[] invUsingX = {608, 747, 619, 613, 747, 747};
    private final float[] invUsingY = {680, 620, 560, 380, 325, 245};

    public Item(int id, int index, int type) {
        this.id = id;
        this.index = index;
        this.type = type;
        setType(type);
        initializeItem(id);
        button = new ButtonBase("smart/items/items.txt", drawable, x, y, width, height);
    }

    public void setType(int type) {
        if (type == 0) itemGameInv();
        if (type == 1) itemInvUsing();
        if (type == 2) itemChipUsing();
        if (type == 3) itemShop();
        if (type == 4) itemOrders();
    }

    private void itemGameInv() {
        width = 29;
        height = 59;
        x = gameInvX[index % gameInvX.length];
        y = gameInvY[index / gameInvX.length];
    }

    private void itemInvUsing() {
        width = 35;
        height = 70;
        x = invUsingX[index];
        y = invUsingY[index];
    }

    private void itemChipUsing() {
        width = 40;
        height = 80;
        x = 655;
        if (index == 0) y = 160;
        if (index == 1) y = 260;
        if (index == 2) y = 355;
        if (index == 3) y = 450;
        if (index == 4) y = 545;
        if (index == 5) y = 640;
        if (index == 6) y = 735;
    }

    private void itemShop() {
        width = 62;
        height = 105;
        if (index >= 0 && index <= 4) y = 709;
        if (index >= 5 && index <= 9) y = 580;
        if (index >= 10 && index <= 14) y = 446;
        if (index >= 15 && index <= 19) y = 312;
        if (index >= 20 && index <= 24) y = 180;
        if (index == 0 || index == 5 || index == 10 || index == 15 || index == 20) x = 191;
        if (index == 1 || index == 6 || index == 11 || index == 16 || index == 21) x = 273;
        if (index == 2 || index == 7 || index == 12 || index == 17 || index == 22) x = 356;
        if (index == 3 || index == 8 || index == 13 || index == 18 || index == 23) x = 438;
        if (index == 4 || index == 9 || index == 14 || index == 19 || index == 24) x = 521;
    }

    private void itemOrders() {
        width = 62;
        height = 105;
        if (index == 0 || index == 1) y = 710;
        if (index == 2 || index == 3) y = 576;
        if (index == 4 || index == 5) y = 444;
        if (index == 6 || index == 7) y = 310;
        if (index == 0 || index == 2 || index == 4 || index == 6) x = 685;
        if (index == 1 || index == 3 || index == 5 || index == 7) x = 767;
    }

    /* type
        0-19 - energy and other drinks
        20-39 - heal and blue blood
        40-79 - augments
        80-89 - weapon(swords)
        90-99 - weapon(guns)
        100-139 - electronics
        140-179 - chips
        180-199 - suit
     */

    private void initializeItem(int id) {
        if (id == 0) {
            drawable = "energy1";
            setCost(50);
        }
        if (id == 1) {
            drawable = "energy2";
            setCost(50);
        }
        if (id == 2) {
            drawable = "energy3";
            setCost(50);
        }
        if (id == 3) {
            drawable = "energy4";
            setCost(50);
        }
        if (id == 4) {
            drawable = "energy5";
            setCost(50);
        }
        if (id == 5) {
            drawable = "energy6";
            setCost(50);
        }
        if (id == 10) {
            drawable = "beer1";
            setCost(100);
        }
        if (id == 10) {
            drawable = "beer2";
            setCost(100);
        }
        if (id == 20) {
            drawable = "heal1";
            setCost(50);
        }
        if (id == 30) {
            drawable = "blueBlood";
            setCost(200);
        }
        if (id == 40) {
            drawable = "brainAug";
            setStatsID(0);
            setCost(2000);
            setBuff(20);
        }
        if (id == 41) {
            drawable = "armsElite";
            setStatsID(1);
            setCost(2000);
            setBuff(40);
        }
        if (id == 42) {
            drawable = "heartAug";
            setStatsID(6);
            setCost(2000);
            setBuff(30);
        }
        if (id == 43) {
            drawable = "legsElite";
            setStatsID(7);
            setCost(2000);
            setBuff(10);
        }
        if (id == 100) {
            drawable = "cpu";
            setCost(5000);
        }
        if (id == 101) {
            drawable = "coil";
            setCost(100);
        }
        if (id == 102) {
            drawable = "diod";
            setCost(100);
        }
        if (id == 103) {
            drawable = "microcircuit";
            setCost(500);
        }
        if (id == 104) {
            drawable = "setOfWires";
            setCost(50);
        }
        if (id == 105) {
            drawable = "transistor";
            setCost(100);
        }
        if (id == 106) {
            drawable = "transistor2";
            setCost(100);
        }
        if (id == 140) {
            drawable = "highJump";
            setCost(1000);
        }
    }

    public int getCost() {return cost;}
    public void setCost(int cost) {this.cost = cost;}
    public int getBuff() {return buff;}
    public void setBuff(int buff) {this.buff = buff;}
    public int getStatsID() {return statsID;}
    public void setStatsID(int statsID) {this.statsID = statsID;}
}