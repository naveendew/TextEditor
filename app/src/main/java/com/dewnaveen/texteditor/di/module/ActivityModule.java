/*
 * Copyright (c) 2017.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 *
 */

package com.dewnaveen.texteditor.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.dewnaveen.texteditor.di.ActivityContext;
import com.dewnaveen.texteditor.di.PerActivity;
import com.dewnaveen.texteditor.rxjava.AppSchedulerProvider;
import com.dewnaveen.texteditor.rxjava.SchedulerProvider;
import com.dewnaveen.texteditor.ui.CotentList.ContentListActivity;
import com.dewnaveen.texteditor.ui.base.BaseActivity;
import com.dewnaveen.texteditor.ui.main.MainActivity;
import com.dewnaveen.texteditor.ui.splash.SplashActivity;
import com.google.firebase.auth.FirebaseAuth;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import io.realm.Realm;


@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

//    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

/*
    @Provides
    @PerActivity
    FirebaseAuth provideFirebaseAuth() {
        return mAuth;
    }
*/

/*
    @Provides
    @PerActivity
    Realm provideRealm() {
        return Realm.getDefaultInstance();
    }
*/


    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @PerActivity
    BaseActivity provideBaseActivity(
            BaseActivity mActivity) {
        return mActivity;
    }

    @Provides
    @PerActivity
    MainActivity provideMainActivity(
            MainActivity mActivity) {
        return mActivity;
    }


    @Provides
    @PerActivity
    SplashActivity provideSplashActivity(
            SplashActivity mActivity) {
        return mActivity;
    }

    @Provides
    @PerActivity
    ContentListActivity provideContentListActivity(
            ContentListActivity mActivity) {
        return mActivity;
    }


    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
        return new LinearLayoutManager(activity);
    }

}
