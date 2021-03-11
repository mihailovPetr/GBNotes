package com.example.gbnotes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Note implements Parcelable {
    private String id;
    private String title;
    private String description;
    private Date date;

    public Note(String title, String description, Date date) {
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
            this.date = new Date();
        } else {
            this.date = date;
        }
    }

    public Note(String title, String description) {
        this(title, description, new Date());
    }

    public Note(String title) {
        this(title, "", new Date());
    }

    protected Note(Parcel in) {
        title = in.readString();
        description = in.readString();
        date = (Date) in.readSerializable();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getDate() {
        return date;
    }

    public void editDate(Date date) {
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
        dest.writeSerializable(date);
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
