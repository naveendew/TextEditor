package com.dewnaveen.texteditor.data.db.model;

/**
 * Created by naveendewangan on 05/03/18.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetContentByIdServer implements Parcelable
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
    public final static Creator<GetContentByIdServer> CREATOR = new Creator<GetContentByIdServer>() {


        @SuppressWarnings({
                "unchecked"
        })
        public GetContentByIdServer createFromParcel(Parcel in) {
            return new GetContentByIdServer(in);
        }

        public GetContentByIdServer[] newArray(int size) {
            return (new GetContentByIdServer[size]);
        }

    }
            ;

    protected GetContentByIdServer(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
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

}
