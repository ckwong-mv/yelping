package org.ckwong.yelprunner.service;

/**
 * Created by ckwong on 10/24/14.
 */
public class ServiceError {
    private String message;
    private Exception cause;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCause(Exception cause) {
        this.cause = cause;
    }

    public String getMessage() {
        return message;
    }

    public Exception getCause() {
        return cause;
    }
}
