package com.dewnaveen.texteditor.ui.Portfolio;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import com.androidnetworking.error.ANError;
import com.dewnaveen.texteditor.R;
import com.dewnaveen.texteditor.data.network.model.PortfolioResponse;
import com.dewnaveen.texteditor.ui.base.BaseActivity;
import com.dewnaveen.texteditor.utils.AppLogger;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


public class PortfolioActivity extends BaseActivity {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private PortfolioAdapter mAdapter;

    private ArrayList<PortfolioResponse.Apps> modelList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        setUp();


    }

    @Override
    protected void setUp() {


        setToolbar();

        setAdapter();

        getDataApi();
    }

    private void setToolbar() {

        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());

    }

    private void getDataApi() {

        showLoading();
        getCompositeDisposable().add(getDataManager()
                .getPortfolioAPI()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(appsResponse -> {
                    if (appsResponse != null && appsResponse.getData() != null) {

                        modelList = (ArrayList<PortfolioResponse.Apps>) appsResponse.getData();
                        mAdapter.addItems(modelList);
                    }
                    hideLoading();
                }, throwable -> {
                    hideLoading();

                    // handle the error here
                    if (throwable instanceof ANError) {
                        ANError anError = (ANError) throwable;
                        handleApiError(anError);
                    }
                }));

    }


    private void setAdapter() {

        mAdapter = new PortfolioAdapter(PortfolioActivity.this, modelList);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(mAdapter);


        mAdapter.SetOnItemClickListener((view, position, model) -> {

            if (!model.getAppUrl().equals("")) {
                try {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(model.getAppUrl()));
                    startActivity(intent);
                } catch (Exception e) {
                    AppLogger.d("url error");
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }

}
