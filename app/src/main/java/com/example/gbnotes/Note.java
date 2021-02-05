package com.example.gbnotes;

import java.util.Date;

class Note {

    private String headLine;
    private String text;
    private String date;

    public Note(String headLine, String text, String date) {
        if (headLine == null) {
            this.headLine = "";
        } else {
            this.headLine = headLine;
        }

        if (text == null) {
            this.text = "";
        } else {
            this.text = text;
        }

        if (date == null) {
            this.date = new Date().toString();
        } else {
            this.date = date;
        }
    }

    public Note(String headLine, String text) {
        this(headLine, text, new Date().toString());
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

    public String getText() {
        return text;
    }

    public void editText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void editDate(String date) {
        this.date = date;
    }
}
