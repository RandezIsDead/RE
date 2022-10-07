package com.randezterying.rainy_east.GameCore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.randezterying.rainy_east.Models.Entity;

public class Data {

    public static Preferences data = Gdx.app.getPreferences("Rainy_East");

    public static boolean getBoolean(String key) {
        return data.getBoolean(key);
    }

    public static void putBoolean(String key, boolean value) {
        data.putBoolean(key, value);
        flush();
    }

    public static float getFloat(String key) {
        return data.getFloat(key);
    }

    public static void putFloat(String key, float value) {
        data.putFloat(key, value);
        flush();
    }

    public static int getInteger(String key) {
        return data.getInteger(key);
    }

    public static void putInteger(String key, int value) {
        data.putInteger(key, value);
        flush();
    }

    public static String getString(String key) {
        return data.getString(key);
    }

    public static void putString(String key, String value) {
        data.putString(key, value);
        flush();
    }

    public static float getPrefX() {
        return data.getFloat("playerX");
    }

    public static float getPrefY() {
        return data.getFloat("playerY");
    }

    public static void setPrefX(float x) {
        data.putFloat("playerX", x);
        flush();
    }

    public static void setPrefY(float y) {
        data.putFloat("playerY", y);
        flush();
    }

    public static void savePlayer(Entity entity) {
        // TODO
    }

    public static Entity getPlayer() {
        Entity entity = new Entity();
        return entity;
    }

    public static void flush() {
        data.flush();
    }
}
