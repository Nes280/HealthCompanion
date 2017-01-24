package com.example.niels.healthcompanion;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.wearable.view.WatchViewStub;
import android.text.Layout;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static java.lang.System.currentTimeMillis;

/**
 * Created by S-Setsuna-F on 21/01/2017.
 */

public class Pas_Activity extends Activity implements SensorEventListener {

    private boolean isSensorPresent = false;
    private SensorManager mSensorManager;
    private Sensor mStepDetectorSensor;
    private Sensor mStepCounterSensor;

    private RelativeLayout mRectLayout;
    private RelativeLayout mRoundLayout;
    private TextView textView_pas;
    private TextView textView_date;
    private TextView textView_pas_par_seconde;

    private int currentStep = 0;
    private long currentDateStep = 0;
    private boolean firstTime = true;

    private ArrayList<Calendar> dateList;


    protected void onCreate(Bundle  savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_display_pas);
        setContentView(R.layout.rect_pas);

        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)  != null) {
            mStepCounterSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isSensorPresent = true;
        }
        else{
            isSensorPresent = false;
        }

        textView_pas = (TextView) findViewById(R.id.tView_val_pas);
        textView_date = (TextView) findViewById(R.id.tView_date);
        textView_pas_par_seconde = (TextView) findViewById(R.id.tView_pas_par_seconde);

    //    final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub_pas);
    //    stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
    //        @Override
    //        public void onLayoutInflated(WatchViewStub stub) {
    //            //mCircledImageView = (CircledImageView) stub.findViewById(R.id.circle);
    //            mRectLayout  = (RelativeLayout) stub.findViewById(R.id.rect_pas_layout);
    //            //mRoundLayout  = (RelativeLayout) stub.findViewById(R.id.round_pas_layout);
    //            textView_pas = (TextView) stub.findViewById(R.id.tView_val_pas);
    //            textView_date = (TextView) stub.findViewById(R.id.tView_date);
    //            textView_pas_par_seconde = (TextView) stub.findViewById(R.id.tView_pas_par_seconde);
    //            if(textView_pas == null)
    //                Log.i("", "textVie ------------- ->>>>>  NULL ");
    //            else
    //                Log.i("", "textVie ------------- ->>>>>  NOT NULL ");
    //        }
    //    });
    }

    public void onSensorChanged(SensorEvent event) {

        Sensor sensor = event.sensor;
        float[] values = event.values;
        int value = -1;

        //Il servirat a ajouter la date dans la liste des dates.
        Calendar date = Calendar.getInstance();

        if (values.length > 0) {
            value = (int) values[0];
        }
        textView_pas.setText("666");

        if (sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            textView_pas.setText("" + value);

            //On recupere le nombre de secondes courrent.
            long currentDateStep_tmp = date.get(Calendar.SECOND);

            /////////========   Calcul du nombre de pas par seconde   =====///////
            //Si le temps entre 2 catch de mouvement est difeerents
            if(currentDateStep != currentDateStep_tmp){
                //creer la difference entre 2 steps
                //Puis diviser la difference entre ces 2 steps par la difference des deux date.
                long nbStepBySecond=0;
                if((currentDateStep_tmp - currentDateStep) > 0) {
                    nbStepBySecond = (value - currentStep) / (currentDateStep_tmp - currentDateStep);
                    Log.i("------------> step/s : ", nbStepBySecond + " step/s");
                    if(firstTime){
                        textView_pas_par_seconde.setText("0 pas/s");
                        firstTime=false;
                    }else {
                        textView_pas_par_seconde.setText(nbStepBySecond+" pas/s");
                    }
                    textView_date.setText(date.get(Calendar.DAY_OF_MONTH)+"/"+(date.get(Calendar.MONTH)+1)+"/"+date.get(Calendar.YEAR));
                }
                currentStep = value;
            }
            currentDateStep = date.get(Calendar.SECOND);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d("Activité pas", "onAccuracyChanged - accuracy: " + accuracy);
    }

    protected void onResume() {
        super.onResume();
        if(isSensorPresent) {
            mSensorManager.registerListener(this, mStepCounterSensor,  SensorManager.SENSOR_DELAY_FASTEST);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        //Unregister the listener
        if (isSensorPresent)
            mSensorManager.unregisterListener(this);
    }
    protected void onStop() {
        super.onStop();
        if(isSensorPresent) {
            mSensorManager.unregisterListener(this, mStepCounterSensor);
        }
    }

}
