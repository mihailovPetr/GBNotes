package com.example.gbnotes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

class Note implements Parcelable {

    private String headLine;
    private String description;
    private String date;

    public Note(String headLine, String description, String date) {
        if (headLine == null) {
            this.headLine = "";
        } else {
            this.headLine = headLine;
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

    public Note(String headLine, String description) {
        this(headLine, description, new Date().toString());
    }

    public Note(String headLine) {
        this(headLine, "", new Date().toString());
    }

    protected Note(Parcel in) {
        headLine = in.readString();
        description = in.readString();
        date = in.readString();
    }


    public String getHeadLine() {
        return headLine;
    }

    public void editHeadLine(String headLine) {
        this.headLine = headLine;
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
        dest.writeString(headLine);
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
