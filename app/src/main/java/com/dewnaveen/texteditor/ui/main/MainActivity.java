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

package com.dewnaveen.texteditor.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.androidnetworking.error.ANError;
import com.dewnaveen.texteditor.BuildConfig;
import com.dewnaveen.texteditor.R;
import com.dewnaveen.texteditor.data.DataManager;
import com.dewnaveen.texteditor.data.db.model.Data;
import com.dewnaveen.texteditor.data.db.model.PostContentRequest;
import com.dewnaveen.texteditor.data.db.model.PostContentResponse;
import com.dewnaveen.texteditor.ui.base.BaseActivity;
import com.dewnaveen.texteditor.ui.custom.PicassoImageGetter;
import com.dewnaveen.texteditor.ui.custom.RoundedImageView;
import com.dewnaveen.texteditor.ui.custom.TextEditor;
import com.dewnaveen.texteditor.ui.preview.PreviewActivity;
import com.dewnaveen.texteditor.utils.AppLogger;
import com.dewnaveen.texteditor.utils.CommonUtils;
import com.dewnaveen.texteditor.utils.DialogFactory;
import com.dewnaveen.texteditor.utils.NetworkUtils;
import com.dewnaveen.texteditor.utils.ScreenUtils;
import com.dewnaveen.texteditor.utils.SettingsScreen;
import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.security.Permission;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import io.realm.Realm;


