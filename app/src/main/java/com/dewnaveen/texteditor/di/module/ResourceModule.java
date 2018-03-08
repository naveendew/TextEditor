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

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.dewnaveen.texteditor.R;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;



@Module
public class ResourceModule {

    private final AppCompatActivity mActivity;

    public ResourceModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @Named("white")
    int provideColorWhite() {
        return ContextCompat.getColor(mActivity, R.color.white);
    }

}
