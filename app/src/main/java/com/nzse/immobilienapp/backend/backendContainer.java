package com.nzse.immobilienapp.backend;

import android.content.Context;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nzse.immobilienapp.MainActivity;
import com.nzse.immobilienapp.R;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class backendContainer {
    public ArrayList<Anzeige> anzeigeList ;
    Context mContext ;

        public  backendContainer (){
            anzeigeList = new ArrayList<>();
            }
    public  backendContainer (Context context){
             anzeigeList = new ArrayList<>();
             mContext = context;
                }


     public void addAnzeige() throws IOException {
       //  Uri uri = Uri.parse("android.resource://com.nzse.immobilienapp/drawable/haus1");
        Anzeige a = new Anzeige("Darmstadt 45 , 5434",getURLForResource(R.drawable.haus2),2, 434,3,4,false,false);
        anzeigeList.add(a);


    };


    public void addAnzeige(String Adresse,String ImageResource,int anzahlZimemr,float preis,float flaeche,float maklerProvision,boolean Angebot) throws IOException {
        if (anzeigeList == null) anzeigeList = new ArrayList<>();
        Anzeige a = new Anzeige(Adresse,ImageResource,anzahlZimemr,preis,flaeche,maklerProvision,Angebot,false);
        anzeigeList.add(a);

    };


    public void saveFile(backendContainer bcd) throws IOException {

        ArrayList<Anzeige> currentAnzeige = bcd.getAnzeigeList();
        String debug = mContext.getFilesDir().getAbsolutePath();
        Gson gson = new Gson();
        String json = gson.toJson(currentAnzeige);


        FileOutputStream fos = mContext.openFileOutput("appData.json", MainActivity.MODE_PRIVATE);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);
        outputStreamWriter.write(json);
        outputStreamWriter.close();

    };

    public  void readFile (){

        Gson gson = new Gson();
        String currentDir = mContext.getFilesDir().getAbsolutePath();
        String json = BufferedReadertoString(currentDir + "/"+ "appData.json");

        Type anzeigeType = new TypeToken<ArrayList<Anzeige>>(){}.getType();
        ArrayList<Anzeige> anzeige = gson.fromJson(json, anzeigeType);
        this.anzeigeList = anzeige;
    }


    public ArrayList<Anzeige> getAnzeigeList() {
        return anzeigeList;
    }

    private static String BufferedReadertoString(String filePath)
    {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    public String getURLForResource (int resourceId) {
        //use BuildConfig.APPLICATION_ID instead of R.class.getPackage().getName() if both are not same
        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
    }

}
