package com.example.is2.ui.events;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.is2.R;
import com.example.is2.RVAdapter;
import com.example.is2.javaclass.SportEvent;
import com.example.is2.javaclass.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.google.common.base.Predicates.equalTo;

public class EventsingoloFragment extends Fragment {

    DatabaseReference mDatabase ;
    Button button;

    public EventsingoloFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_sport_event, container, false);
        System.out.println("Debug onCreateView");
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

/*
        String nome=getArguments().getString("nome");
        String luogo=getArguments().getString("luogo");
        String ora=getArguments().getString("ora");
        String prezzo=getArguments().getString("prezzo");
        String maxpartecipanti=getArguments().getString("maxpartecipanti");
        String currentpartecipanti=getArguments().getString("currentpartecipanti");
        String sport=getArguments().getString("sport");
        String proprietario=getArguments().getString("proprietario");
        String data=getArguments().getString("data");
        final ArrayList<String> partecipanti=getArguments().getStringArrayList("partecipanti");
        final String idevento=getArguments().getString("idevento");
        */

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String iduser = user.getUid();

        final String idevento=getArguments().getString("idevento");
        System.out.println("id evento"+idevento);
        getDBData(idevento,iduser);
        //System.out.println(nome +" "+luogo+" "+ora+" "+prezzo+" "+maxpartecipanti+" "+currentpartecipanti+" "+sport+" "+proprietario+" "+data);


        System.out.println("id evento"+idevento+" id user"+iduser);


        mDatabase = FirebaseDatabase.getInstance().getReference("SportEvents");

        final boolean partecipa;
        if (partecipanti.contains(iduser))
            partecipa = true;
        else
            partecipa = false;

        button = (Button) getActivity().findViewById(R.id.button);
        if (partecipa)
            button.setText("Abbandona");
        else
            button.setText("Partecipa");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!partecipa){
                    getDBData(idevento);
                getDBData(iduser);}
                else{
                    onViewCreated(view, savedInstanceState);
                    }

                mDatabase.child(idevento).child("eventnumberofplayers").setValue(partecipanti);
            }
        });

    }

    public void rendering(String nome, String luogo, String sport, String data, String ora, String prezzo, String currentpartecipanti, String maxpartecipanti, final ArrayList<String> partecipanti, final String iduser,final String idevento) {
        TextView sportevent_name = getActivity().findViewById(R.id.single_sportevent_name);
        sportevent_name.setText(nome);

        TextView sportevent_place = getActivity().findViewById(R.id.single_sportevent_place);
        sportevent_place.setText("Luogo: " + luogo);
        TextView sportevent_type = getActivity().findViewById(R.id.single_sportevent_type);
        sportevent_type.setText("Tipo: " + sport);

        TextView sportevent_date = getActivity().findViewById(R.id.single_sportevent_date);
        sportevent_date.setText(" Data: " + data);

        TextView sportevent_hour = getActivity().findViewById(R.id.single_sportevent_hour);
        sportevent_hour.setText(" Orario: " + ora);
        TextView sportevent_price = getActivity().findViewById(R.id.single_sportevent_price);
        sportevent_price.setText(" Costo: " + prezzo + "€");


        TextView sportevent_numeropartecipanti = getActivity().findViewById(R.id.single_sportevent_numeropartecipanti);
        if (currentpartecipanti != null)
            sportevent_numeropartecipanti.setText("0/" + maxpartecipanti);
        else
            sportevent_numeropartecipanti.setText(currentpartecipanti + "/" + maxpartecipanti);


        ImageView sportevent_immagine = getActivity().findViewById(R.id.single_sportevent_immagine);
        switch (sport) {
            case "Jogging":
                sportevent_immagine.setImageResource(R.drawable.ic_jogging);
                break;
            case "Tennis":
                sportevent_immagine.setImageResource(R.drawable.ic_tennis);
                break;
            case "Basket":
                sportevent_immagine.setImageResource(R.drawable.ic_basket);
                break;
            case "Soccer":
                sportevent_immagine.setImageResource(R.drawable.ic_soccer);
                break;
            case "Rugby":
                sportevent_immagine.setImageResource(R.drawable.ic_rugby);
                break;
            case "Volley":
                sportevent_immagine.setImageResource(R.drawable.ic_volley);
                break;
            default:
                break;
        }


    }

    public SportEvent getDBData(final String iduser, final String idevento){
        DatabaseReference sportEvents = FirebaseDatabase.getInstance().getReference("SportEvents").child(idevento);
        Query evento = sportEvents;

        evento.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
/*                System.out.println("dddddd"+dataSnapshot);
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){*/
                 SportEvent se = dataSnapshot.getValue(SportEvent.class);
                    System.out.println("oooook"+se.getEventsport());
                    rendering(se.getEventname(),se.getEventplace(),se.getEventsport(),se.getEventdate(),se.getEventhour(),se.getEventprice(),"current","max",se.getEventnumberofplayers(),iduser,idevento);
 //               }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return new SportEvent();
    }

}
