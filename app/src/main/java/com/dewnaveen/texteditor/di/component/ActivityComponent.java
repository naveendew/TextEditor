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

import com.dewnaveen.texteditor.di.PerActivity;
import com.dewnaveen.texteditor.di.module.ActivityModule;
import com.dewnaveen.texteditor.di.module.ResourceModule;
import com.dewnaveen.texteditor.ui.CotentList.ContentListActivity;
import com.dewnaveen.texteditor.ui.base.BaseActivity;
import com.dewnaveen.texteditor.ui.main.MainActivity;
import com.dewnaveen.texteditor.ui.splash.SplashActivity;

import dagger.Component;


@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class,ResourceModule.class})
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(SplashActivity activity);

    void inject(BaseActivity activity);

    void inject(ContentListActivity activity);


}
