package com.nzse.immobilienapp.backend;

import android.net.Uri;

import com.nzse.immobilienapp.R;

public class Anzeige {
    private static int ID = 0;
    private  int mID ;
    private String mAdresse;
    private String mImageResource;
    private int[] otherImages;



    private float mflaeche;
    private int mZimmerAnzahl;
    private float mPreis;
    private float mMaklerProvision;



    private boolean mAngebot ; // 1 fur Kauf und 0 fur Miete


    public void setFavorit(boolean istFavorit) {
        this.istFavorit = istFavorit;
    }

    private boolean istFavorit ;




    Anzeige(String Adresse, String imageResource, int ZimmerAnzahl, float Preis, float flaeche, float MaklerProvision, boolean Angebot, boolean favorit) {
        mID = ID++;
        mImageResource = imageResource;
        mZimmerAnzahl = ZimmerAnzahl;
        mPreis = Preis;
        mMaklerProvision = MaklerProvision;
        mAngebot = Angebot ;
        mAdresse = Adresse;
        mflaeche = flaeche;
        istFavorit = favorit;

    }

    public String getImageResource() {
        return mImageResource;
    }

    public int getZimmerAnzahl() {
        return mZimmerAnzahl;
    }

    public float getPreis() {
        return mPreis;
    }

    public float getMaklerProvision() {
        return mMaklerProvision;
    }

    public boolean isKauf() {
        return mAngebot;
    }

    public String getAdresse() {
        return mAdresse;
    }

    public int getmID() { return mID; }

    public boolean ismAngebot() {
        return mAngebot;
    }

    public boolean isFavorit() {
        return istFavorit;
    }

    public float getFlaeche() {
        return mflaeche;
    }


}

