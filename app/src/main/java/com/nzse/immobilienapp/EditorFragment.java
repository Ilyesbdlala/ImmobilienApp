package com.nzse.immobilienapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import static android.app.Activity.RESULT_OK;

public class EditorFragment extends Fragment {
    public static String currentImagePath ="";
    private static final int PICK_IMAGE_REQUEST = 1;
    private View view;
    public  Uri imageRes = Uri.parse("android.resource://com.nzse.immobilienapp/drawable/haus3");


    @Nullable
    @Override


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_editor, container, false);

    }


    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {

        this.view = view;
        Button buttonSave = (Button) view.findViewById(R.id.button_save);
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

                tmp= view.findViewById(R.id.editTextAdresse);
                Adresse = tmp.getText().toString();



                tmp= view.findViewById(R.id.editTextZimmern);
                zimmerAnzahl = Integer.valueOf(tmp.getText().toString());

                tmp= view.findViewById(R.id.editTextPreis);
                preis = Float.valueOf(tmp.getText().toString());

                tmp= view.findViewById(R.id.editTextFläche);
                flaeche = Float.valueOf(tmp.getText().toString());

                tmp= view.findViewById(R.id.editTextMaklerProvision);
                maklerProvison = Float.valueOf(tmp.getText().toString());

                RadioButton tmpB = view.findViewById(R.id.RadioButtonKauf);
                if (tmpB.isChecked()) angebot = true;
                }catch (NumberFormatException ne)
                {

                    Toast.makeText(view.getContext(),"Falsche Eingabe",Toast.LENGTH_SHORT).show();
                    wrongInput = true;
                }
                if(!wrongInput){
                try {
                    MainActivity.bcd.addAnzeige(Adresse,currentImagePath,zimmerAnzahl,preis,flaeche,maklerProvison,angebot);
                    MainActivity.bcd.saveFile(MainActivity.bcd);
                    Toast.makeText(view.getContext(),"Anzeige hinzugefügt",Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                }

            }
        });

        this.view = view;

        ImageButton buttonImage = (ImageButton) view.findViewById(R.id.buttonImage);

        buttonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View buttonView) {
                Intent intent = new Intent(view.getContext(), takePhoto.class);
                startActivity(intent);
            }
        });

    }


    }




