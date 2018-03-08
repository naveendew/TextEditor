
package com.dewnaveen.texteditor.app;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor.Level;
import com.dewnaveen.texteditor.BuildConfig;
import com.dewnaveen.texteditor.data.DataManager;
import com.dewnaveen.texteditor.di.component.ApplicationComponent;
import com.dewnaveen.texteditor.di.component.DaggerApplicationComponent;
import com.dewnaveen.texteditor.di.module.ApplicationModule;
import com.dewnaveen.texteditor.utils.AppLogger;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class MyApplication extends Application {

    @Inject
    DataManager mDataManager;

    @Inject
    CalligraphyConfig mCalligraphyConfig;

    @Inject
    RealmConfiguration realmConfiguration;

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();


        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        Realm.init(this);

        mApplicationComponent.inject(this);

        AppLogger.init();

        AndroidNetworking.initialize(getApplicationContext());

        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(Level.BODY);
        }

        CalligraphyConfig.initDefault(mCalligraphyConfig);
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }


    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
