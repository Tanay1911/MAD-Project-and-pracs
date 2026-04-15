package com.example.my_project;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 123;
    private EditText editTextReceiverNumber;
    private EditText editTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextReceiverNumber = findViewById(R.id.editTextReceiverNumber);
        editTextMessage = findViewById(R.id.editTextMessage);
        Button buttonSend = findViewById(R.id.buttonSend);

        buttonSend.setOnClickListener(v -> {
            if (checkPermissions()) {
                sendMessage();
            } else {
                requestPermissions();
            }
        });
    }

    private boolean checkPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 2 && 
                grantResults[0] == PackageManager.PERMISSION_GRANTED && 
                grantResults[1] == PackageManager.PERMISSION_GRANTED && 
                grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                // Permissions granted
            } else {
                Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sendMessage() {
        String receiverNumber = editTextReceiverNumber.getText().toString().trim();
        String messageContent = editTextMessage.getText().toString().trim();

        if (TextUtils.isEmpty(receiverNumber)) {
            editTextReceiverNumber.setError("Receiver's number is required.");
            return;
        }
        if (TextUtils.isEmpty(messageContent)) {
            editTextMessage.setError("Message cannot be empty.");
            return;
        }

        try {
            // Encrypt the message before sending
            String encryptedMessage = EncryptionHelper.encrypt(messageContent);
            
            SmsManager smsManager = this.getSystemService(SmsManager.class);
            
            // Send the encrypted message
            smsManager.sendTextMessage(receiverNumber, null, encryptedMessage, null, null);
            
            // Manually save the ORIGINAL (decrypted) message to sent folder so user can read it
            saveToSentFolder(receiverNumber, messageContent);

            Toast.makeText(MainActivity.this, "Encrypted message sent", Toast.LENGTH_SHORT).show();
            editTextMessage.setText("");
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Failed to send: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void saveToSentFolder(String number, String message) {
        try {
            ContentValues values = new ContentValues();
            values.put("address", number);
            values.put("body", message);
            values.put("date", System.currentTimeMillis());
            values.put("read", 1);
            values.put("type", 2); // 2 = sent folder
            getContentResolver().insert(Uri.parse("content://sms/sent"), values);
        } catch (Exception e) {
            Log.e("MainActivity", "Failed to save SMS to sent folder", e);
        }
    }
}