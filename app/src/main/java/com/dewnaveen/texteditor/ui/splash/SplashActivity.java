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

package com.dewnaveen.texteditor.ui.splash;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.dewnaveen.texteditor.R;
import com.dewnaveen.texteditor.data.DataManager;
import com.dewnaveen.texteditor.rxjava.SchedulerProvider;
import com.dewnaveen.texteditor.ui.base.BaseActivity;
import com.dewnaveen.texteditor.ui.main.MainActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;


public class SplashActivity extends BaseActivity {


    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));


        setUp();
    }

    /**
     * Making the screen wait so that the  branding can be shown
     */
    public void openLoginActivity() {
//        Intent intent = LoginActivity.getStartIntent(SplashActivity.this);
//        startActivity(intent);
//        finish();
    }

    public void openMainActivity() {
        Intent intent = MainActivity.getStartIntent(SplashActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void setUp() {
        openLoginActivity();
    }


}

