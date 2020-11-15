package com.nzse.immobilienapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.nzse.immobilienapp.backend.anzeigeAdapter;
import com.nzse.immobilienapp.backend.anzeigeAdapterKunde;

public class AnzeigeKundeGallery extends AppCompatActivity {
    public static anzeigeAdapterKunde mAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anzeige_kunde_gallery);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        anzeigeAdapterKunde adapter = new anzeigeAdapterKunde(this,MainActivity.bcd.getAnzeigeList());
        mAdapter = adapter;
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }
}
