package com.example.niels.healthcompanion;

import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class MainPhoneActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private final List<Pair<String, String>> characters = Arrays.asList(
            Pair.create("Pas", "Visualisez le nombre de pas effectuer dans la journée."),
            Pair.create("Pouls", "Visualisez la moyenne du pouls et un instantané."),
            Pair.create("Appel d'urgance", "Paramétrez les numéros à appeler en cas d'urgeance."),
            Pair.create("Notification", "Paramétrez divers rappels"),
            Pair.create("Paramètres", "paragmétreges générale de l'application.")

    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_phone);

        mRecyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //String[]maListe = {"test1", "test2", "test3", "test4"};
        // specify an adapter (see also next example)
        mAdapter = new MainAdapter(characters);
        mRecyclerView.setAdapter(mAdapter);
        this.getString(R.string.footstep);
    }
}
