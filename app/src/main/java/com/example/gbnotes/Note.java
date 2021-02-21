package com.example.gbnotes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Note implements Parcelable {

    private String title;
    private String description;
    private String date;

    public Note(String title, String description, String date) {
        if (title == null) {
            this.title = "";
        } else {
            this.title = title;
        }

        if (description == null) {
            this.description = "";
        } else {
            this.description = description;
        }

        if (date == null) {
            this.date = new Date().toString();
        } else {
            this.date = date;
        }
    }

    public Note(String title, String description) {
        this(title, description, new Date().toString());
    }

    public Note(String title) {
        this(title, "", new Date().toString());
    }

    protected Note(Parcel in) {
        title = in.readString();
        description = in.readString();
        date = in.readString();
    }


    public String getTitle() {
        return title;
    }

    public void editTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void editText(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void editDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(date);
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

}