public class MainActivity extends BaseActivity {
    private static final int REQUEST_PERMISSION_STORAGE = 3;

/*
    @Inject
    @Named("white")
    int white;
*/


    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.editText)
    TextEditor editText;
    @BindView(R.id.bold)
    Button bold;
    @BindView(R.id.italic)
    Button italic;
    @BindView(R.id.boldItalic)
    Button boldItalic;
    @BindView(R.id.normal)
    Button normal;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;
    @BindView(R.id.cl_root_view)
    CoordinatorLayout clRootView;
    @BindView(R.id.image)
    Button image;

    @BindDrawable(R.drawable.btn_rect_trans_black)
    Drawable btn_bg;

    @BindDrawable(R.drawable.btn_pressed_rect_trans_black)
    Drawable btn_bg_pressed;

    @BindColor(R.color.white)
    int white;

    @BindColor(R.color.black)
    int black;

    @BindView(R.id.editTextHdr)
    EditText editTextHdr;

    @BindView(R.id.edit_layout)
    LinearLayout editLayout;

    private static final int SELECTED_PIC = 1;

    private int typefaceStyle;
    private String imageFileName = "";

    @Inject
    public AppCompatActivity activity;

    int content_id = 0;

    final Handler handler = new Handler();
    private boolean upload_success = false;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        setUp();

    }

    @Override
    public void onBackPressed() {

        if (editText.getText().toString().equals("") && editTextHdr.getText().toString().equals(""))
            super.onBackPressed();
        else
            showBackPressedDialog();


    }

    private void showBackPressedDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.exit)
                .content(R.string.are_yot_sure)
                .positiveText(R.string.agree)
                .negativeText(R.string.disagree)
                .onPositive((dialog, which) -> {


                    if (NetworkUtils.isNetworkConnected(context)) {
                        uploadToServer(false);
                        MainActivity.super.onBackPressed();
                    } else {
                        updateContentRealm(false);
                        MainActivity.super.onBackPressed();
                    }
                    dialog.dismiss();
                })
                .onNegative((dialog, which) -> dialog.dismiss())
                .show();

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Drawable drawable = item.getIcon();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
        switch (item.getItemId()) {
            case R.id.action_save:
                saveDataEditor();
                return true;
            case R.id.action_preview:
                previewDataEditor();
                return true;
            case R.id.action_refresh:
                setUp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showUploadLocalDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.no_internet)
                .content(R.string.no_internet_content)
                .positiveText(R.string.agree)
                .negativeText(R.string.disagree)
                .onPositive((dialog, which) -> {
                    updateContentRealm(false);
                    dialog.dismiss();
                })
                .onNegative((dialog, which) -> dialog.dismiss())
                .show();
    }

    private void updateContentRealm(Boolean isConnected) {

        SpannableStringBuilder builder_ = new SpannableStringBuilder(editText.getText());

        String editText_source = Html.toHtml(builder_);
        String editText_source_hdr = editTextHdr.getText().toString();

        Data data = new Data();
        data.setId(content_id);
        data.setContent(editText_source);
        data.setHeader(editText_source_hdr);
        data.setContent_id(content_id);
//                            data.setFile("");
//                            data.setCreated_at("");
        data.setUpdated_at(CommonUtils.getTimeStamp());

        if (isConnected)
            data.setSync_flag(false);
        else
            data.setSync_flag(true);

        Data data1 = getDataManager().updateContentByIdRealm(data);
        if (data1 != null)
            TastyToast.makeText(activity, getString(R.string.upload_success_local), TastyToast.LENGTH_LONG, TastyToast.INFO).show();


    }


    private void showUploadErrorDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.oops)
                .content(R.string.error_uploading)
                .positiveText(R.string.agree)
                .negativeText(R.string.disagree)
                .onPositive((dialog, which) -> dialog.dismiss())
                .onNegative((dialog, which) -> dialog.dismiss())
                .show();

    }

    private void saveDataEditor() {
        if (NetworkUtils.isNetworkConnected(activity)) {
            uploadToServer(false);
        } else {
            showUploadLocalDialog();
        }
    }

    private void previewDataEditor() {
        if (NetworkUtils.isNetworkConnected(activity)) {
            openPreviewActivity();
        } else {
            new MaterialDialog.Builder(this)
                    .title(R.string.no_internet)
                    .content(R.string.no_internet_content_preview)
                    .positiveText(R.string.agree)
                    .negativeText(R.string.disagree)
                    .onPositive((dialog, which) -> {

                        SettingsScreen settingsScreen = new SettingsScreen(context);
                        if (CommonUtils.wifiState(context)) {
                            settingsScreen.showWifiScreen();
                        } else {
                            settingsScreen.showDataMobileScreen();
                        }
                        dialog.dismiss();
                    })
                    .onNegative((dialog, which) -> dialog.dismiss())
                    .show();
        }
    }

    private void openPreviewActivity() {

        uploadToServer(true);


    }

    private void uploadToServer(Boolean preview) {

//        upload_success = false;

        showLoading();

        ArrayList<String> imgList = getSpannedImages();

        SpannableStringBuilder builder_ = new SpannableStringBuilder(editText.getText());

        String editText_source = Html.toHtml(builder_);
        String editText_source_hdr = editTextHdr.getText().toString();

        PostContentRequest postContentRequest = new PostContentRequest();
        postContentRequest.setContent(editText_source);
        postContentRequest.setHeader(editText_source_hdr);
        postContentRequest.setContent_id(content_id);
        postContentRequest.setFile("");
        postContentRequest.setFile_count(imgList.size());
        postContentRequest.setImgList(imgList);


        getCompositeDisposable().add(getDataManager()
                .postContenttoServer(postContentRequest)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {

                    Log.d("PostContentResponse- ", response.getMessage());

                    hideLoading();

                    if (response.isError()) {
                        showUploadErrorDialog();
                        updateContentRealm(false);
                    } else {
                        updateContentRealm(true);

                        TastyToast.makeText(activity, getString(R.string.upload_success), TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();

                        if (preview) {
                            content_id = response.getData().getId();
                            Bundle bundle = new Bundle();
                            bundle.putInt("content_id", content_id);

                            Intent intent = new Intent(activity, PreviewActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);

                        }
                    }

                    Log.d("isError", String.valueOf(response.isError()));
                    Log.d("upload_success", String.valueOf(upload_success));

                }, throwable -> {

                    showUploadErrorDialog();
                    // handle the login error here
                    if (throwable instanceof ANError) {
                        ANError anError = (ANError) throwable;
                        handleApiError(anError);
                    }
                    hideLoading();
                    updateContentRealm(false);
                }));

    }

    private ArrayList<String> getSpannedImages() {
        ArrayList<String> imgList = new ArrayList<>();


        SpannableStringBuilder builder = new SpannableStringBuilder(editText.getText());
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

    @Override
    protected void setUp() {
        setSupportActionBar(mToolbar);
        getPermissions();
    }


    @OnClick({R.id.bold, R.id.italic, R.id.boldItalic, R.id.normal})
    public void onViewClicked(View view) {
        resetAllButtons();

        switch (view.getId()) {
            case R.id.bold:
                typefaceStyle = TextEditor.TYPEFACE_BOLD;
                editText.changeTypeface(typefaceStyle);
                ButtonPressed(bold);
                break;
            case R.id.italic:
                typefaceStyle = TextEditor.TYPEFACE_ITALICS;
                editText.changeTypeface(typefaceStyle);
                ButtonPressed(italic);
                break;
            case R.id.boldItalic:
                typefaceStyle = TextEditor.TYPEFACE_BOLD_ITALICS;
                editText.changeTypeface(typefaceStyle);
                ButtonPressed(boldItalic);
                break;
            case R.id.normal:
                typefaceStyle = TextEditor.TYPEFACE_NORMAL;
                editText.changeTypeface(typefaceStyle);
                ButtonPressed(normal);
                break;
        }

    }

    private void ButtonPressed(Button btn) {

        btn.setBackground(btn_bg_pressed);
        btn.setTextColor(white);

    }

    private void resetAllButtons() {
        bold.setBackground(btn_bg);
        bold.setTextColor(black);

        italic.setBackground(btn_bg);
        italic.setTextColor(black);

        boldItalic.setBackground(btn_bg);
        boldItalic.setTextColor(black);

        normal.setBackground(btn_bg);
        normal.setTextColor(black);

        image.setBackground(btn_bg);
        image.setTextColor(black);


    }


    public void initializeEditor() {

        showLoading();

        editText.setOnTouchListener((v, event) -> {
            Log.i("click", "onMtouch");
            editText.requestFocus();
            editLayout.setVisibility(View.VISIBLE);
            return false;
        });

        editTextHdr.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    editLayout.setVisibility(View.GONE);
                } else {
                    editLayout.setVisibility(View.VISIBLE);
                }
            }
        });


        typefaceStyle = TextEditor.TYPEFACE_NORMAL;
        editText.changeTypeface(typefaceStyle);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            content_id = bundle.getInt("content_id", 0);

            getContentFromRealm();

