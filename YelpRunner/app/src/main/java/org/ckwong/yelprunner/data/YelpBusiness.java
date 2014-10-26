package org.ckwong.yelprunner.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ckwong on 10/24/14.
 */
public class YelpBusiness {

    List<List<String>> categories;
    String displayPhone;
    String id;
    String imageUrl;
    YelpLocation location;

    String name;
    String phone;

    int reviewCount;
    String snippetImageUrl;

    String snippetText;

    String url;

    @SerializedName("is_closed") boolean closed;

    public List<List<String>> getCategories() {
        return categories;
    }

    public String getDisplayPhone() {
        return displayPhone;
    }

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public YelpLocation getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public String getSnippetImageUrl() {
        return snippetImageUrl;
    }

    public String getSnippetText() {
        return snippetText;
    }

    public String getUrl() {
        return url;
    }

    public boolean isClosed() {
        return closed;
    }

    @Override
    public String toString() {
        return "YelpBusiness{" +
                "categories=" + categories +
                ", displayPhone='" + displayPhone + '\'' +
                ", id='" + id + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", location=" + location +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", reviewCount=" + reviewCount +
                ", snippetImageUrl='" + snippetImageUrl + '\'' +
                ", snippetText='" + snippetText + '\'' +
                ", url='" + url + '\'' +
                ", closed=" + closed +
                '}';
    }
}
