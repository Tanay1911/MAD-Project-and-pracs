package com.example.prac7;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    // Arrays to hold color names and their corresponding integer values 
    String[] colorNames = {"Red", "Green", "Blue", "Yellow", "Cyan", "Magenta", "Light Gray", "White"}; 
    int[] colorValues = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,
            Color.CYAN, Color.MAGENTA, Color.LTGRAY, Color.WHITE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize views 
        LinearLayout mainLayout = findViewById(R.id.main_layout);
        ListView listView = findViewById(R.id.listView);
        // Create adapter to display color names in the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, colorNames);
        listView.setAdapter(adapter);
        // Set item click listener to change background color 
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mainLayout.setBackgroundColor(colorValues[position]);
            }
        });
    }
}
