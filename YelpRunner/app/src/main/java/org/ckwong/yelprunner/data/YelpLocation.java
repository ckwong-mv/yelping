package org.ckwong.yelprunner.data;

import java.util.List;

/**
 * Created by ckwong on 10/26/14.
 */
public class YelpLocation {
    String city;
    String postalCode;
    String stateCode;

    List<String> displayAddress;

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public List<String> getDisplayAddress() {
        return displayAddress;
    }
}
