package com.quicksilver.mbeattie.rxperiment.domain;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public class NumberRxInteractor implements RxInteractor<Number> {
    private static final String TAG = NumberRxInteractor.class.getSimpleName();

    private static final List<Number> DEFAULT_NUMBERS = new ArrayList<Number>() {{
        add(1);
        add(2);
        add(3);
        add(4);
        add(5);
        add(6);
        add(7);
        add(8);
        add(9);
        add(10);
        add(11);
        add(12);
        add(13);
        add(14);
        add(15);
    }};

    private List<Number> items;

    public NumberRxInteractor() {
        items = DEFAULT_NUMBERS;
    }

    public void setNumbers(List<Number> numbers) {
        this.items = numbers;
    }

    @Override
    public Observable<Number> getObservable() {
        return Observable.defer(() -> Observable.from(items));
    }
}