/*
            if(NetworkUtils.isNetworkConnected(activity)){
                getContentFromServer();

            }else{
                getContentFromRealm();

            }
*/


        } else {
            content_id = getDataManager().getMaxContentId();
            hideLoading();
        }

//        hideLoading();

    }

    private void getContentFromRealm() {

        final Boolean[] resource_missing = new Boolean[1];
        resource_missing[0] = false;

        Data data = getDataManager().getContentByIdRealm(content_id);

        assert data != null;
        String content = data.getContent();

        editTextHdr.setText(data.getHeader());

//            content = content +  "<img src=\"Temp_20180306_064450_.jpg\">";

        handler.postDelayed(() -> {
            String base = "storage/emulated/0/TextEditor/";


            editText.setText(Html.fromHtml(content, (String source) -> {
//                Log.d("image_source - ", source);
                Drawable drawable;
                Bitmap bitmap = BitmapFactory.decodeFile(base + source);

                AppLogger.d("BitmapParse-", base + source);

                if (bitmap == null) {
                    resource_missing[0] = true;
                    AppLogger.d("BitmapParseException");

                }
//                drawable = new BitmapDrawable(bitmap);
                drawable = new BitmapDrawable(getResources(), bitmap);

                drawable.setBounds(
                        0,
                        0,
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight());

                return drawable;
            }, null));

            editText.setMovementMethod(LinkMovementMethod.getInstance());
            hideLoading();
        }, 2500);

        if (resource_missing[0])
            TastyToast.makeText(getApplicationContext(), getString(R.string.res_missing), TastyToast.LENGTH_LONG, TastyToast.WARNING);

    }

    private void getContentFromServer() {
        showLoading();
        getCompositeDisposable().add(getDataManager()
                .getContentByIdfromServer(content_id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(getContentByIdServer -> {

                    if (getContentByIdServer != null && !getContentByIdServer.getError()) {

//                            Log.d("ContentResponse", getContentByIdServer.getMessage());

                        editTextHdr.setText(getContentByIdServer.getData().getHeader());

                        String content = getContentByIdServer.getData().getContent();
//                            htmlTextView.setText(Html.fromHtml(response.getData().getContent()));

                        PicassoImageGetter imageGetter = new PicassoImageGetter(editText, activity);
                        Spannable html;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            html = (Spannable) Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY, imageGetter, null);
                        } else {
                            html = (Spannable) Html.fromHtml(content, imageGetter, null);
                        }
                        editText.setText(html);


                    } else {
                        getContentFromRealm();
                    }
                    hideLoading();
                }, throwable -> {

                    getContentFromRealm();
                    hideLoading();

                    if (throwable instanceof ANError) {
                        ANError anError = (ANError) throwable;
                        handleApiError(anError);
                        Log.e("ANError--", throwable.toString());
                    }
                }));

    }

    private void getPermissions() {

        String getAccountsPermission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        if (!hasPermission(getAccountsPermission)) {
            requestPermissionsSafely(new String[]{getAccountsPermission},
                    REQUEST_PERMISSION_STORAGE);
        } else {
            initializeEditor();
        }

    }

    @OnClick(R.id.image)
    public void onViewClicked() {
        resetAllButtons();
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECTED_PIC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SELECTED_PIC:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};


                    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filepath = cursor.getString(columnIndex);
                    cursor.close();

                    Bitmap bitmap = BitmapFactory.decodeFile(filepath);
                    Drawable drawable = new BitmapDrawable(getResources(), bitmap);

                    String root = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";

                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    imageFileName = "Temp_" + timeStamp + "_" + ".jpg";

                    File folder = new File(Environment.getExternalStorageDirectory() + "/TextEditor");
                    File file = new File(root + "TextEditor" + File.separator + imageFileName);

                    UCrop.Options options = new UCrop.Options();
                    options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                    options.setCompressionQuality(50);
                    options.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

                    boolean success = true;
                    if (!folder.exists()) {
                        success = folder.mkdir();
                    }
                    if (success) {
                        if (!file.exists()) {
                            try {
                                file.createNewFile();
                                //f.createNewFile();
                                int maxWidth = ScreenUtils.getScreenWidth(activity);
                                int maxHeight = ScreenUtils.getScreenHeight(activity);
                                Uri sourceUri = uri;
                                Uri destinationUri = Uri.fromFile(file);
                                UCrop.of(sourceUri, destinationUri)
//                                        .withAspectRatio(16, 9)
                                        .withOptions(options)
                                        .withMaxResultSize(maxWidth, maxHeight)
                                        .start(activity);
//                                copyFile(new File(filepath), file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }


//                    addImageBetweentext(drawable);
                }
                break;

            case UCrop.REQUEST_CROP:
                if (resultCode == RESULT_OK) {
                    final Uri resultUri = UCrop.getOutput(data);
                    Drawable drawable;
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(resultUri);
                        drawable = Drawable.createFromStream(inputStream, resultUri.toString());
                    } catch (FileNotFoundException e) {
                        drawable = getResources().getDrawable(R.drawable.ic_launcher);
                    }

                    Log.d("filepath", resultUri.toString());

                    typefaceStyle = TextEditor.TYPEFACE_IMAGE;
                    editText.insertDrawable(drawable, imageFileName);

                } else if (resultCode == UCrop.RESULT_ERROR) {
                    final Throwable cropError = UCrop.getError(data);
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initializeEditor();
                } else {
                    DialogFactory.createSimpleOkErrorDialog(this,
                            R.string.title_permissions,
                            R.string.permission_not_accepted_storage).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
