package com.example.prac14;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ViewOrderActivity extends AppCompatActivity {

    ListView listView;
    DatabaseHelper db;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        listView = findViewById(R.id.listView);
        db = new DatabaseHelper(this);
        list = new ArrayList<>();

        Cursor cursor = db.getData();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Orders Found", Toast.LENGTH_SHORT).show();
            return;
        }

        while (cursor.moveToNext()) {
            list.add("Phone: " + cursor.getString(1) +
                    "\nItem: " + cursor.getString(2) +
                    "\nQty: " + cursor.getString(3));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, list);

        listView.setAdapter(adapter);
    }
}
