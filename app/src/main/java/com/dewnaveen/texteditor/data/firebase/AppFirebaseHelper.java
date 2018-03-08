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

package com.dewnaveen.texteditor.data.firebase;

import android.content.Context;
import android.net.Uri;

import com.dewnaveen.texteditor.di.ApplicationContext;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class AppFirebaseHelper implements FirebaseHelper {


    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Inject
    public AppFirebaseHelper(@ApplicationContext Context context) {
//        currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }
/*

    @Override
    public String getGoogleUserDisplayName() {
        return currentUser.getDisplayName();
    }

    @Override
    public String getGoogleUserEmail() {
        return currentUser.getEmail();
    }

    @Override
    public String getGoogleUserPhoneNumber() {
        return currentUser.getPhoneNumber();
    }

    @Override
    public Uri getGoogleUserPhotoUrl() {
        return currentUser.getPhotoUrl();
    }

    @Override
    public String getGoogleUserUid() {
        return currentUser.getUid();
    }
*/
}
