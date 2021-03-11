package com.example.gbnotes;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class NotesFireBase implements NotesSource {

    private static final String NOTES_COLLECTION = "notes";
    private static final String TAG = "[NotesFireBase]";

    // База данных Firestore
    private FirebaseFirestore store = FirebaseFirestore.getInstance();

    // Коллекция документов
    private CollectionReference collection = store.collection(NOTES_COLLECTION);

    // Загружаемый список карточек
    private List<Note> notesData = new ArrayList<>();

    @Override
    public NotesSource init(final NotesSourceResponse notesSourceResponse) {
        // Получить всю коллекцию, отсортированную по полю «Дата»
        collection.orderBy(NoteMapping.Fields.DATE, Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    // При удачном считывании данных загрузим список карточек
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            notesData = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> doc = document.getData();
                                String id = document.getId();
                                Note note = NoteMapping.toNote(id, doc);
                                notesData.add(note);
                            }
                            Log.d(TAG, "success " + notesData.size() + " qnt");
                            notesSourceResponse.initialized(NotesFireBase.this);
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "get failed with ", e);
                    }
                });
        return this;
    }

    @Override
    public Note getNote(int position) {
        return notesData.get(position);
    }

    @Override
    public int size() {
        if (notesData == null) {
            return 0;
        }
        return notesData.size();
    }

    @Override
    public void deleteNote(int position) {
        // Удалить документ с определённым идентификатором
        collection.document(notesData.get(position).getId()).delete();
        notesData.remove(position);
    }

    @Override
    public void updateNote(int position, Note note) {
        String id = note.getId();
        // Изменить документ по идентификатору
        collection.document(id).set(NoteMapping.toDocument(note));
        notesData.set(position, note);
    }

    @Override
    public void addNote(final Note note) {
        // Добавить документ
        collection.add(NoteMapping.toDocument(note)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                note.setId(documentReference.getId());
            }
        });
        notesData.add(note);
    }

    @Override
    public void clearNotes() {
        for (Note note : notesData) {
            collection.document(note.getId()).delete();
        }
        notesData = new ArrayList<>();
    }
}
