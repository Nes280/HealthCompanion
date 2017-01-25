package com.example.niels.healthcompanion;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by S-Setsuna-F on 24/01/2017.
 */

public class Aide_Activity extends Activity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

        GoogleApiClient googleClient;
        private Button mSendButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_aide);

            // Build a new GoogleApiClient for the the Wearable API
            googleClient = new GoogleApiClient.Builder(this)
            .addApi(Wearable.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build();

            mSendButton = (Button) findViewById(R.id.button_aide);
            mSendButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                    googleClient.connect();
                }
                });
            }

            // Connect to the data layer when the Activity starts
            @Override
            protected void onStart() {
                super.onStart();
                // googleClient.connect();
            }

        @Override
        public void onConnected(Bundle connectionHint) {

            String WEARABLE_DATA_PATH = "/wearable_data";

            // Create a DataMap object and send it to the data layer
            DataMap dataMap = new DataMap();
              dataMap.putLong("time", new Date().getTime());
            dataMap.putString("hole", "1");
            dataMap.putString("front", "250");
            dataMap.putString("middle", "260");
            dataMap.putString("back", "270");
            //Requires a new thread to avoid blocking the UI
            new SendToDataLayerThread(WEARABLE_DATA_PATH, dataMap).start();
        }

        // Disconnect from the data layer when the Activity stops
        @Override
        protected void onStop() {
                if (null != googleClient && googleClient.isConnected()) {
                googleClient.disconnect();
                }
                super.onStop();
                }

        // Placeholders for required connection callbacks
        @Override
        public void onConnectionSuspended(int cause) { }

        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) { }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                //getMenuInflater().inflate(R.menu.menu_data_map, menu);
                return true;
                }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
                // Handle action bar item clicks here. The action bar will
                // automatically handle clicks on the Home/Up button, so long
                // as you specify a parent activity in AndroidManifest.xml.
                int id = item.getItemId();

                //noinspection SimplifiableIfStatement
                /*if (id == R.id.action_settings) {
                return true;
                }*/

                return super.onOptionsItemSelected(item);
                }

        class SendToDataLayerThread extends Thread {
            String path;
            DataMap dataMap;

            // Constructor for sending data objects to the data layer
            SendToDataLayerThread(String p, DataMap data) {
                path = p;
                dataMap = data;
            }

            public void run() {
                // Construct a DataRequest and send over the data layer
                PutDataMapRequest putDMR = PutDataMapRequest.create(path);
                putDMR.getDataMap().putAll(dataMap);
                PutDataRequest request = putDMR.asPutDataRequest();
                DataApi.DataItemResult result = Wearable.DataApi.putDataItem(googleClient, request).await();
                if (result.getStatus().isSuccess()) {
                    Log.v("myTag", "DataMap: " + dataMap + " sent successfully to data layer ");
                } else {
                    // Log an error
                    Log.v("myTag", "ERROR: failed to send DataMap to data layer");
                }
            }
}
}
