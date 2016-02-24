package com.quicksilver.mbeattie.rxperiment.domain;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public class StringRxInteractor implements RxInteractor<String> {
    private static final String TAG = StringRxInteractor.class.getSimpleName();

    private static final List<String> DEFAULT_STRINGS = new ArrayList<String>() {{
        add("Apple");
        add("Banana");
        add("Celery");
        add("Date");
        add("Eel");
        add("French Fries");
        add("Grapes");
        add("Hummus");
        add("Ice Cream");
        add("Jello");
        add("Kale");
        add("Lettuce");
        add("Marshmallows");
        add("Nectarine");
        add("Orange");
    }};

    private List<String> items;

    public StringRxInteractor() {
        items = DEFAULT_STRINGS;
    }

    public void setStrings(List<String> strings) {
        this.items = strings;
    }

    @Override
    public Observable<String> getObservable() {
        return Observable.defer(() -> Observable.from(items));
    }
}
