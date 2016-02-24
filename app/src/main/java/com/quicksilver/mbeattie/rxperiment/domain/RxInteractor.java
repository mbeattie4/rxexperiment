package com.quicksilver.mbeattie.rxperiment.domain;

import rx.Observable;

public interface RxInteractor<T> {

    Observable<T> getObservable();
}
