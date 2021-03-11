package com.example.gbnotes;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbnotes.observe.Observer;
import com.example.gbnotes.observe.Publisher;

public class HeadlinesFragment extends Fragment {

    public static final String CURRENT_NOTE = "CurrentNote";
    private Adapter adapter;
    private RecyclerView recyclerView;
    private NotesSource notes;
    private int currentPosition = 0;
    private boolean isLandscape;
    private Publisher publisher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_headlines, container, false);
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(CURRENT_NOTE, 0);
        }

        if (isLandscape) {
            showLandNote(0);
        }
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.card_menu, menu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                showAddChaneNoteFragment(new Note(""));
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateNoteData(Note note) {
                        notes.addNote(note);
                        adapter.notifyDataSetChanged();
                    }
                });
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = adapter.getMenuPosition();
        switch (item.getItemId()) {
            case R.id.action_edit:
                showAddChaneNoteFragment(notes.getNote(position));
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateNoteData(Note note) {
                        notes.updateNote(position, note);
                        adapter.notifyItemChanged(position);
                    }
                });
                return true;
            case R.id.action_delete:
                showDeleteNoteDialog(position);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void showDeleteNoteDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.deleteNote)
                .setMessage(R.string.delete_Note_msg)
                .setPositiveButton(R.string.positive_button,
                        (dialog, id) -> {
                            deleteNote(position);
                            Toast.makeText(getContext(), "Заметка удалена", Toast.LENGTH_SHORT).show();
                        })
                .setNegativeButton(R.string.negative_button, (dialog, id) -> {
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void deleteNote(int position) {
        notes.deleteNote(position);
        adapter.notifyItemRemoved(position);
    }

    private void initList(View view) {
        recyclerView = view.findViewById(R.id.recycler);
        adapter = new Adapter(this);
        notes = ((MainActivity) requireActivity()).getNotes();
        notes.init(notes -> adapter.notifyDataSetChanged());
        adapter.setDataSource(notes);
        adapter.setOnItemClickListener((position, note) -> showNote(position));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(CURRENT_NOTE, currentPosition);
        super.onSaveInstanceState(outState);
    }

    private void showNote(int index) {
        if (isLandscape) {
            showLandNote(index);
        } else {
            showPortNote(index);
        }
    }

    private void showLandNote(int index) {
        NoteFragment noteFragment = NoteFragment.newInstance(notes.getNote(index));

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.note_FrameLayout, noteFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    private void showPortNote(int index) {
        Intent intent = new Intent(getActivity(), NoteActivity.class);
        intent.putExtra(NoteFragment.ARG_NOTE, notes.getNote(index));
        startActivity(intent);
    }

    private void showAddChaneNoteFragment(Note note) {
        AddChangeNoteFragment addChangeNoteFragment = AddChangeNoteFragment.newInstance(note);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.headlines_fragment, addChangeNoteFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public NotesSource getNotes() {
        return notes;
    }
}