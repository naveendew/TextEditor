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

package com.dewnaveen.texteditor.data.prefs;


import com.dewnaveen.texteditor.data.DataManager;

public interface PreferencesHelper{

    int getCurrentUserLoggedInMode();

    void setCurrentUserLoggedInMode(DataManager.LoggedInMode mode);

    String getCurrentUserId();

    void setCurrentUserId(String userId);

    String getCurrentUserName();

    void setCurrentUserName(String userName);

    String getCurrentUserEmail();

    void setCurrentUserEmail(String email);

    String getCurrentUserProfilePicUrl();

    void setCurrentUserProfilePicUrl(String profilePicUrl);


}
