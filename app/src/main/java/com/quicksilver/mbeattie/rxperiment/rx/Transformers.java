package com.quicksilver.mbeattie.rxperiment.rx;

import android.os.AsyncTask;

import rx.Observable;
import rx.Observable.Transformer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public final class Transformers {

    private static final Transformer<Observable, Observable> SCHEDULERS_TRANSFORMER_SUBSCRIBE_IO_OBSERVE_MAIN =
            observable -> observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

    private static final Transformer<Observable, Observable> SCHEDULERS_TRANSFORMER_SUBSCRIBE_ASYNC_OBSERVE_MAIN =
            observable -> observable.subscribeOn(Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR))
                    .observeOn(AndroidSchedulers.mainThread());

    private static final Transformer<Observable, Observable> SCHEDULERS_TRANSFORMER_SUBSCRIBE_MAIN_OBSERVE_MAIN =
            observable -> observable.subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread());

    @SuppressWarnings("unchecked")
    public static <T> Transformer<T, T> applySchedulersSubscribeIoObserveMain() {
        return (Transformer<T, T>) SCHEDULERS_TRANSFORMER_SUBSCRIBE_IO_OBSERVE_MAIN;
    }

    @SuppressWarnings("unchecked")
    public static <T> Transformer<T, T> applySchedulersSubscribeAsyncObserveMain() {
        return (Transformer<T, T>) SCHEDULERS_TRANSFORMER_SUBSCRIBE_ASYNC_OBSERVE_MAIN;
    }

    @SuppressWarnings("unchecked")
    public static <T> Transformer<T, T> applySchedulersSubscribeMainObserveMain() {
        return (Transformer<T, T>) SCHEDULERS_TRANSFORMER_SUBSCRIBE_MAIN_OBSERVE_MAIN;
    }

    public static <T> Transformer<T, T> applySchedulers(final Scheduler subscribeScheduler, final Scheduler observeScheduler) {
        return observable -> observable.subscribeOn(subscribeScheduler)
                .observeOn(observeScheduler);
    }

    private Transformers() {}
}
