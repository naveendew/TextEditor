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


import com.dewnaveen.texteditor.data.db.model.ContentListResponse;
import com.dewnaveen.texteditor.data.db.model.Data;
import com.dewnaveen.texteditor.data.db.model.GetContentByIdServer;
import com.dewnaveen.texteditor.data.db.model.PostContentRequest;
import com.dewnaveen.texteditor.data.db.model.PostContentResponse;
import com.dewnaveen.texteditor.data.network.model.PortfolioResponse;

import io.reactivex.Observable;
import io.realm.RealmResults;

public interface ApiHelper {

    ApiHeader getApiHeader();

    Observable<PostContentResponse> postContenttoServer(PostContentRequest request);

    Observable<ContentListResponse> gettContentListfromServer();

    void upDateContentListRealm(ContentListResponse contentListResponse);

    ContentListResponse getContentListRealm();

    Observable<GetContentByIdServer> getContentByIdfromServer(int content_id);

    Data getContentByIdRealm(int content_id);

    Data updateContentByIdRealm(Data data);

    Data updateContentSyncDataRealm(Data data);

    RealmResults<Data> getContentsyncListRealm();

    int getMaxContentId();

    Observable<PortfolioResponse> getPortfolioAPI();


}
