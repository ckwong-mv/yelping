package org.ckwong.yelprunner.storage;

import android.content.Context;
import android.content.SharedPreferences;

import org.ckwong.yelprunner.YelpApplication;

/**
 * Created by ckwong on 10/26/14.
 */
public class SettingStore {
    private static SettingStore ourInstance = new SettingStore();

    public static SettingStore getInstance() {
        return ourInstance;
    }

    static SharedPreferences gPref;
    private SettingStore() {
        gPref = YelpApplication.getInstance().
                getSharedPreferences("YelpSetting", Context.MODE_PRIVATE);
    }

    public String getFindValue() {
        return SettingStoreEntry.FIND_VALUE.getValue();
    }

    public void setFindValue(String value) {
        SettingStoreEntry.FIND_VALUE.setValue(value);
    }

    public String getNearValue() {
        return SettingStoreEntry.NEAR_VALUE.getValue();
    }

    public void setNearValue(String value) {
        SettingStoreEntry.NEAR_VALUE.setValue(value);
    }
}


enum SettingStoreEntry {
    FIND_VALUE(""), NEAR_VALUE("");

    String defaultValue;

    SettingStoreEntry(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    String getValue() {
        return SettingStore.gPref.getString(toString(), defaultValue);
    }

    void setValue(String value) {
        SharedPreferences.Editor editor = SettingStore.gPref.edit();
        editor.putString(toString(), value);
        editor.commit();
    }
}

