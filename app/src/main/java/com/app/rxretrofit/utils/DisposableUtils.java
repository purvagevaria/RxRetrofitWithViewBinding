package com.app.rxretrofit.utils;


import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by ThanhBan on 9/28/2016.
 */

public class DisposableUtils {

    public static void unsubscribeIfNotNull(CompositeDisposable subscription) {
        if (subscription != null && !subscription.isDisposed()) {
            subscription.clear();
        }
    }


    public static CompositeDisposable getNewCompositeSubIfUnsubscribed(CompositeDisposable subscription) {
        if (subscription == null || subscription.isDisposed()) {
            return new CompositeDisposable();
        }
        return subscription;
    }
}
