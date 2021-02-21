package com.example.gbnotes;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.example.gbnotes.observe.Publisher;

public class MainActivity extends AppCompatActivity {

    private NotesSource notes;
    private Publisher publisher = new Publisher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initNotes();
        setContentView(R.layout.activity_main);
        initViews();

    }

    private void initNotes() {
        notes = new Notes(getResources());
    }

    public NotesSource getNotes() {
        return notes;
    }

    private void initViews() {
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public Publisher getPublisher() {
        return publisher;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        switch (id) {
//            case R.id.action_settings:
//                return true;
//            case R.id.action_favorite:
//                return true;
//            case R.id.action_add:
//                Toast.makeText(MainActivity.this, "add note", Toast.LENGTH_SHORT).show();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        MenuItem search = menu.findItem(R.id.action_search);
//        SearchView searchText = (SearchView) search.getActionView();
//        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return true;
//            }
//        });
//
//        return true;
//    }


}