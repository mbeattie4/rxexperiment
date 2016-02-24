package com.quicksilver.mbeattie.rxperiment.presentation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.quicksilver.mbeattie.rxperiment.R;
import com.quicksilver.mbeattie.rxperiment.domain.NumberRxInteractor;
import com.quicksilver.mbeattie.rxperiment.domain.StringRxInteractor;
import com.quicksilver.mbeattie.rxperiment.rx.Transformers;
import com.quicksilver.mbeattie.rxperiment.utils.ThreadUtils;

import rx.Subscriber;
import rx.Subscription;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private NumberRxInteractor numberRxInteractor;
    private StringRxInteractor stringRxInteractor;

    private Subscription numberSubscription;
    private Subscription stringSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unsubscribe();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private NumberRxInteractor getNumberInteractor() {
        if (numberRxInteractor == null) {
            numberRxInteractor = new NumberRxInteractor();
        }
        return numberRxInteractor;
    }

    private StringRxInteractor getStringInteractor() {
        if (stringRxInteractor == null) {
            stringRxInteractor = new StringRxInteractor();
        }
        return stringRxInteractor;
    }

    private void subscribe() {
        subscribeToNumberStream();
        subscribeToStringStream();
    }

    private void subscribeToNumberStream() {
        if (numberSubscription != null && !numberSubscription.isUnsubscribed()) {
            Log.e(TAG, "subscribeToNumberStream(): subscriptions already exists. unsubscribing.");
            numberSubscription.unsubscribe();
        }

        numberSubscription = getNumberInteractor().getObservable()
                .compose(Transformers.applySchedulersSubscribeIoObserveMain())
                .subscribe(new Subscriber<Number>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted() called");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e(TAG, "onError() called", throwable);
                    }

                    @Override
                    public void onNext(Number number) {
                        final String thread = (ThreadUtils.isMainThread() ? "MAIN" : "BACKGROUND");
                        Log.d(TAG, "Observing on thread: " + thread);
                        Log.d(TAG, "onNext() called with number: " + number);
                    }
                });
    }

    private void subscribeToStringStream() {
        if (stringSubscription != null && !stringSubscription.isUnsubscribed()) {
            Log.e(TAG, "subscribeToStringStream(): subscriptions already exists. unsubscribing.");
            stringSubscription.unsubscribe();
        }

        stringSubscription = getStringInteractor().getObservable()
                .compose(Transformers.applySchedulersSubscribeIoObserveMain())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted() called");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e(TAG, "onError() called", throwable);
                    }

                    @Override
                    public void onNext(String text) {
                        final String thread = (ThreadUtils.isMainThread() ? "MAIN" : "BACKGROUND");
                        Log.d(TAG, "Observing on thread: " + thread);
                        Log.d(TAG, "onNext() called with string: " + text);
                    }
                });
    }

    private void unsubscribe() {
        if (numberSubscription != null && !numberSubscription.isUnsubscribed()) {
            Log.e(TAG, "unsubscribe(): unsubscribing number subscription now");
            numberSubscription.unsubscribe();
        }

        if (stringSubscription != null && !stringSubscription.isUnsubscribed()) {
            Log.e(TAG, "unsubscribe(): unsubscribing string subscription now");
            stringSubscription.unsubscribe();
        }
    }
}
