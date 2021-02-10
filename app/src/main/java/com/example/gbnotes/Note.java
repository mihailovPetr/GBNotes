package com.example.gbnotes;

import java.util.Date;

class Note {

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
}
