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

package com.dewnaveen.texteditor.di.component;

import android.app.Application;
import android.content.Context;

import com.dewnaveen.texteditor.app.MyApplication;
import com.dewnaveen.texteditor.data.DataManager;
import com.dewnaveen.texteditor.di.ApplicationContext;
import com.dewnaveen.texteditor.di.module.ApplicationModule;
import com.dewnaveen.texteditor.service.SyncService;
import com.dewnaveen.texteditor.ui.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MyApplication app);

    void inject(SyncService service);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
}