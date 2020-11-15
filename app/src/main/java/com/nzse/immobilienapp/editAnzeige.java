package com.nzse.immobilienapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nzse.immobilienapp.backend.Anzeige;

import java.io.File;
import java.io.IOException;

public class editAnzeige extends AppCompatActivity {

    private static final String TAG = "Edit Anzeige";
    public static String currentImagePath ="";
    private static final int PICK_IMAGE_REQUEST = 1;
    private View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_anzeige);
        getIncomingIntent();
    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("AnzeigeIndex")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");


            final int pos = getIntent().getIntExtra("AnzeigeIndex", 0);
            Anzeige a = MainActivity.bcd.getAnzeigeList().get(pos);



            EditText adresse= findViewById(R.id.editTextAdresse);
            adresse.setText(a.getAdresse());

            EditText numberOfRooms= findViewById(R.id.editTextZimmern);
            numberOfRooms.setText(Integer.toString(a.getZimmerAnzahl()));

            EditText price= findViewById(R.id.editTextPreis);
            price.setText(Float.toString(a.getPreis()));

            EditText surface = findViewById(R.id.editTextFläche);
            surface.setText(Float.toString(a.getFlaeche()));

            EditText makler = findViewById(R.id.editTextMaklerProvision);
            makler.setText(Float.toString(a.getMaklerProvision()));

            RadioButton angebotK = findViewById(R.id.RadioButtonKauf);
            RadioButton angebotM = findViewById(R.id.RadioButtonKauf);

            if (a.ismAngebot())
                 angebotK.toggle();
            else
                angebotM.toggle();

            EditorFragment.currentImagePath = a.getImageResource();


            ImageButton buttonImage = (ImageButton) findViewById(R.id.buttonImage);
            buttonImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View buttonView) {
                    Intent intent = new Intent(getApplicationContext(), takePhoto.class);
                    startActivity(intent);


                }
            });


            Button cancelButton = (Button) findViewById(R.id.cancelEdit);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View buttonView) {
                    finish();
                }
            });



            Button buttonSave = (Button) findViewById(R.id.button_save);
            buttonSave.setOnClickListener(new View.OnClickListener() {
                boolean wrongInput = false;

                @Override
                public void onClick(View buttonView) {
                    String Adresse="";
                    int zimmerAnzahl=0;
                    float preis=0;
                    float maklerProvison=0;
                    float flaeche = 0;
                    boolean angebot= false;
                    int imageResource = 2131165284;
                    try{
                        EditText tmp;

                        tmp= findViewById(R.id.editTextAdresse);
                        Adresse = tmp.getText().toString();



                        tmp= findViewById(R.id.editTextZimmern);
                        zimmerAnzahl = Integer.valueOf(tmp.getText().toString());

                        tmp= findViewById(R.id.editTextPreis);
                        preis = Float.valueOf(tmp.getText().toString());

                        tmp= findViewById(R.id.editTextFläche);
                        flaeche = Float.valueOf(tmp.getText().toString());

                        tmp= findViewById(R.id.editTextMaklerProvision);
                        maklerProvison = Float.valueOf(tmp.getText().toString());

                        RadioButton tmpB = findViewById(R.id.RadioButtonKauf);
                        if (tmpB.isChecked()) angebot = true;
                    }catch (NumberFormatException ne)
                    {

                        Toast.makeText(getApplicationContext(),"Falsche Eingabe",Toast.LENGTH_SHORT).show();
                        wrongInput = true;
                    }
                    if(!wrongInput){
                        try {
                            MainActivity.bcd.addAnzeige(Adresse,EditorFragment.currentImagePath,zimmerAnzahl,preis,flaeche,maklerProvison,angebot);
                            MainActivity.bcd.saveFile(MainActivity.bcd);
                            Toast.makeText(getApplicationContext(),"Anzeige hinzugefügt",Toast.LENGTH_SHORT).show();
                            removeItem(pos);
                            finish();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });


        }
    }


    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {


        return super.onCreateView(parent, name, context, attrs);
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

    }

}
