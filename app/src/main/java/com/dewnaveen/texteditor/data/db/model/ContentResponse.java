package com.dewnaveen.texteditor.data.db.model;

/**
 * Created by naveendewangan on 05/03/18.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContentResponse implements Parcelable
{

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("message")
    @Expose
    private String message;
    public final static Creator<ContentResponse> CREATOR = new Creator<ContentResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ContentResponse createFromParcel(Parcel in) {
            return new ContentResponse(in);
        }

        public ContentResponse[] newArray(int size) {
            return (new ContentResponse[size]);
        }

    }
            ;

    protected ContentResponse(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ContentResponse() {
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(data);
        dest.writeValue(message);
    }

    public int describeContents() {
        return 0;
    }


    public class Data implements Parcelable
    {

        @SerializedName("id")
        @Expose
        private Long id;
        @SerializedName("content_id")
        @Expose
        private Long contentId;
        @SerializedName("content")
        @Expose
        private String content;
        @SerializedName("file")
        @Expose
        private String file;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public String header;
        @SerializedName("header")

        public final Creator<Data> CREATOR = new Creator<Data>() {


            @SuppressWarnings({
                    "unchecked"
            })
            public Data createFromParcel(Parcel in) {
                return new Data(in);
            }

            public Data[] newArray(int size) {
                return (new Data[size]);
            }

        }
                ;

        Data(Parcel in) {
            this.id = ((Long) in.readValue((Long.class.getClassLoader())));
            this.contentId = ((Long) in.readValue((Long.class.getClassLoader())));
            this.content = ((String) in.readValue((String.class.getClassLoader())));
            this.header = ((String) in.readValue((String.class.getClassLoader())));
            this.file = ((String) in.readValue((String.class.getClassLoader())));
            this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
            this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        }

        public Data() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getContentId() {
            return contentId;
        }

        public void setContentId(Long contentId) {
            this.contentId = contentId;
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

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }


        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(id);
            dest.writeValue(contentId);
            dest.writeValue(content);
            dest.writeValue(header);
            dest.writeValue(file);
            dest.writeValue(createdAt);
            dest.writeValue(updatedAt);
        }

        public int describeContents() {
            return 0;
        }

    }
}
