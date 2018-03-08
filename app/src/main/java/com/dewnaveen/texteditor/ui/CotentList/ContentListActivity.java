package com.dewnaveen.texteditor.ui.CotentList;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.support.v4.widget.SwipeRefreshLayout;

import com.androidnetworking.error.ANError;
import com.dewnaveen.texteditor.R;
import com.dewnaveen.texteditor.data.db.model.ContentListResponse;
import com.dewnaveen.texteditor.data.db.model.Data;
import com.dewnaveen.texteditor.data.network.ApiEndPoint;
import com.dewnaveen.texteditor.ui.Portfolio.PortfolioActivity;
import com.dewnaveen.texteditor.ui.about.AboutActivity;
import com.dewnaveen.texteditor.ui.base.BaseActivity;
import com.dewnaveen.texteditor.ui.custom.RoundedImageView;
import com.dewnaveen.texteditor.ui.main.MainActivity;
import com.dewnaveen.texteditor.utils.AppLogger;
import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;

import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.support.v7.widget.SearchView;
import android.support.v4.view.MenuItemCompat;
import android.app.SearchManager;
import android.widget.EditText;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.Spanned;

import android.support.design.widget.FloatingActionButton;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.realm.Realm;


public class ContentListActivity extends BaseActivity {

    private final String TAG = "ContentListActivity";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.drawer_view)
    DrawerLayout mDrawer;

    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh_recycler_list)
    SwipeRefreshLayout swipeRefreshRecyclerList;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private ActionBarDrawerToggle mDrawerToggle;


    private RecyclerViewAdapter mAdapter;
    private RecyclerViewScrollListener scrollListener;

    private List<Data> modelList = new ArrayList<>();

    @Inject
    public AppCompatActivity activity;
    public List<Data> mContentListResponse = new ArrayList<Data>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_list);

        getActivityComponent().inject(this);

        ButterKnife.bind(activity);

        setUp();

    }

    @Override
    protected void setUp() {

        setSupportActionBar(mToolbar);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawer,
                mToolbar,
                R.string.open_drawer,
                R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                hideKeyboard();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        setupNavMenu();
        onNavMenuCreated();
        onViewInitialized();


        setAdapter();
        getContentListData();

        swipeRefreshRecyclerList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // Do your stuff on refresh
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (swipeRefreshRecyclerList.isRefreshing())
                            swipeRefreshRecyclerList.setRefreshing(false);
                        {
                            mAdapter.resetAdapter();
                            getContentListData();

                        }
                    }
                }, 2500);

            }
        });

    }


    public void lockDrawer() {
        if (mDrawer != null)
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void unlockDrawer() {
        if (mDrawer != null)
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }


    void setupNavMenu() {
        View headerLayout = mNavigationView.getHeaderView(0);

        mNavigationView.setNavigationItemSelectedListener(
                item -> {
                    mDrawer.closeDrawer(GravityCompat.START);
                    switch (item.getItemId()) {
                        case R.id.nav_item_about:
                            onDrawerOptionAboutClick();
                            openAboutActivity();
                            return true;
                        case R.id.nav_item_rate_us:
                            openResumeLink();
                            return true;
                        case R.id.nav_item_feed:
                            openPortfolioActivity();
                            return true;
                        case R.id.nav_contact:
                            openDialpad();
                            return true;
                        default:
                            return false;
                    }
                });
    }

    private void openDialpad() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + "+917000166619"));
        startActivity(intent);
    }

    private void openResumeLink() {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse(ApiEndPoint.ENDPOINT_RESUME));
            startActivity(intent);
        } catch (Exception e) {
            AppLogger.d("url error");
        }

    }

    private void openPortfolioActivity() {
        startActivity(new Intent(activity, PortfolioActivity.class));

    }

    private void openAboutActivity() {
        startActivity(new Intent(activity, AboutActivity.class));
    }


    public void closeNavigationDrawer() {
        if (mDrawer != null) {
            mDrawer.closeDrawer(Gravity.START);
        }
    }

    public void onDrawerOptionAboutClick() {
        closeNavigationDrawer();
    }

    public void onDrawerOptionLogoutClick() {
        showLoading();

    }

    public void onViewInitialized() {
    }


    public void onNavMenuCreated() {
    }

    public void onDrawerRateUsClick() {
        closeNavigationDrawer();
    }

    public void onDrawerMyFeedClick() {
        closeNavigationDrawer();
    }

    @OnClick(R.id.fab)
    public void onClick(View v) {
        startMainEditorActivity();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_search, menu);


        final SearchView searchView = (SearchView) MenuItemCompat
                .getActionView(menu.findItem(R.id.action_search));

        SearchManager searchManager = (SearchManager) this.getSystemService(this.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));

        EditText searchEdit = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
        searchEdit.setTextColor(Color.WHITE);
        searchEdit.setHintTextColor(Color.WHITE);
        searchEdit.setBackgroundColor(Color.TRANSPARENT);
        searchEdit.setHint("Search");

        InputFilter[] fArray = new InputFilter[2];
        fArray[0] = new InputFilter.LengthFilter(40);
        fArray[1] = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                for (int i = start; i < end; i++) {

                    if (!Character.isLetterOrDigit(source.charAt(i)))
                        return "";
                }


                return null;


            }
        };
        searchEdit.setFilters(fArray);
        View v = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        v.setBackgroundColor(Color.TRANSPARENT);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Data> filterList = new ArrayList<>();
                if (s.length() > 0) {
                    for (int i = 0; i < mContentListResponse.size(); i++) {
                        if (mContentListResponse.get(i).getHeader().toLowerCase().contains(s.toString().toLowerCase())) {
                            filterList.add(mContentListResponse.get(i));
                            mAdapter.updateList(filterList);
                        }
                    }

                } else {
                    mAdapter.updateList(mContentListResponse);
                }
                return false;
            }
        });


        return true;
    }


    private void getContentListData() {

        showLoading();
        getCompositeDisposable().add(getDataManager()
                .gettContentListfromServer()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(contentListResponse -> {
                    if (contentListResponse != null && !contentListResponse.isError()) {


                        mContentListResponse.clear();
                        mContentListResponse = contentListResponse.getData();
                        mAdapter.addItems(mContentListResponse);
//                            Log.d("contentListResponse", String.valueOf(contentListResponse.getCount()));

                        TastyToast.makeText(activity,getString(R.string.success), TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
                        updateRealm(contentListResponse);

                    }
                    hideLoading();
                }, throwable -> {


                    getRealmData();

                    hideLoading();

                    if (throwable instanceof ANError) {
                        ANError anError = (ANError) throwable;
                        handleApiError(anError);
                    }
                }));

    }

    private void getRealmData() {

        activity.runOnUiThread(() -> {
            ContentListResponse contentListResponse = getDataManager().getContentListRealm();
            mContentListResponse = contentListResponse.getData();
            mAdapter.addItems(mContentListResponse);

            Log.d("Adapter Size- ", String.valueOf(mAdapter.getItemCount()));
        });
        TastyToast.makeText(activity,getString(R.string.no_internet_available), TastyToast.LENGTH_LONG, TastyToast.WARNING).show();


    }

    private void setAdapter() {

        mAdapter = new RecyclerViewAdapter(activity, mContentListResponse);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);


        scrollListener = new RecyclerViewScrollListener() {

            public void onEndOfScrollReached(RecyclerView rv) {

//                Toast.makeText(activity, "End of the RecyclerView reached. Do your pagination stuff here", Toast.LENGTH_SHORT).show();

                scrollListener.disableScrollListener();
            }
        };

        recyclerView.addOnScrollListener(scrollListener);

        mAdapter.SetOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Data model) {

                Bundle bundle = new Bundle();
                bundle.putInt("content_id", model.getId());

                Intent intent = new Intent(activity, MainActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });


    }

    public void updateRealm(ContentListResponse contentListResponse) {
        getDataManager().upDateContentListRealm(contentListResponse);
    }

    @Override
    public void onFragmentAttached() {
    }

    @Override
    public void onFragmentDetached(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                    .remove(fragment)
                    .commitNow();
            unlockDrawer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDrawer != null)
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    private void startMainEditorActivity() {
        startActivity(new Intent(activity, MainActivity.class));

    }


}
