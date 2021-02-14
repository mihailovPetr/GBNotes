package com.example.gbnotes;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class AddChangeNoteFragment extends Fragment {

    private boolean isLandscape;
    public static final String NOTE = "Note";
    private Note note;
    private TextInputEditText titleEditText;
    private TextInputEditText descriptionEditText;

    public static AddChangeNoteFragment newInstance(Note note) {
        AddChangeNoteFragment fragment = new AddChangeNoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_change_note, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            note = savedInstanceState.getParcelable(NOTE);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleEditText = view.findViewById(R.id.addChangeTitleEditText);
        descriptionEditText = view.findViewById(R.id.addChangeDescriptionEditText);

        if (note != null) {
            titleEditText.setText(note.getHeadLine());
            descriptionEditText.setText(note.getDescription());
        }

        Button saveButton = view.findViewById(R.id.button_save);
        Fragment fragment = this;

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note = new Note(titleEditText.getText().toString(), descriptionEditText.getText().toString());
                ((MainActivity) getActivity()).getNotes().updateNote(0, note);
                getFragmentManager().beginTransaction().remove(AddChangeNoteFragment.this).commit();
            }
        });
    }


}