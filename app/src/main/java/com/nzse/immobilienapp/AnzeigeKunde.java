package com.nzse.immobilienapp;


import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.nzse.immobilienapp.backend.Anzeige;
import java.io.File;
import java.io.IOException;

public class AnzeigeKunde extends AppCompatActivity {
    private static final String TAG = "AnzeigeKunde";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anzeige_kunde);
        getIncomingIntent();

    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("AnzeigeIndex")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");


            final int pos = getIntent().getIntExtra("AnzeigeIndex", 0);
            final Anzeige a = MainActivity.bcd.getAnzeigeList().get(pos);
            ImageView img = findViewById(R.id.galleryImage);
            img.setImageURI(Uri.fromFile(new File(a.getImageResource())));

            TextView adresse = findViewById(R.id.galleryAdresse);
            TextView price = findViewById(R.id.galleryPreis);
            TextView numberRoomes = findViewById(R.id.galleryAnzahlZimmer) ;
            TextView angebot = findViewById(R.id.galleryAngebot);
            TextView surface = findViewById(R.id.galleryFlaeche);

            adresse.setText(a.getAdresse());
            price.setText(Float.toString(a.getPreis()));
            numberRoomes.setText(Integer.toString(a.getZimmerAnzahl()));

            if (a.ismAngebot())
                angebot.setText(R.string.kauf);
            else
                angebot.setText(R.string.miete);

            surface.setText(Float.toString(a.getFlaeche()));

            final ImageButton favoritButton = findViewById(R.id.favoritButton);

            if (a.isFavorit())
                favoritButton.setImageResource(android.R.drawable.btn_star_big_on);
            else
                favoritButton.setImageResource(android.R.drawable.btn_star_big_off);




            favoritButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        toggleFavorit(pos,a);
                    if (MainActivity.bcd.getAnzeigeList().get(pos).isFavorit())
                        favoritButton.setImageResource(android.R.drawable.btn_star_big_on);
                    else
                        favoritButton.setImageResource(android.R.drawable.btn_star_big_off);

                }
            });

        }
    }

    public  void toggleFavorit (int pos, Anzeige a){
        MainActivity.bcd.getAnzeigeList().get(pos).setFavorit(!a.isFavorit());
        try {
            MainActivity.bcd.saveFile(MainActivity.bcd);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
