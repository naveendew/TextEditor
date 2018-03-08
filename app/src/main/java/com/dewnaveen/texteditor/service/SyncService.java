package com.dewnaveen.texteditor.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.util.Log;


import com.androidnetworking.error.ANError;
import com.dewnaveen.texteditor.app.MyApplication;
import com.dewnaveen.texteditor.data.DataManager;
import com.dewnaveen.texteditor.data.db.model.Data;
import com.dewnaveen.texteditor.data.db.model.PostContentRequest;
import com.dewnaveen.texteditor.di.component.DaggerServiceComponent;
import com.dewnaveen.texteditor.di.component.ServiceComponent;
import com.dewnaveen.texteditor.rxjava.AppSchedulerProvider;
import com.dewnaveen.texteditor.rxjava.SchedulerProvider;
import com.dewnaveen.texteditor.utils.AppLogger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.realm.RealmResults;

public class SyncService extends Service {

    private static final String TAG = "SyncService";

    @Inject
    DataManager mDataManager;


    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private final SchedulerProvider mSchedulerProvider = new AppSchedulerProvider();


    public static Intent getStartIntent(Context context) {
        return new Intent(context, SyncService.class);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, SyncService.class);
        context.startService(starter);
    }

    public static void stop(Context context) {
        context.stopService(new Intent(context, SyncService.class));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ServiceComponent component = DaggerServiceComponent.builder()
                .applicationComponent(((MyApplication) getApplication()).getComponent())
                .build();
        component.inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        RealmResults<Data> syncList = mDataManager.getContentsyncListRealm();

        if (syncList == null)
            stopSelf();

        for (Data data : syncList) {
            String content = data.getContent();
            ArrayList<String> imgList = getSpannedImages(content);
            SpannableStringBuilder builder_ = new SpannableStringBuilder(content);

            String editText_source = Html.toHtml(builder_);
            String editText_source_hdr = data.getHeader();

            PostContentRequest postContentRequest = new PostContentRequest();
            postContentRequest.setContent(editText_source);
            postContentRequest.setHeader(editText_source_hdr);
            postContentRequest.setContent_id(5);
            postContentRequest.setFile();
            postContentRequest.setFile_count(imgList.size());
            postContentRequest.setImgList(imgList);


            mCompositeDisposable.add(mDataManager
                    .postContenttoServer(postContentRequest)
                    .subscribeOn(mSchedulerProvider.io())
                    .observeOn(mSchedulerProvider.ui())
                    .subscribe(response -> {

                        Log.d("PostContentResponse- ", response.getMessage());

                        if (response.isError()) {
                        } else {

                            upDateRealmSyncData(data);

                        }

                    }, throwable -> {

                        // handle the login error here
                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            AppLogger.d(anError.toString());
                        }
                    }));


        }

        AppLogger.d(TAG, "SyncService started");

        return START_STICKY;
    }

    private void upDateRealmSyncData(Data data) {

        data.setSync_flag(false);

        Data data1 = mDataManager.updateContentByIdRealm(data);

    }

    @Override
    public void onDestroy() {
        AppLogger.d(TAG, "SyncService stopped");
        mCompositeDisposable.dispose();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private ArrayList<String> getSpannedImages(String source) {
        ArrayList<String> imgList = new ArrayList<>();


        SpannableStringBuilder builder = new SpannableStringBuilder(source);
        String edit_source = Html.toHtml(builder);

        Document doc = Jsoup.parse(edit_source);
        Elements imgs = doc.select("img");


//        Log.d("imgs", String.valueOf(imgs.size()));

        for (Element el : imgs) {

            Element img = el.select("img").first();

            String src = el.attr("src");
            imgList.add(src);
        }

        return imgList;
    }

}
