package com.example.prac3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etUsername = findViewById(R.id.etUsername);
        final EditText etPassword = findViewById(R.id.etPassword);
        final EditText etEmail = findViewById(R.id.etEmail);
        final EditText etPhone = findViewById(R.id.etPhone);
        final EditText etCountry = findViewById(R.id.etCountry);
        final EditText etState = findViewById(R.id.etState);
        final RadioGroup rgGender = findViewById(R.id.rgGender);
        final CheckBox cbSports = findViewById(R.id.cbSports);
        final CheckBox cbMusic = findViewById(R.id.cbMusic);
        final CheckBox cbTech = findViewById(R.id.cbTech);
        final DatePicker datePicker = findViewById(R.id.datePicker);
        final TimePicker timePicker = findViewById(R.id.timePicker);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        timePicker.setIs24HourView(true);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder sb = new StringBuilder();
                sb.append("Username: ").append(etUsername.getText().toString()).append("\n");
                sb.append("Password: ").append(etPassword.getText().toString()).append("\n");
                sb.append("Email: ").append(etEmail.getText().toString()).append("\n");
                sb.append("Phone: ").append(etPhone.getText().toString()).append("\n");
                sb.append("Country: ").append(etCountry.getText().toString()).append("\n");
                sb.append("State: ").append(etState.getText().toString()).append("\n");

                int selectedId = rgGender.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    RadioButton rb = findViewById(selectedId);
                    sb.append("Gender: ").append(rb.getText().toString()).append("\n");
                } else {
                    sb.append("Gender: Not Selected\n");
                }

                sb.append("Interests: ");
                StringBuilder interests = new StringBuilder();
                if (cbSports.isChecked()) interests.append("Sports ");
                if (cbMusic.isChecked()) interests.append("Music ");
                if (cbTech.isChecked()) interests.append("Tech ");
                sb.append(interests.toString().trim()).append("\n");

                sb.append("Birth Date: ").append(datePicker.getDayOfMonth())
                        .append("/").append(datePicker.getMonth() + 1)
                        .append("/").append(datePicker.getYear()).append("\n");

                sb.append("Birth Time: ").append(String.format(Locale.getDefault(), "%02d:%02d", timePicker.getHour(), timePicker.getMinute()));

                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                intent.putExtra("info", sb.toString());
                startActivity(intent);
            }
        });
    }
}
