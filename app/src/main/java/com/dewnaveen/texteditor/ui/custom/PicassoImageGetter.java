package com.dewnaveen.texteditor.ui.custom;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.dewnaveen.texteditor.R;
import com.dewnaveen.texteditor.data.network.ApiEndPoint;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by naveendewangan on 05/03/18.
 */

public class PicassoImageGetter implements Html.ImageGetter {

    private TextView textView = null;

    private Activity activity;

    public PicassoImageGetter() {

    }

    public PicassoImageGetter(TextView target, Activity activity) {
        textView = target;
        this.activity = activity;
    }

    @Override
    public Drawable getDrawable(String source) {

//        source = "http://192.168.56.1/naveen_resume/public/upload/Images/" + source;
        String endpointContentImagePath = ApiEndPoint.ENDPOINT_CONTENT_IMAGE_PATH;

        Log.d("source - -", endpointContentImagePath + source);
        BitmapDrawablePlaceHolder drawable = new BitmapDrawablePlaceHolder();
        Picasso.with(activity)
                .load(endpointContentImagePath + source)
                .error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.ic_launcher_background)
                .into(drawable);
        return drawable;
    }

    @SuppressWarnings("deprecation")
    private class BitmapDrawablePlaceHolder extends BitmapDrawable implements Target {

        protected Drawable drawable = activity.getResources().getDrawable( R.drawable.ic_loader );;

        @Override
        public void draw(final Canvas canvas) {
            if (drawable != null) {
                drawable.draw(canvas);
            }
        }

        public void setDrawable(Drawable drawable) {
            this.drawable = drawable;
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            drawable.setBounds(0, 0, width, height);
            setBounds(0, 0, width, height);
            if (textView != null) {
                textView.setText(textView.getText());
            }
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            setDrawable(new BitmapDrawable(activity.getResources(), bitmap));
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }

    }
}