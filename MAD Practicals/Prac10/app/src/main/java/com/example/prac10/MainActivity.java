package com.example.prac10;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText etNo1, etNo2;
    private TextView tvOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNo1 = findViewById(R.id.et_no1);
        etNo2 = findViewById(R.id.et_no2);
        tvOutput = findViewById(R.id.tv_output);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Addition");
        menu.add(0, 2, 0, "Subtraction");
        menu.add(0, 3, 0, "Multiplication");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String no1Str = etNo1.getText().toString();
        String no2Str = etNo2.getText().toString();
        if (no1Str.isEmpty() || no2Str.isEmpty()) {
            Toast.makeText(this, "Please enter both numbers", Toast.LENGTH_SHORT).show();
            return true;
        }
        double no1 = Double.parseDouble(no1Str);
        double no2 = Double.parseDouble(no2Str);
        double result = 0;
        switch (item.getItemId()) {
            case 1: // Addition
                result = no1 + no2;
                tvOutput.setText("Result: " + result);
                return true;
            case 2: // Subtraction
                result = no1 - no2;
                tvOutput.setText("Result: " + result);
                return true;
            case 3: // Multiplication
                result = no1 * no2;
                tvOutput.setText("Result: " + result);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
