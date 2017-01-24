package com.example.niels.healthcompanion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by S-Setsuna-F on 23/01/2017.
 */

public class Stub_pas extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent();
        i.setAction("com.example.niels.healthcompanion.SHOW_NOTIFICATION");
        i.putExtra(Receiver_pas.CONTENT_KEY, getString(R.string.title));
        sendBroadcast(i);
        finish();
    }
}
