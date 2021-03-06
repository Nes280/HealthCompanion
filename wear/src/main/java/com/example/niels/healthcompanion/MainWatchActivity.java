package com.example.niels.healthcompanion;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.WearableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainWatchActivity extends WearableActivity {
    private static ArrayList<Pair<Integer, String>> mIcons;
    private TextView mHeader;
    private Intent mIntent;

    //Sensor and SensorManager
    Sensor mHeartRateSensor;
    SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_watch);


        //Sensor and sensor manager
        mSensorManager = ((SensorManager)getSystemService(SENSOR_SERVICE));
        mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);


        // Sample icons for the list
        mIcons = new ArrayList<Pair<Integer, String>>();
        mIcons.add(Pair.create(R.drawable.ic_shoe, "Pas"));
        mIcons.add(Pair.create(R.drawable.ic_cardio, "Pouls"));
        mIcons.add(Pair.create(R.drawable.ic_action_call, "Appel) d'urgance"));
        mIcons.add(Pair.create(R.drawable.ic_action_mail, "Notification"));
        mIcons.add(Pair.create(R.drawable.ic_action_overflow, "Paramètres"));
        // This is our list header
        mHeader = (TextView) findViewById(R.id.header);

        WearableListView wearableListView =
                (WearableListView) findViewById(R.id.wearable_List);
        wearableListView.setAdapter(new WearableAdapter(this, mIcons));
        wearableListView.setClickListener(mClickListener);
        wearableListView.addOnScrollListener(mOnScrollListener);
    }

    // Handle our Wearable List's click events
    private WearableListView.ClickListener mClickListener =
            new WearableListView.ClickListener() {
                @Override
                public void onClick(WearableListView.ViewHolder viewHolder) {
                    /*Toast.makeText(MainWatchActivity.this,
                            String.format("You selected item #%s",
                                    viewHolder.getLayoutPosition()+1),
                            Toast.LENGTH_SHORT).show();*/
                    switch (viewHolder.getLayoutPosition()+1) {

                        case 1:
                            mIntent= new Intent(MainWatchActivity.this, Pas_Activity.class);
                            startActivity(mIntent);
                            break;
                        case 2:
                            if (mHeartRateSensor != null) {
                                mIntent = new Intent(MainWatchActivity.this, Pouls_Activity.class);
                                startActivity(mIntent);
                            }
                            else {
                                Toast.makeText(MainWatchActivity.this,
                                        String.format(getString(R.string.unavailable_sensor)),
                                        Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 3:
                            mIntent= new Intent(MainWatchActivity.this, Aide_Activity.class);
                            startActivity(mIntent);
                            break;

                        default:
                            Toast.makeText(MainWatchActivity.this,
                                    String.format(getString(R.string.unavailable)),
                                    Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onTopEmptyRegionClick() {
                    Toast.makeText(MainWatchActivity.this,
                            "Top empty area tapped", Toast.LENGTH_SHORT).show();
                }
            };

    // The following code ensures that the title scrolls as the user scrolls up
    // or down the list
    private WearableListView.OnScrollListener mOnScrollListener =
            new WearableListView.OnScrollListener() {
                @Override
                public void onAbsoluteScrollChange(int i) {
                    // Only scroll the title up from its original base position
                    // and not down.
                    if (i > 0) {
                        mHeader.setY(-i);
                    }
                }

                @Override
                public void onScroll(int i) {
                    // Placeholder
                }

                @Override
                public void onScrollStateChanged(int i) {
                    // Placeholder
                }

                @Override
                public void onCentralPositionChanged(int i) {
                    // Placeholder
                }
            };



}
