package com.dewnaveen.texteditor.ui.preview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.Spannable;
import android.util.Log;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.dewnaveen.texteditor.R;
import com.dewnaveen.texteditor.data.db.model.ContentResponse;
import com.dewnaveen.texteditor.data.network.ApiEndPoint;
import com.dewnaveen.texteditor.ui.CotentList.ContentListActivity;
import com.dewnaveen.texteditor.ui.base.BaseActivity;
import com.dewnaveen.texteditor.ui.custom.PicassoImageGetter;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PreviewActivity extends BaseActivity {

    @BindView(R.id.htmlTextView)
    TextView htmlTextView;

    @BindView(R.id.htmlHdrTextView)
    TextView htmlHdrTextView;

    private final Activity mAct = this;
    private OkHttpClient client;
    private final String TAG = "PreviewActivity";
    private int content_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preiew);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            content_id = bundle.getInt("content_id", 0);
        }

        setUp();

    }

    @Override
    protected void setUp() {

        showLoading();
        getCompositeDisposable().add(getDataManager()
                .getContentByIdfromServer(content_id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(getContentByIdServer -> {

                    if (getContentByIdServer != null && !getContentByIdServer.getError()) {

//                            Log.d("ContentResponse", getContentByIdServer.getMessage());

                        htmlHdrTextView.setText(getContentByIdServer.getData().getHeader());

                        String content = getContentByIdServer.getData().getContent();
//                            htmlTextView.setText(Html.fromHtml(response.getData().getContent()));

                        PicassoImageGetter imageGetter = new PicassoImageGetter(htmlTextView, mAct);
                        Spannable html;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            html = (Spannable) Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY, imageGetter, null);
                        } else {
                            html = (Spannable) Html.fromHtml(content, imageGetter, null);
                        }
                        htmlTextView.setText(html);


                    }
                    hideLoading();
                }, throwable -> {

                    hideLoading();

                    if (throwable instanceof ANError) {
                        ANError anError = (ANError) throwable;
                        handleApiError(anError);
                        Log.e("ANError--", throwable.toString());
                    }
                }));


    }

    public void okHttpCall() {


        client = new OkHttpClient();

        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        final Request request = new Request.Builder()
//                .url("http://192.168.56.1/naveen_resume/public/api/getContentById/1")
                .url(ApiEndPoint.ENDPOINT_CONTENT_BY_ID)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    final String responseData = response.body().string();
                    Log.d(TAG, responseData);

                    // Run view-related code back on the main thread
                    PreviewActivity.this.runOnUiThread(() -> {
                        Gson gson = new Gson();
                        ContentResponse response1 = gson.fromJson(responseData, ContentResponse.class);
                        Log.d("ContentResponse", response1.getMessage());

                        String content = response1.getData().getContent();
//                            htmlTextView.setText(Html.fromHtml(response.getData().getContent()));

                        PicassoImageGetter imageGetter = new PicassoImageGetter(htmlTextView, mAct);
                        Spannable html;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            html = (Spannable) Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY, imageGetter, null);
                        } else {
                            html = (Spannable) Html.fromHtml(content, imageGetter, null);
                        }
                        htmlTextView.setText(html);

                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mainActivity = new Intent(this, ContentListActivity.class);
        mainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainActivity);
        finish();

    }


}
