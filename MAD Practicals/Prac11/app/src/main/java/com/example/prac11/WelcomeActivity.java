package com.example.prac11;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TextView tvEmail = findViewById(R.id.tvEmail);
        TextView tvPassword = findViewById(R.id.tvPassword);
        TextView tvRemember = findViewById(R.id.tvRemember);

        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");
        boolean isRemembered = getIntent().getBooleanExtra("remember", false);

        tvEmail.setText("Email: " + email);
        tvPassword.setText("Password: " + password);
        tvRemember.setText("Remember Me: " + (isRemembered ? "Checked" : "Unchecked"));
    }
}
