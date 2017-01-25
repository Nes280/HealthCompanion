package com.example.niels.healthcompanion;

/**
 * Created by S-Setsuna-F on 25/01/2017.
 */

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by Wasim on 08-05-2015.
 */
public class WearListCallListenerService extends WearableListenerService {

    public static String SERVICE_CALLED_WEAR = "WearListClicked";


    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);

        String event = messageEvent.getPath();

        Log.d("Listclicked", event);

        String [] message = event.split("--");

        if (message[0].equals(SERVICE_CALLED_WEAR)) {
            Intent i = new Intent(this,AideActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            ///startActivity(new Intent((Intent) AideActivity.getInstance().afficherTest(message[1]))
            ///        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
}
