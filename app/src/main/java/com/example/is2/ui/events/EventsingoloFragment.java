package com.example.is2.ui.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.is2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

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

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String iduser = user.getUid();

        final String idevento=getArguments().getString("idevento");
        System.out.println("id evento"+idevento);
        //System.out.println(nome +" "+luogo+" "+ora+" "+prezzo+" "+maxpartecipanti+" "+currentpartecipanti+" "+sport+" "+proprietario+" "+data);
        System.out.println("id evento"+idevento+" id user"+iduser);


        mDatabase = FirebaseDatabase.getInstance().getReference("SportEvents");

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

        //GESTIRE BOTTONE....
        /*
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
        */
    }

}
