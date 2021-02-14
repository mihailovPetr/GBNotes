package com.example.gbnotes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

public class NoteFragment extends Fragment {

    static final String ARG_INDEX = "index";
    private int index;

    public static NoteFragment newInstance(int index) {
        NoteFragment notes = new NoteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX, index);
        notes.setArguments(args);
        return notes;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_INDEX);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        TextInputEditText headlineEditText = view.findViewById(R.id.headlineTextInputEditText);
        TextInputEditText textInputEditText = view.findViewById(R.id.textInputEditText);

        NotesSource notes = new Notes(getResources()); //((MainActivity) getActivity()).getNotes();
        Note note = notes.getNote(index);

        headlineEditText.setText(String.format("%s,  %s",note.getHeadLine(), note.getDate()));
        textInputEditText.setText(note.getDescription());
        return view;
    }
}