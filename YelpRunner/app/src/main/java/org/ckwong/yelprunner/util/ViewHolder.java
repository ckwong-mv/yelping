package org.ckwong.yelprunner.util;

import android.util.SparseArray;
import android.view.View;

/**
 * Created by ckwong on 10/26/14.
 */
public class ViewHolder {

    SparseArray<View> views = new SparseArray<View>();
    private final View parent;

    public ViewHolder(View parent) {
        this.parent = parent;
    }

    public View getView(int id) {
        View v = views.get(id);
        if (v == null) {
            v = parent.findViewById(id);
            views.put(id, v);
        }

        return v;
    }
}
