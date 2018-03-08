package com.dewnaveen.texteditor.data.db.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by naveendewangan on 05/03/18.
 */

public class ContentListResponse extends RealmObject{

    @PrimaryKey
    @SerializedName("id")
    private int id;
    @SerializedName("error")
    private boolean error;
    @SerializedName("data")
    private RealmList<Data> data;
    @SerializedName("count")
    private int count;
    @SerializedName("message")
    private String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public RealmList<Data> getData() {
        return data;
    }

    public void setData(RealmList<Data> data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
