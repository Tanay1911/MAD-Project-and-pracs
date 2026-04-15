package com.example.prac41;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText websiteEditText = findViewById(R.id.website_edittext);
        EditText locationEditText = findViewById(R.id.location_edittext);
        EditText shareEditText = findViewById(R.id.share_edittext);
        findViewById(R.id.open_website_button).setOnClickListener(v -> {
            String url = websiteEditText.getText().toString();
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        });
        findViewById(R.id.open_location_button).setOnClickListener(v -> {
            String loc = locationEditText.getText().toString();
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + loc)));
        });
        findViewById(R.id.share_text_button).setOnClickListener(v -> {
            String txt = shareEditText.getText().toString();
            ShareCompat.IntentBuilder.from(this).setType("text/plain").setChooserTitle("Share with:").setText(txt).startChooser();
        });
    }
}
