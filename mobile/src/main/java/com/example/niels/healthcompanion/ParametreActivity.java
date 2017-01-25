package com.example.niels.healthcompanion;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by S-Setsuna-F on 25/01/2017.
 */

public class ParametreActivity extends Activity {
    private EditText et_name;
    private EditText et_prenom;
    private EditText et_age;
    private EditText et_poids;
    private EditText et_taille;
    private EditText et_numero;
    private EditText et_numero_urgence;
    private EditText et_adresse_postale;
    private Button   button_save;
    SharedPreferences parametre;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametre);

        et_name             = (EditText) findViewById(R.id.eText_name);
        et_prenom           = (EditText) findViewById(R.id.eText_prenom);
        et_age              = (EditText) findViewById(R.id.eText_age);
        et_poids            = (EditText) findViewById(R.id.eText_poids);
        et_taille           = (EditText) findViewById(R.id.eText_taille);
        et_numero           = (EditText) findViewById(R.id.eText_phone);
        et_numero_urgence   = (EditText) findViewById(R.id.eText_num_urgence);
        et_adresse_postale  = (EditText) findViewById(R.id.eText_adresse);
        button_save         = (Button)   findViewById(R.id.button_save);
        parametre           =            getSharedPreferences("parametre", MODE_PRIVATE);

        button_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                SharedPreferences.Editor edit = parametre         .edit();
                edit.putString("nom",           et_name           .getText().toString().trim());
                edit.putString("prenom",        et_prenom         .getText().toString().trim());
                edit.putString("age",           et_age            .getText().toString().trim());
                edit.putString("poids",         et_poids          .getText().toString().trim());
                edit.putString("taille",        et_taille         .getText().toString().trim());
                edit.putString("numero",        et_numero         .getText().toString().trim());
                edit.putString("num_urgence",   et_numero_urgence .getText().toString().trim());
                edit.putString("adresse_post",  et_adresse_postale.getText().toString().trim());
                edit.commit();
                setResult(1);
                finish();
            }
        });

        if(parametre.getString("nom", "") != null || parametre.getString("nom", "") != "")
            et_name.setText(parametre.getString("nom", "") );
        else
            et_name.setText("");

        if(parametre.getString("prenom", "") != null || parametre.getString("prenom", "") != "")
            et_prenom.setText(parametre.getString("prenom", "") );
        else
            et_prenom.setText("");


        if(parametre.getString("age", "") != null || parametre.getString("age", "") != "")
            et_age.setText(parametre.getString("age", "") );
        else
            et_age.setText("");

        if(parametre.getString("poids", "") != null || parametre.getString("poids", "") != "")
            et_poids.setText(parametre.getString("poids", "") );
        else
            et_poids.setText("");

        if(parametre.getString("taille", "") != null || parametre.getString("taille", "") != "")
            et_taille.setText(parametre.getString("taille", "") );
        else
            et_taille.setText("");

        if(parametre.getString("numero", "") != null || parametre.getString("numero", "") != "")
            et_numero.setText(parametre.getString("numero", "") );
        else
            et_numero.setText("");

        if(parametre.getString("num_urgence", "") != null || parametre.getString("num_urgence", "") != "")
            et_numero_urgence.setText(parametre.getString("num_urgence", "") );
        else
            et_numero_urgence.setText("");

        if(parametre.getString("adresse_post", "") != null || parametre.getString("adresse_post", "") != "")
            et_adresse_postale.setText(parametre.getString("adresse_post", "") );
        else
            et_adresse_postale.setText("");
    }


    @Override
    protected void onStop() {
        super.onStop();
    }
}
