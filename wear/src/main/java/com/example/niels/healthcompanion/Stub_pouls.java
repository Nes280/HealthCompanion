package com.example.niels.healthcompanion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Example shell activity which simply broadcasts to our receiver and exits.
 */
public class Stub_pouls extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent();
        i.setAction("com.example.niels.healthcompanion.SHOW_NOTIFICATION");
        i.putExtra(Receiver_pouls.CONTENT_KEY, getString(R.string.title));
        sendBroadcast(i);
        finish();
    }
}
