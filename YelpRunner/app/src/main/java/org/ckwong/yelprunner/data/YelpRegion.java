package org.ckwong.yelprunner.data;

/**
 * Created by ckwong on 10/24/14.
 */
public class YelpRegion {

    Center center;
    Span span;

    public Center getCenter() {
        return center;
    }

    public Span getSpan() {
        return span;
    }

    @Override
    public String toString() {
        return "YelpRegion{" +
                "center=" + center +
                ", span=" + span +
                '}';
    }

    static public class Center {
        double latitude;
        double longitude;

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        @Override
        public String toString() {
            return "Center{" +
                    "latitude=" + latitude +
                    ", longitude=" + longitude +
                    '}';
        }
    }

    static public class Span {
        double latitudeDelta;
        double longitudeDelta;

        public double getLatitudeDelta() {
            return latitudeDelta;
        }

        public double getLongitudeDelta() {
            return longitudeDelta;
        }

        @Override
        public String toString() {
            return "Span{" +
                    "latitudeDelta=" + latitudeDelta +
                    ", longitudeDelta=" + longitudeDelta +
                    '}';
        }
    }
}
