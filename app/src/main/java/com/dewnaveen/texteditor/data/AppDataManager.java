
package com.dewnaveen.texteditor.data;


import android.content.Context;
import android.net.Uri;

import com.dewnaveen.texteditor.data.db.DbHelper;
import com.dewnaveen.texteditor.data.db.model.ContentListResponse;
import com.dewnaveen.texteditor.data.db.model.Data;
import com.dewnaveen.texteditor.data.db.model.GetContentByIdServer;
import com.dewnaveen.texteditor.data.db.model.PostContentRequest;
import com.dewnaveen.texteditor.data.db.model.PostContentResponse;
import com.dewnaveen.texteditor.data.firebase.FirebaseHelper;
import com.dewnaveen.texteditor.data.network.ApiHeader;
import com.dewnaveen.texteditor.data.network.ApiHelper;
import com.dewnaveen.texteditor.data.network.model.PortfolioResponse;
import com.dewnaveen.texteditor.data.prefs.PreferencesHelper;
import com.dewnaveen.texteditor.di.ApplicationContext;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.realm.RealmResults;


@Singleton
public class AppDataManager implements DataManager {

    private static final String TAG = "AppDataManager";

    private final Context mContext;
    private final DbHelper mDbHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final ApiHelper mApiHelper;
    private final FirebaseHelper mFireBaseHelper;


    @Inject
    public AppDataManager(@ApplicationContext Context context,
                          DbHelper dbHelper,
                          PreferencesHelper preferencesHelper,
                          ApiHelper apiHelper, FirebaseHelper fireBaseHelper) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
        mFireBaseHelper = fireBaseHelper;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHelper.getApiHeader();
    }

    @Override
    public Observable<PostContentResponse> postContenttoServer(PostContentRequest request) {
        return mApiHelper.postContenttoServer(request);
    }

    @Override
    public Observable<ContentListResponse> gettContentListfromServer() {
        return mApiHelper.gettContentListfromServer();
    }

    @Override
    public void upDateContentListRealm(ContentListResponse contentListResponse) {
        mApiHelper.upDateContentListRealm(contentListResponse);
    }

    @Override
    public ContentListResponse getContentListRealm() {
        return mApiHelper.getContentListRealm();
    }

    @Override
    public Observable<GetContentByIdServer> getContentByIdfromServer(int content_id) {
        return mApiHelper.getContentByIdfromServer(content_id);
    }

    @Override
    public Data getContentByIdRealm(int content_id) {
        return mApiHelper.getContentByIdRealm(content_id);
    }

    @Override
    public Data updateContentByIdRealm(Data data) {
        return mApiHelper.updateContentByIdRealm(data);
    }

    @Override
    public Data updateContentSyncDataRealm(Data data) {
        return mApiHelper.updateContentSyncDataRealm(data);
    }

    @Override
    public RealmResults<Data> getContentsyncListRealm() {
        return mApiHelper.getContentsyncListRealm();
    }

    @Override
    public int getMaxContentId() {
        return mApiHelper.getMaxContentId();
    }

    @Override
    public Observable<PortfolioResponse> getPortfolioAPI() {
        return mApiHelper.getPortfolioAPI();
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPreferencesHelper.getCurrentUserLoggedInMode();
    }

    @Override
    public void setCurrentUserLoggedInMode(LoggedInMode mode) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode);
    }

    @Override
    public String getCurrentUserId() {
        return mPreferencesHelper.getCurrentUserId();
    }

    @Override
    public void setCurrentUserId(String userId) {
        mPreferencesHelper.setCurrentUserId(userId);
    }

    @Override
    public String getCurrentUserName() {
        return mPreferencesHelper.getCurrentUserName();
    }

    @Override
    public void setCurrentUserName(String userName) {
        mPreferencesHelper.setCurrentUserName(userName);
    }

    @Override
    public String getCurrentUserEmail() {
        return mPreferencesHelper.getCurrentUserEmail();
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPreferencesHelper.setCurrentUserEmail(email);
    }

    @Override
    public String getCurrentUserProfilePicUrl() {
        return mPreferencesHelper.getCurrentUserProfilePicUrl();
    }

    @Override
    public void setCurrentUserProfilePicUrl(String profilePicUrl) {
        mPreferencesHelper.setCurrentUserProfilePicUrl(profilePicUrl);
    }


/*
    @Override
    public String getGoogleUserDisplayName() {
        return mFireBaseHelper.getGoogleUserDisplayName();
    }

    @Override
    public String getGoogleUserEmail() {
        return mFireBaseHelper.getGoogleUserEmail();
    }

    @Override
    public String getGoogleUserPhoneNumber() {
        return mFireBaseHelper.getGoogleUserPhoneNumber();
    }

    @Override
    public Uri getGoogleUserPhotoUrl() {
        return mFireBaseHelper.getGoogleUserPhotoUrl();
    }

    @Override
    public String getGoogleUserUid() {
        return mFireBaseHelper.getGoogleUserUid();
    }
*/

    @Override
    public void updateUserInfo(
            String userId,
            LoggedInMode loggedInMode,
            String userName,
            String email,
            String profilePicPath) {

        setCurrentUserId(userId);
        setCurrentUserLoggedInMode(loggedInMode);
        setCurrentUserName(userName);
        setCurrentUserEmail(email);
        setCurrentUserProfilePicUrl(profilePicPath);

    }
}
