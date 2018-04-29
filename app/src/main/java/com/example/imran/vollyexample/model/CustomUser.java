package com.example.imran.vollyexample.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Imran on 4/29/2018.
 */

public class CustomUser implements Parcelable {

    private String id;
    private String first_name;
    private String last_name;
    private String avatar;


    public CustomUser() {

    }

    public static final Creator<CustomUser> CREATOR = new Creator<CustomUser>() {
        @Override
        public CustomUser createFromParcel(Parcel in) {
            return new CustomUser(in);
        }

        @Override
        public CustomUser[] newArray(int size) {
            return new CustomUser[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(first_name);
        dest.writeString(last_name);
        dest.writeString(avatar);

    }

    protected CustomUser(Parcel in) {
        id = in.readString();
        first_name = in.readString();
        last_name = in.readString();
        avatar = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
