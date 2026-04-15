package com.example.prac15;

import android.content.*;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    EditText etNote;
    Button btnAdd;
    RecyclerView recyclerView;

    List<NoteModel> list;
    NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNote = findViewById(R.id.etNote);
        btnAdd = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.recyclerView);

        list = new ArrayList<>();
        adapter = new NotesAdapter(this, list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        loadData();

        btnAdd.setOnClickListener(v -> {
            String note = etNote.getText().toString();

            if (note.isEmpty()) {
                Toast.makeText(this, "Enter note", Toast.LENGTH_SHORT).show();
                return;
            }

            ContentValues cv = new ContentValues();
            cv.put("title", note);

            getContentResolver().insert(NotesProvider.URI, cv);

            etNote.setText("");

            loadData(); // 🔥 IMPORTANT
        });
    }

    private void loadData() {
        list.clear();

        Cursor cursor = getContentResolver().query(
                NotesProvider.URI, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(new NoteModel(
                        cursor.getInt(0),
                        cursor.getString(1)
                ));
            }
            cursor.close();
        }

        adapter.notifyDataSetChanged();
    }
}
