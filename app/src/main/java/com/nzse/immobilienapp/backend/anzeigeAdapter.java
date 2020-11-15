package com.nzse.immobilienapp.backend;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.nzse.immobilienapp.AnzeigeFragment;
import com.nzse.immobilienapp.EditorFragment;
import com.nzse.immobilienapp.GalleryActivity;
import com.nzse.immobilienapp.MainActivity;
import com.nzse.immobilienapp.R;
import com.nzse.immobilienapp.navigationMakler;


public class anzeigeAdapter extends RecyclerView.Adapter<anzeigeAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private static int pos = 0 ;

    public ArrayList<Anzeige> getmAnzeigeList() {
        return mAnzeigeList;
    }

    public void setmAnzeigeList(ArrayList<Anzeige> mAnzeigeList) {
        this.mAnzeigeList = mAnzeigeList;
    }

    private ArrayList<Anzeige> mAnzeigeList;

    private Context mContext;

    public anzeigeAdapter(Context context, ArrayList<Anzeige> AnzeigeList) {
        mAnzeigeList = AnzeigeList;
        mContext = context;

    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.anzeige_item_makler, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    public void onBindViewHolder(final ViewHolder holder, final int position)  {
        Log.d(TAG, "onBindViewHolder: called.");

           Anzeige currentAnzeige = mAnzeigeList.get(position);

        holder.mImageView.setImageURI(Uri.fromFile(new File(currentAnzeige.getImageResource())));
        holder.mAdresse.setText(currentAnzeige.getAdresse());
        holder.mPreis.setText(String.valueOf(currentAnzeige.getPreis()));
        holder.mAnzahlZimmer.setText(String.valueOf(currentAnzeige.getZimmerAnzahl()));
        holder.mFlaeche.setText(String.valueOf(currentAnzeige.getFlaeche()));
        holder.mMakler.setText(String.valueOf(currentAnzeige.getMaklerProvision()));

        if (currentAnzeige.isKauf())
             holder.mAngebot.setText(R.string.kauf);
        else
            holder.mAngebot.setText(R.string.miete);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(mContext , GalleryActivity.class);
                int pos = holder.getAdapterPosition();
                intent.putExtra("AnzeigeIndex", pos);
                mContext.startActivity(intent);

            }
        });






        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                alertDialog.setTitle(R.string.delete);
                alertDialog.setMessage(R.string.areYouSure);
                alertDialog.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeItem(holder);
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
                return false;
            }

        });





    }

    public boolean removeItem (final ViewHolder holder){

        mAnzeigeList.remove(holder.getAdapterPosition());
        notifyDataSetChanged();
        MainActivity.bcd.anzeigeList = mAnzeigeList;
        try {
            MainActivity.bcd.saveFile(MainActivity.bcd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int getItemCount() {
        if(mAnzeigeList!=null)
        return mAnzeigeList.size();

        else return 0;
    }


     class ViewHolder extends RecyclerView.ViewHolder{

         ImageView mImageView;
         TextView mAdresse;
         TextView mPreis;
         TextView mAngebot;
         TextView mAnzahlZimmer;
         TextView mFlaeche;
         TextView mMakler;
        LinearLayout parentLayout;

         ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mAdresse = itemView.findViewById(R.id.adresse);
            mPreis = itemView.findViewById(R.id.preis_value);
            mAngebot = itemView.findViewById(R.id.angebot_value);
            mAnzahlZimmer = itemView.findViewById(R.id.anzahlZimmer_value);
            mFlaeche = itemView.findViewById(R.id.flaeche_value);
            mMakler = itemView.findViewById(R.id.maklerP_value);
            parentLayout = itemView.findViewById(R.id.item_layout_makler);


        }

    }



}



