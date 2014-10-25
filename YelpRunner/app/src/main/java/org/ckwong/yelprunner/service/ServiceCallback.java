package org.ckwong.yelprunner.service;

public class ServiceCallback<S, F> {

    public void onSuccess(S successObj) {
    }

    public void onFailure(F failureObj) {
    }

    public boolean isCanceled() {
        return false;
    }
}
