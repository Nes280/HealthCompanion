package com.example.niels.healthcompanion;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by S-Setsuna-F on 23/01/2017.
 */

public class Receiver_pas extends BroadcastReceiver {
    public static final String CONTENT_KEY = "contentText";

    public Receiver_pas() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent displayIntent = new Intent(context, Pas_Activity.class);
        String text = intent.getStringExtra(CONTENT_KEY);
        Notification notification = new Notification.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(text)
                .extend(new Notification.WearableExtender()
                        .setDisplayIntent(PendingIntent.getActivity(context, 0, displayIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT)))
                .build();
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(0, notification);

        Toast.makeText(context, context.getString(R.string.notification_posted), Toast.LENGTH_SHORT).show();
    }
}
