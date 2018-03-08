package com.dewnaveen.texteditor.data.db.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by naveendewangan on 07/03/18.
 */

public class PostContentRequest {
    @Expose
    @SerializedName("content")
    private
    String content;

    @Expose
    @SerializedName("imgList")
    private
    ArrayList<String> imgList;

    @Expose
    @SerializedName("file_count")
    private
    int file_count;

    @Expose
    @SerializedName("content_id")
    private
    int content_id;

    @Expose
    @SerializedName("header")
    private
    String header;

    @Expose
    @SerializedName("file")
    private
    String file;

    public PostContentRequest(String content, ArrayList<String> imgList, int file_count, int content_id, String header, String file) {
        this.content = content;
        this.imgList = imgList;
        this.file_count = file_count;
        this.content_id = content_id;
        this.header = header;
        this.file = file;
    }

    public PostContentRequest() {

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<String> getImgList() {
        return imgList;
    }

    public void setImgList(ArrayList<String> imgList) {
        this.imgList = imgList;
    }

    public int getFile_count() {
        return file_count;
    }

    public void setFile_count(int file_count) {
        this.file_count = file_count;
    }

    public int getContent_id() {
        return content_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
