package com.example.niels.healthcompanion;

import android.app.Notification;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

public class NotificationActivity extends AppCompatActivity {
    private final static String TAG="TEST ----------";
    public static final String PREFS_NAME = "MyPrefsFile";

    private EditText editText;
    private EditText editIntWater;
    private EditText editIntMedicament;
    private ToggleButton toggleButtonWater;
    private ToggleButton toggleButtonMedication;
    private boolean isEditedWater = false;
    private boolean isEditedMedicament = false;
    private long[] pattern = {0, 100, 1000, 300, 200, 100, 500, 200, 100};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        setContentView(R.layout.activity_notification);
        editText = (EditText) findViewById(R.id.editText);

        //Bouton de test
        final Button button = (Button) findViewById(R.id.actionButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.e(TAG, "TEST");
                String toSend = editText.getText().toString();
                if(toSend.isEmpty())
                    toSend = getString(R.string.notif_send);
                Notification notification = new NotificationCompat.Builder(getApplication())
                        .setSmallIcon(R.mipmap.notification_flat)
                        .setContentTitle("HealthCompanion")
                        .setContentText(toSend)
                        .setVibrate(pattern)
                        .setSound(alarmSound)
                        .setColor(getResources().getColor(R.color.wallet_holo_blue_light))
                        .setVisibility(0x00000001)
                        .extend(new NotificationCompat.WearableExtender().setHintShowBackgroundOnly(true))
                        .build();
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplication());
                int notificationId = 1;
                notificationManager.notify(notificationId, notification);
                //Log.e(TAG, notification.toString());
            }
        });


        toggleButtonWater = (ToggleButton) findViewById(R.id.toggleButtonWater);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        int water = settings.getInt("water", 0);
        editIntWater = (EditText) findViewById(R.id.editTextWater);

        if (water > 0) {
            toggleButtonWater.setChecked(true);
            editIntWater.setText(water+"");
        }
        else  {
            toggleButtonWater.setChecked(false);

        }

        toggleButtonWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEditedWater=true;
                if(toggleButtonWater.isChecked() ) {
                    int heure ;
                    //Log.e(TAG, "--->"+editIntWater.getText().toString().equals("")+"<---");
                    if (!editIntWater.getText().toString().equals("0") && !editIntWater.getText().toString().equals("")) {
                        //Log.e(TAG, "--->"+editIntWater.getText().toString()+"<---");
                        heure = Integer.parseInt(editIntWater.getText().toString());
                        NotificationEventReceiverWater.setupAlarm(getApplicationContext(), heure);
                        Log.e("---------water", "" + heure);
                    }
                    else {
                        toggleButtonWater.setChecked(false);
                        isEditedWater = false;
                    }
                }
                else {
                    editIntWater.setText(0+"");
                    NotificationEventReceiverWater.getDeleteIntent(getApplicationContext());
                    Log.e("---------water", "delete");
                }
            }
        });


        toggleButtonMedication = (ToggleButton) findViewById(R.id.toggleButtonMedicament);
        SharedPreferences settings2 = getSharedPreferences(PREFS_NAME, 0);
        int medicament = settings2.getInt("medicament", 0);
        editIntMedicament = (EditText) findViewById(R.id.editTextMedicament);

        if (medicament > 0) {
            toggleButtonMedication.setChecked(true);
            editIntMedicament.setText(medicament+"");
        }
        else  {
            toggleButtonMedication.setChecked(false);
        }

        toggleButtonMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEditedMedicament = true;
                if(toggleButtonMedication.isChecked()) {
                    int heure;
                    if (!editIntMedicament.getText().toString().equals("0") && !editIntMedicament.getText().toString().equals(""))
                    {
                    heure = Integer.parseInt(editIntMedicament.getText().toString());
                        NotificationEventReceiverWater.setupAlarm(getApplicationContext(), heure);
                        Log.e("---------medication", "" + heure);
                    }
                    else {
                        toggleButtonMedication.setChecked(false);
                        isEditedMedicament = false;
                    }
                }
                else {
                    editIntMedicament.setText(0+"");
                    NotificationEventReceiverWater.getDeleteIntent(getApplicationContext());
                    Log.e("---------medication", "delete");
                }
            }
        });
    }
    public void alert()
    {

    }
    @Override
    protected void onStop(){
        super.onStop();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        if (isEditedMedicament && isEditedWater) {
            editor.putInt("water", Integer.parseInt(editIntWater.getText().toString()));
            editor.putInt("medicament", Integer.parseInt(editIntMedicament.getText().toString()));
            editor.commit();
        }
        else if(isEditedMedicament)
        {
            editor.putInt("medicament", Integer.parseInt(editIntMedicament.getText().toString()));
            editor.commit();
        }
       else if(isEditedWater)
        {
            editor.putInt("water", Integer.parseInt(editIntWater.getText().toString()));
            editor.commit();
        }
    }
}