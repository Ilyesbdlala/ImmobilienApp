package com.nzse.immobilienapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.nzse.immobilienapp.MainActivity;
import com.nzse.immobilienapp.backend.Anzeige;
import com.nzse.immobilienapp.backend.backendContainer;
import com.nzse.immobilienapp.navigationMakler;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nzse.immobilienapp.backend.anzeigeAdapter;

import java.util.ArrayList;

public class AnzeigeFragment extends Fragment {
    public static anzeigeAdapter mAdapter ;
    static boolean onlyFavorit = false;

    @Nullable
    @Override


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anzeige, container, false);
        return  view ;

    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        final Switch favoritSwitch = view.findViewById(R.id.nurFavoritButton);
        this.onlyFavorit = favoritSwitch.isChecked();



        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        anzeigeAdapter adapter;
        if (onlyFavorit)
             adapter = new anzeigeAdapter(this.getContext(),getFavorites(MainActivity.bcd.getAnzeigeList()));
        else
             adapter = new anzeigeAdapter(this.getContext(),MainActivity.bcd.getAnzeigeList());

         mAdapter = adapter;
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        favoritSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onlyFavorit = favoritSwitch.isChecked();

                if (onlyFavorit)
                    recyclerView.setAdapter(new anzeigeAdapter(getContext(),getFavorites(MainActivity.bcd.getAnzeigeList())));

                else
                    recyclerView.setAdapter(new anzeigeAdapter(getContext(),MainActivity.bcd.getAnzeigeList()));
            }
        });
    }



    public ArrayList<Anzeige> getFavorites (ArrayList<Anzeige> AnzeigeList){
        ArrayList<Anzeige> Favorite = new ArrayList<>();

        for (Anzeige a  : AnzeigeList )
            if (a.isFavorit())
                Favorite.add(a);

        return Favorite;

    }
}
