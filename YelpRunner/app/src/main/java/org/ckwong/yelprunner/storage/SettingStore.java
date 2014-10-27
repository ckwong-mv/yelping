package org.ckwong.yelprunner.storage;

import android.content.Context;
import android.content.SharedPreferences;

import org.ckwong.yelprunner.YelpApplication;

/**
 * Created by ckwong on 10/26/14.
 */
public class SettingStore {
    private static SettingStore gInstance = new SettingStore();

    public static SettingStore getInstance() {
        return gInstance;
    }

    static SharedPreferences gPref;

    private SettingStore() {
        gPref = YelpApplication.getInstance().
                getSharedPreferences("YelpSetting", Context.MODE_PRIVATE);
    }

    public String getFindValue() {
        return SettingStringEntry.FIND.getValue();
    }

    public void setFindValue(String value) {
        SettingStringEntry.FIND.setValue(value);
    }

    public String getNearValue() {
        return SettingStringEntry.NEAR.getValue();
    }

    public void setNearValue(String value) {
        SettingStringEntry.NEAR.setValue(value);
    }
}


enum SettingStringEntry {
    FIND(""), NEAR("");

    String defaultValue;

    SettingStringEntry(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    String getValue() {
        return SettingStore.gPref.getString(toString(), defaultValue);
    }

    void setValue(String value) {
        SharedPreferences.Editor editor = SettingStore.gPref.edit();
        editor.putString(toString(), value);
        editor.apply();
    }
}

