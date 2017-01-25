package com.example.niels.healthcompanion;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import static com.google.android.gms.internal.zzir.runOnUiThread;

/**
 * Created by S-Setsuna-F on 24/01/2017.
 */

public class AideService extends WearableListenerService {

    private static final String WEARABLE_DATA_PATH = "/wearable_data";

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {

        DataMap dataMap;
        for (DataEvent event : dataEvents) {
            Log.v("myTag", "DataMap received on watch: " + DataMapItem.fromDataItem(event.getDataItem()).getDataMap());
            // Check the data type
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // Check the data path
                String path = event.getDataItem().getUri().getPath();
                if (path.equals(WEARABLE_DATA_PATH)) {
                    //On envoie un SMS a toute les personnes de la base de données.
                    String num = "0615814750";

                    SharedPreferences parametre = getSharedPreferences("parametre", MODE_PRIVATE);

                    if(parametre.getString("num_urgence", "") != null || parametre.getString("num_urgence", "") != ""){
                        num = parametre.getString("num_urgence", "");
                        String nom = parametre.getString("nom", "");
                        String adresse = parametre.getString("adresse", "");
                        String numero = parametre.getString("numero", "");

                        nom = (nom==null||nom=="")?nom:"[nom]";
                        adresse = (adresse==null||adresse=="")?adresse:"[adresse]";
                        numero = (numero==null||numero=="")?numero:"[numero]";

                        //On recupere le nom de la personne
                        String msg = nom+" a besoin d'aide au plus vite ! \nElle habite à l'adresse : "+adresse+"\nVous pouvez la contacter au numero suivant : "+numero;
                        SmsManager.getDefault().sendTextMessage(num, null, msg, null, null);
                    }
                    else{
                        Toast.makeText(AideService.this,  String.format("Aucun numéro trouvé"), Toast.LENGTH_LONG).show();
                    }

                    //On recupere le nom de la personne
                    String msg = "[nomDeLaPersonne] a besoin d'aide au plus vite !";
                    SmsManager.getDefault().sendTextMessage(num, null, msg, null, null);

                }
                /*dataMap = DataMapItem.fromDataItem(event.getDataItem()).getDataMap();



                Intent messageIntent = new Intent();
                messageIntent.setAction(Intent.ACTION_SEND);
                messageIntent.putExtra("datamap", dataMap.toBundle());
                LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);*/

            }
        }
    }
}
