package com.example.gbnotes;

import java.util.Date;

class Note {

    private String headLine;
    private String text;
    private Date date;

    public Note(String headLine, String text, Date date) {
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
            this.date = new Date();
        } else {
            this.date = date;
        }
    }

    public Note(String headLine, String text) {
        this(headLine, text, new Date());
    }

    public Note(String headLine) {
        this(headLine, "", new Date());
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

    public Date getDate() {
        return date;
    }

    public void editDate(Date date) {
        this.date = date;
    }
}
