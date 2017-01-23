package com.example.niels.healthcompanion;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
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
    private TextView textView_pas;
    private TextView textView_date;
    private TextView textView_pas_par_seconde;

    private int currentStep = 0;
    private long currentDateStep = 0;

    private ArrayList<Calendar> dateList;


    protected void onCreate(Bundle  savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_pas);
        textView_pas = (TextView) findViewById(R.id.tView_val_pas);
        textView_date = (TextView) findViewById(R.id.tView_date);
        textView_pas_par_seconde = (TextView) findViewById(R.id.tView_pas_par_seconde);

        mSensorManager      = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        if(mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)  != null) {
            mStepCounterSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isSensorPresent = true;
        }
        else{
            isSensorPresent = false;
        }
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
                    textView_pas_par_seconde.setText(nbStepBySecond+"pas/s");
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

    protected void onStop() {
        super.onStop();
        if(isSensorPresent) {
            mSensorManager.unregisterListener(this, mStepCounterSensor);
        }
    }

}
