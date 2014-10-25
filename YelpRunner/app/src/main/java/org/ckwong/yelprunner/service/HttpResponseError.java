package org.ckwong.yelprunner.service;

/**
 * Created by ckwong on 10/24/14.
 */
public class HttpResponseError extends ServiceError {
    private int responseCode;

    public HttpResponseError(int responseCode) {
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
