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

package com.dewnaveen.texteditor.data.network;

import android.os.Environment;
import android.util.Log;

import com.dewnaveen.texteditor.data.db.model.ContentListResponse;
import com.dewnaveen.texteditor.data.db.model.Data;
import com.dewnaveen.texteditor.data.db.model.GetContentByIdServer;
import com.dewnaveen.texteditor.data.db.model.PostContentRequest;
import com.dewnaveen.texteditor.data.db.model.PostContentResponse;
import com.dewnaveen.texteditor.data.network.model.PortfolioResponse;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.OkHttpClient;


@Singleton
public class AppApiHelper implements ApiHelper {

    private final ApiHeader mApiHeader;

    @Inject
    Realm realm;

    @Inject
    public AppApiHelper(ApiHeader apiHeader) {
        mApiHeader = apiHeader;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHeader;
    }

    @Override
    public Observable<PostContentResponse> postContenttoServer(PostContentRequest request) {


        Log.d("file.count ", String.valueOf(request.getFile_count()));

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);

        String root = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";

        Map<String, File> multiPartFileMap = new HashMap<>();
        Map<String, String> multiPartFileName = new HashMap<>();

        int i = 1;
        for (String img : request.getImgList()) {
            File file = new File(root + "TextEditor" + File.separator + img);
            if (file.exists()) {
                Log.d("file.exists ", String.valueOf(i));
                multiPartFileMap.put("file" + i, file);
                multiPartFileName.put("fileName" + i, img);
            }

            i++;
        }


        String content_utf = request.getContent();
//        Log.d("content_utf", content_utf);

        return Rx2AndroidNetworking.upload(ApiEndPoint.ENDPOINT_UPLOAD_CONTENT)
                //.setContentType("application/json; charset=utf-8")
//                .addHeaders(mApiHeader.getPublicApiHeader())
                .setOkHttpClient(builder.build())
                .addMultipartFile(multiPartFileMap)
                .addMultipartParameter("file_count", String.valueOf(request.getFile_count()))
                .addMultipartParameter("content", content_utf)
                .addMultipartParameter("header", request.getHeader())
                .addMultipartParameter("content_id", String.valueOf(request.getContent_id()))
                .addMultipartParameter("file", request.getFile())
                .addMultipartParameter(multiPartFileName)
                .build()
                .getObjectObservable(PostContentResponse.class);

    }

    @Override
    public Observable<ContentListResponse> gettContentListfromServer() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_CONTENT_LIST)
//                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectObservable(ContentListResponse.class);

    }

    @Override
    public void upDateContentListRealm(ContentListResponse contentListResponse) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(contentListResponse);
        realm.commitTransaction();

    }

    @Override
    public ContentListResponse getContentListRealm() {
        realm.beginTransaction();
        ContentListResponse contentListResponse = realm.where(ContentListResponse.class).findFirst();
        realm.commitTransaction();
        return contentListResponse;
    }

    @Override
    public Observable<GetContentByIdServer> getContentByIdfromServer(int content_id) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_CONTENT_BY_ID)
//                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addPathParameter("id", String.valueOf(content_id))
                .build()
                .getObjectObservable(GetContentByIdServer.class);
    }

    @Override
    public Data getContentByIdRealm(int content_id) {
        realm.beginTransaction();
        Data data = realm.where(Data.class).equalTo("id", content_id).findFirst();
        realm.commitTransaction();

        return data;
    }

    @Override
    public Data updateContentByIdRealm(Data data) {
        realm.beginTransaction();
//        RealmResults<Data> data1 = realm.where(Data.class).equalTo("id",data.getId()).findAll();
        realm.copyToRealmOrUpdate(data);
        realm.commitTransaction();

/*
        for (Data data2: data1){
            Log.d("data1.getIds()",String.valueOf(data2.getId()));
            Log.d("data1.getHeaders()",data2.getHeader());

        }

        Log.d("data1.getHeader()",data.getHeader());
*/
        return data;
    }

    @Override
    public Data updateContentSyncDataRealm(Data data) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(data);
        realm.commitTransaction();
        return data;
    }

    @Override
    public RealmResults<Data> getContentsyncListRealm() {
        realm.beginTransaction();
        RealmResults<Data> syncList = realm.where(Data.class).equalTo("sync_flag", true).findAll();
        realm.commitTransaction();

        return syncList;

    }

    @Override
    public int getMaxContentId() {
        realm.beginTransaction();
        Number currentIdNum = realm.where(Data.class).max("id");
        int nextId;
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }
        realm.commitTransaction();
        return nextId;
    }

    @Override
    public Observable<PortfolioResponse> getPortfolioAPI() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_PORTFOLIO)
                .build()
                .getObjectObservable(PortfolioResponse.class);

    }


}

