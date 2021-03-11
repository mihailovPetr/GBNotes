package com.example.gbnotes;

import android.content.res.Resources;
import android.text.format.DateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Notes implements NotesSource {

    private List<Note> notes;
    private Resources resources;

    public Notes(Resources resources) {
        this.resources = resources;
        notes = new ArrayList<>(15);
    }

    @Override
    public NotesSource init(NotesSourceResponse notesSourceResponse) {
        String[] titles = resources.getStringArray(R.array.titles);
        String[] descriptions = resources.getStringArray(R.array.descriptions);
        String[] dates = resources.getStringArray(R.array.dates);

        for (int i = 0; i < titles.length; i++) {
            notes.add(new Note(titles[i], descriptions[i], new Date()));
        }

        if (notesSourceResponse != null){
            notesSourceResponse.initialized(this);
        }

        notesSourceResponse.initialized(this);
        return this;
    }


    @Override
    public Note getNote(int position) {
        return notes.get(position);
    }

    @Override
    public int size() {
        return notes.size();
    }

    @Override
    public void deleteNote(int position) {
        notes.remove(position);
    }

    @Override
    public void updateNote(int position, Note note) {
        notes.set(position, note);
    }

    @Override
    public void addNote(Note note) {
        notes.add(note);
    }

    @Override
    public void clearNotes() {
        notes = new ArrayList<>();
    }
}
