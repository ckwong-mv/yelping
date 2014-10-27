package org.ckwong.yelprunner;

import android.app.Application;

/**
 * Created by ckwong on 10/26/14.
 */
public class YelpApplication extends Application {

    private static YelpApplication gInstance;

    static public YelpApplication getInstance() {
        return gInstance;
    }

    public YelpApplication() {
        gInstance = this;
    }
}
