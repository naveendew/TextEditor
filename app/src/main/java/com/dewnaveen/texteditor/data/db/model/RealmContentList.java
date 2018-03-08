package com.dewnaveen.texteditor.data.db.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by naveendewangan on 06/03/18.
 */

public class RealmContentList extends RealmObject{


    @SerializedName("error")
    public boolean error;
    @SerializedName("data")
    public RealmList<Data> data;
    @SerializedName("count")
    public int count;
    @SerializedName("message")
    public String message;


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