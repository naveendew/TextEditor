package com.dewnaveen.texteditor.ui.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.CharacterStyle;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by naveendewangan on 22/01/18.
 */

public class TextEditor extends android.support.v7.widget.AppCompatEditText {

    public static final int TYPEFACE_NORMAL = 0;
    public static final int TYPEFACE_BOLD = 1;
    public static final int TYPEFACE_ITALICS = 2;
    public static final int TYPEFACE_BOLD_ITALICS = 3;
    public static final int TYPEFACE_UNDERLINE = 4;
    public static final int TYPEFACE_IMAGE = 5;
    public static boolean underline = false;

    private int currentTypeface;
    private int lastCursorPosition, endCursorPosition = 0;
    private int selectionStartCursorPosition, selectionEndCursorPosition = 0;
    private int tId;

    Drawable drawable;
    private String imageFileName;


    public TextEditor(Context context) {
        super(context);
        lastCursorPosition = this.getSelectionStart();
    }

    public TextEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public int gettId() {
        return tId;
    }

    public void settId(int tId) {
        this.tId = tId;
    }

    public void changeTypeface(int tfId) {
        currentTypeface = tfId;
        lastCursorPosition = this.getSelectionStart();
//        endCursorPosition = this.getSelectionEnd();

        if (selectionStartCursorPosition != selectionEndCursorPosition)
            triggerSelecionStyle();

    }

    private void triggerSelecionStyle() {

        Spannable str = this.getText();
        StyleSpan ss;

        switch (currentTypeface) {
            case TYPEFACE_NORMAL:
                ss = new StyleSpan(Typeface.NORMAL);
                Object spansToRemove[] = str.getSpans(selectionStartCursorPosition, selectionEndCursorPosition, Object.class);
                for (Object span : spansToRemove) {
                    if (span instanceof CharacterStyle)
                        str.removeSpan(span);
                }
                break;
            case TYPEFACE_BOLD:
                ss = new StyleSpan(Typeface.BOLD);
                break;
            case TYPEFACE_ITALICS:
                ss = new StyleSpan(Typeface.ITALIC);
                break;
/*
            case TYPEFACE_UNDERLINE:
                underline = true;
                ss = new StyleSpan(Typeface.NORMAL);
//                tt = new UnderlineSpan();
                break;
*/
            case TYPEFACE_BOLD_ITALICS:
                ss = new StyleSpan(Typeface.BOLD_ITALIC);
                break;
/*
            case TYPEFACE_IMAGE:
                addImageBetweentext(drawable, start);
                ss = new StyleSpan(Typeface.NORMAL);
                break;
*/
            default:
                ss = new StyleSpan(Typeface.NORMAL);
                break;
        }

        // Runtime Error Fix!
        if (selectionEndCursorPosition > selectionStartCursorPosition)
            str.setSpan(ss, selectionStartCursorPosition, selectionEndCursorPosition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

//        Log.d("Spannable str - ", Html.toHtml(str));


    }

    public void insertDrawable(Drawable drawable, String imageFileName) {
        this.drawable = drawable;
        this.imageFileName = imageFileName;
        addImageBetweentext(drawable, this.getSelectionStart());
        lastCursorPosition = this.getSelectionStart();

    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {

        if (selStart <= 0 && selEnd <= 0)
            this.clearFocus();

        if (selStart < 0)
            selStart = 0;

        if (selEnd < 0)
            selEnd = 0;
//            selectionStartCursorPosition = 0;

        selectionStartCursorPosition = selStart;
        selectionEndCursorPosition = selEnd;

//        Log.d("selStart - ", String.valueOf(selStart));
//        Log.d("selEnd - ", String.valueOf(selEnd));


    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        Spannable str = this.getText();
        StyleSpan ss;
//        Log.d("Spannable str1 - ", Html.toHtml(str));

        int endLength = text.toString().length();

        underline = false;

//        Log.d("onTextChanged ", text.toString());
//        Log.d("currentTypeface ", String.valueOf(currentTypeface));

        switch (currentTypeface) {
            case TYPEFACE_NORMAL:
                ss = new StyleSpan(Typeface.NORMAL);
                break;
            case TYPEFACE_BOLD:
                ss = new StyleSpan(Typeface.BOLD);
                break;
            case TYPEFACE_ITALICS:
                ss = new StyleSpan(Typeface.ITALIC);
                break;
/*
            case TYPEFACE_UNDERLINE:
                underline = true;
                ss = new StyleSpan(Typeface.NORMAL);
//                tt = new UnderlineSpan();
                break;
*/
            case TYPEFACE_BOLD_ITALICS:
                ss = new StyleSpan(Typeface.BOLD_ITALIC);
                break;
/*
            case TYPEFACE_IMAGE:
                addImageBetweentext(drawable, start);
                ss = new StyleSpan(Typeface.NORMAL);
                break;
*/
            default:
                ss = new StyleSpan(Typeface.NORMAL);
        }

//        Log.d("endLength - ", String.valueOf(endLength));
//        Log.d("lastCursorPosition - ", String.valueOf(lastCursorPosition));
        // Runtime Error Fix!
        if (currentTypeface != TYPEFACE_NORMAL) {

            if (start > lastCursorPosition)
                str.setSpan(ss, lastCursorPosition, start, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
//        Log.d("Spannable str - ", Html.toHtml(str));


    }

    private void addImageBetweentext(Drawable drawable, int selectionCursor) {


        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

//        Log.d("getIntrinsicHeight", String.valueOf(drawable.getIntrinsicHeight()));
//        Log.d("getIntrinsicWidth", String.valueOf(drawable.getIntrinsicWidth()));

        selectionCursor = this.getSelectionStart();
        this.getText().insert(selectionCursor, ".");
        selectionCursor = this.getSelectionStart();


        SpannableStringBuilder builder = new SpannableStringBuilder(this.getText());
        builder.setSpan(new ImageSpan(drawable), selectionCursor - ".".length(), selectionCursor, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        this.setText(builder);
        this.setSelection(selectionCursor);
//        Log.d("before", Html.toHtml(builder));

        String sou_ = Html.toHtml(builder);
        String substr = sou_.substring(0, selectionCursor);
        String substr2 = sou_.substring(selectionCursor, sou_.length());

        substr2 = substr2.replace("src=\"null\"", "src=\"" + imageFileName + "\"");

        String ss = substr + substr2;
        this.setText(Html.fromHtml(ss, new Html.ImageGetter() {

            @Override
            public Drawable getDrawable(String source) {
                Log.d("image_source - ", source);
                String base = "storage/emulated/0/TextEditor/";
                Drawable drawable;
                Bitmap bitmap = BitmapFactory.decodeFile(base + source);
                drawable = new BitmapDrawable(getResources(), bitmap);

                drawable.setBounds(
                        0,
                        0,
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight());

                return drawable;
            }

        }, null));

//        this.setMovementMethod(LinkMovementMethod.getInstance());
        this.setSelection(selectionCursor);
    }

}