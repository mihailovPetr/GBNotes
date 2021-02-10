package com.example.gbnotes;

public interface NotesSource {
    Note getNote(int position);
    int size();
}
