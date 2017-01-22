package com.example.niels.healthcompanion;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainPhoneActivity extends Activity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList liste = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_phone);
        /**
         * Creation de la liste
         */
        Triple pas = new Triple(getString(R.string.footstep), getString(R.string.footstap_desc), 1);
        liste.add(pas);
        Triple Pouls = new Triple(getString(R.string.pulse), getString(R.string.pulse_desc), 2);
        liste.add(Pouls);
        Triple Appel = new Triple(getString(R.string.emergency_call), getString(R.string.emergency_desc), 3);
        liste.add(Appel);
        Triple Notification = new Triple(getString(R.string.notification), getString(R.string.notification_desc), 4);
        liste.add(Notification);
        Triple Paramètres = new Triple(getString(R.string.settings), getString(R.string.settings_desc), 5);
        liste.add(Paramètres);



        mRecyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //String[]maListe = {"test1", "test2", "test3", "test4"};
        // specify an adapter (see also next example)
        mAdapter = new MainAdapter(liste);
        mRecyclerView.setAdapter(mAdapter);
    }

}
