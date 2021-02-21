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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
        MainActivity activity = (MainActivity)context;
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
        switch (item.getItemId()){
            case R.id.action_add:
                notes.addNote(new Note("Тестовая"));
                adapter.notifyItemInserted(notes.size() - 1);
                recyclerView.smoothScrollToPosition(notes.size() - 1);
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
                notes.deleteNote(position);
                adapter.notifyItemRemoved(position);
                return true;
        }
        return super.onContextItemSelected(item);
    }


    private void initList(View view) {
        recyclerView = view.findViewById(R.id.recycler);
        notes = ((MainActivity) requireActivity()).getNotes();
        adapter = new Adapter(notes, this);
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
        NoteFragment noteFragment = NoteFragment.newInstance(index);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.note_FrameLayout, noteFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    private void showPortNote(int index) {
        Intent intent = new Intent(getActivity(), NoteActivity.class);
        intent.putExtra(NoteFragment.ARG_INDEX, index);
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

    public NotesSource getNotes(){
       return notes;
    }
}