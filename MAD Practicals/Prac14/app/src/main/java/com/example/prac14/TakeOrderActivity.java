package com.example.prac14;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class TakeOrderActivity extends AppCompatActivity {

    EditText etPhone, etItem, etQty;
    Button btnSave;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_order);

        etPhone = findViewById(R.id.etPhone);
        etItem = findViewById(R.id.etItem);
        etQty = findViewById(R.id.etQty);
        btnSave = findViewById(R.id.btnSave);

        db = new DatabaseHelper(this);

        btnSave.setOnClickListener(v -> {

            String phone = etPhone.getText().toString();
            String item = etItem.getText().toString();
            String qty = etQty.getText().toString();

            if (phone.isEmpty() || item.isEmpty() || qty.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean inserted = db.insertData(phone, item, qty);

            if (inserted) {
                Toast.makeText(this, "Order Saved", Toast.LENGTH_SHORT).show();

                etPhone.setText("");
                etItem.setText("");
                etQty.setText("");
            } else {
                Toast.makeText(this, "Error Saving Data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
