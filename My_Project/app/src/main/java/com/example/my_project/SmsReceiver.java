package com.example.my_project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus != null) {
                    for (Object pdu : pdus) {
                        try {
                            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                            String sender = smsMessage.getDisplayOriginatingAddress();
                            String encryptedBody = smsMessage.getMessageBody();
                            
                            try {
                                // Attempt to decrypt
                                String decryptedBody = EncryptionHelper.decrypt(encryptedBody);
                                
                                // Show the decrypted message
                                Toast.makeText(context, "Decrypted SMS from " + sender + ":\n" + decryptedBody, Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                // If decryption fails, it's likely a regular unencrypted SMS
                                // We can choose to show it as is, or ignore it
                                // For now, let's just show the original body if decryption fails
                                // Toast.makeText(context, "New SMS from " + sender + ": " + encryptedBody, Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
