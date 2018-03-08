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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.dewnaveen.texteditor.R;
import com.dewnaveen.texteditor.ui.CotentList.ContentListActivity;
import com.dewnaveen.texteditor.ui.base.BaseActivity;
import com.dewnaveen.texteditor.ui.main.MainActivity;

import butterknife.ButterKnife;


public class SplashActivity extends BaseActivity {

    private final Handler mHandler = new Handler();

    private final Runnable mOpenActivityTask = () -> openContentListActivity();

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

    private void openContentListActivity() {
        Intent intent = ContentListActivity.getStartIntent(SplashActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacks(mOpenActivityTask);
        super.onDestroy();
    }

    @Override
    protected void setUp() {
        mHandler.removeCallbacks(mOpenActivityTask);
        mHandler.postDelayed(mOpenActivityTask, 1200);
    }



    @Override
    protected void onStop() {
        super.onStop();
    }
}

