package com.example.gbnotes;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gbnotes.observe.Publisher;
import com.google.android.material.textfield.TextInputEditText;

public class AddChangeNoteFragment extends Fragment {

    private boolean isLandscape;
    public static final String NOTE = "Note";
    private Note note;
    private TextInputEditText titleEditText;
    private TextInputEditText descriptionEditText;
    MainActivity mainActivity;
    Publisher publisher;

    public static AddChangeNoteFragment newInstance(Note note) {
        AddChangeNoteFragment fragment = new AddChangeNoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(NOTE);
        } else {
            note = new Note("");
        }
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
            titleEditText.setText(note.getTitle());
            descriptionEditText.setText(note.getDescription());
        }

        Button saveButton = view.findViewById(R.id.button_save);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note.editTitle(titleEditText.getText().toString());
                note.editText(descriptionEditText.getText().toString());
                publisher.notifySingle(note);
                //note = new Note(titleEditText.getText().toString(), descriptionEditText.getText().toString());

                getFragmentManager().beginTransaction().remove(AddChangeNoteFragment.this).commit();
            }
        });

    }


}