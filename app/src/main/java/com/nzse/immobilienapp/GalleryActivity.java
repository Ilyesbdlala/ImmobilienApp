package com.nzse.immobilienapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nzse.immobilienapp.backend.Anzeige;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;

public class GalleryActivity extends AppCompatActivity {
    private static final String TAG = "GalleryActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        getIncomingIntent();
    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("AnzeigeIndex")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");


            final int pos = getIntent().getIntExtra("AnzeigeIndex", 0);
            Anzeige a = MainActivity.bcd.getAnzeigeList().get(pos);
            ImageView img = findViewById(R.id.galleryImage);
            img.setImageURI(Uri.fromFile(new File(a.getImageResource())));

            TextView adresse = findViewById(R.id.galleryAdresse);
            TextView price = findViewById(R.id.galleryPreis);
            TextView numberRoomes = findViewById(R.id.galleryAnzahlZimmer) ;
            TextView angebot = findViewById(R.id.galleryAngebot);
            TextView surface = findViewById(R.id.galleryFlaeche);
            TextView makler = findViewById(R.id.galleryMakler);


            adresse.setText(a.getAdresse());
            price.setText(Float.toString(a.getPreis()));
            numberRoomes.setText(Integer.toString(a.getZimmerAnzahl()));

                      if (a.ismAngebot())
                          angebot.setText(R.string.kauf);
                      else
                          angebot.setText(R.string.miete);

            surface.setText(Float.toString(a.getFlaeche()));
            makler.setText(Float.toString(a.getMaklerProvision()));

            ImageButton deleteButton = findViewById(R.id.deleteGalleryButton);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                    alertDialog.setTitle(R.string.delete);
                    alertDialog.setMessage(R.string.areYouSure);
                    alertDialog.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           removeItem(pos);
                    }
                    });


                    alertDialog.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog dialog = alertDialog.create();
                    dialog.show();

                }
            });




            ImageButton editButton = findViewById(R.id.editGalleryButton);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext() , editAnzeige.class);
                    intent.putExtra("AnzeigeIndex", pos);
                    startActivity(intent);
                    finish();

                }
            });

        }
    }


    public  void removeItem (int pos){
            MainActivity.bcd.getAnzeigeList().remove(pos);
            AnzeigeFragment.mAdapter.notifyDataSetChanged();
            MainActivity.bcd.anzeigeList =  AnzeigeFragment.mAdapter.getmAnzeigeList();
            try {
                MainActivity.bcd.saveFile(MainActivity.bcd);
            } catch (IOException e) {
                e.printStackTrace();
            }

            finish();
             }


}


