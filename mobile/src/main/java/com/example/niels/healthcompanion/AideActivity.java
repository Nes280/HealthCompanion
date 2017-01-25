package com.example.niels.healthcompanion;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.TextView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

import static com.google.android.gms.internal.zzir.runOnUiThread;

/**
 * Created by S-Setsuna-F on 24/01/2017.
 */

public class AideActivity extends Activity {

    private TextView aide;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aide);

        Log.i("CREATE", "Je suis la et j'attends");
        aide = (TextView) findViewById(R.id.textViewAide);

        String num="xx.xx.xx.xx.xx";
        SharedPreferences parametre = getSharedPreferences("parametre", MODE_PRIVATE);
        if(parametre.getString("num_urgence", "") != null || parametre.getString("num_urgence", "") != "")
            num = parametre.getString("num_urgence", "");
        aide.setText(num);

        // Register the local broadcast receiver
        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        MessageReceiver messageReceiver = new MessageReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, messageFilter);
    }

    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("onReceive", "----------------------------------------");
            Log.i("onReceive", "J'ai recu quelque chose !!!!!!!!!!!!!!!!");
            Bundle data = intent.getBundleExtra("datamap");
            // Display received data in UI
            String display = "Received from the data Layer\n" +
                    "Hole: " + data.getString("hole") + "\n" +
                    "Front: " + data.getString("front") + "\n" +
                    "Middle: "+ data.getString("middle") + "\n" +
                    "Back: " + data.getString("back");
            aide.setText(display);
        }
    }
}

