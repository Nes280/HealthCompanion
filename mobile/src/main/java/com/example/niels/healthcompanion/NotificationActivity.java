package com.example.niels.healthcompanion;

import android.app.Notification;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NotificationActivity extends AppCompatActivity {
    private EditText editText;
    private EditText editInt;
    private final static String TAG="TEST ----------";
    private long[] pattern = {0, 100, 1000, 300, 200, 100, 500, 200, 100};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        editText = (EditText) findViewById(R.id.editText);
        final Button button = (Button) findViewById(R.id.actionButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.e(TAG, "TEST");
                String toSend = editText.getText().toString();
                if(toSend.isEmpty())
                    toSend = getString(R.string.notif_send);
                Notification notification = new NotificationCompat.Builder(getApplication())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("HealthCompanion")
                        .setContentText(toSend)
                        .setVibrate(pattern)
                        .setVisibility(0x00000001)
                        .extend(new NotificationCompat.WearableExtender().setHintShowBackgroundOnly(true))
                        .build();
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplication());
                int notificationId = 1;
                notificationManager.notify(notificationId, notification);
                //Log.e(TAG, notification.toString());
            }
        });
        final Button button2 = (Button) findViewById(R.id.buttonHeure);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int heure;
                int min;
                editInt = (EditText) findViewById(R.id.editInt);
                heure = Integer.parseInt(editInt.getText().toString());
                NotificationEventReceiver.setupAlarm(getApplicationContext(),heure );
                Log.e("---------", ""+heure);
            }
        });
    }
}