package com.example.gbnotes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

public class NoteFragment extends Fragment {

    static final String ARG_NOTE = "note";
    private Note note;

    public static NoteFragment newInstance(Note note) {
        NoteFragment notes = new NoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        notes.setArguments(args);
        return notes;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_NOTE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        TextInputEditText headlineEditText = view.findViewById(R.id.headlineTextInputEditText);
        TextInputEditText textInputEditText = view.findViewById(R.id.textInputEditText);

        headlineEditText.setText(String.format("%s,  %s",note.getTitle(), note.getDate()));
        textInputEditText.setText(note.getDescription());
        return view;
    }
}