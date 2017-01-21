package com.example.niels.healthcompanion;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class Pouls_Activity extends Activity implements SensorEventListener {

    //UI Elements
    private RelativeLayout mRLayout;
    private TextView mTextView;

    //Sensor and SensorManager
    Sensor mHeartRateSensor;
    SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_pouls);

        //Sensor and sensor manager
        mSensorManager = ((SensorManager)getSystemService(SENSOR_SERVICE));
        mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);

        //View
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                //mCircledImageView = (CircledImageView) stub.findViewById(R.id.circle);
                mRLayout  = (RelativeLayout) stub.findViewById(R.id.round_layout);
                mTextView = (TextView) stub.findViewById(R.id.value);
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        //Register the listener
        if (mSensorManager != null){
            mSensorManager.registerListener(this, mHeartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Unregister the listener
        if (mSensorManager!=null)
            mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //Update your data. This check is very raw. You should improve it when the sensor is unable to calculate the heart rate
        if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
            if ((int)event.values[0]>0) {
                //mCircledImageView.setBackgroundColor(getResources().getColor(R.color.green));
                int pouls = (int) event.values[0];
                if (pouls < 100)
                {
                    mRLayout.setBackgroundColor(getResources().getColor(R.color.green));
                }
                else if(pouls > 100 && pouls < 120)
                {
                    mRLayout.setBackgroundColor(getResources().getColor(R.color.orange));
                }
                else mRLayout.setBackgroundColor(getResources().getColor(R.color.red));
                mTextView.setText("" + (int) event.values[0]);
                Log.d("SENSOR--->",""+event.values[0]);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}