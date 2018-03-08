package com.dewnaveen.texteditor.data.db.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by naveendewangan on 06/03/18.
 */

public class Data extends RealmObject {
    @PrimaryKey
    @SerializedName("id")
    public int id;
    @SerializedName("content_id")
    public int content_id;
    @SerializedName("content")
    public String content;
    @SerializedName("header")
    public String header;
    @SerializedName("file")
    public String file;
    @SerializedName("created_at")
    public String created_at;
    @SerializedName("updated_at")
    public String updated_at;

    private Boolean sync_flag;

    public Boolean getSync_flag() {
        return sync_flag;
    }

    public void setSync_flag(Boolean sync_flag) {
        this.sync_flag = sync_flag;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContent_id() {
        return content_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}


