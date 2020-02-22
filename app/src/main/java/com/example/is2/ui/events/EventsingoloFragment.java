package com.example.is2.ui.events;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.is2.ListUserAdapter;
import com.example.is2.R;
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

public class EventsingoloFragment extends Fragment {

    DatabaseReference mDatabase;

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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String iduser = user.getUid();

        final String idevento=getArguments().getString("idevento");
        System.out.println("id evento"+idevento+" id user"+iduser);
        mDatabase = FirebaseDatabase.getInstance().getReference("SportEvents");
        getDBData(iduser,idevento);

    }

    public void rendering(String nome, String luogo, String sport, String data, String ora, String prezzo, String maxpartecipanti,final ArrayList<String> partecipanti,final String iduser, final String idevento){

        int currentpartecipanti=partecipanti.size();
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

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users");
        final ArrayList<User> partecipantiObj=new ArrayList<>();
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot);
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    partecipantiObj.add(singleSnapshot.getValue(User.class));
                    listUserEvent(partecipantiObj);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("EventsingoloFragment", "onCancelled", databaseError.toException());
            }
        });

        final boolean eventfull=(currentpartecipanti==Integer.parseInt(maxpartecipanti));

        final boolean partecipa;
        if (partecipanti.contains(iduser))
            partecipa = true;
        else
            partecipa = false;

        final Button button = (Button) getActivity().findViewById(R.id.button);
        if (partecipa)
            button.setText("Abbandona");
        else
            if(!eventfull)
                button.setText("Partecipa");
            else {
                button.setText("Evento Full!");
                return;
                }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!partecipa){
                    if(!eventfull){
                        button.setText("Abbandona");
                        partecipanti.add(iduser);
                        mDatabase.child(idevento).child("eventnumberofplayers").setValue(partecipanti);
                        getDBData(iduser,idevento);
                    }
                }
                else{
                    button.setText("Partecipa");
                    partecipanti.remove(iduser);
                    mDatabase.child(idevento).child("eventnumberofplayers").setValue(partecipanti);
                    getDBData(iduser,idevento);
                }
            }
        });

    }

    public void getDBData(final String iduser, final String idevento){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("SportEvents");
        DatabaseReference ref = database.child(idevento);

        Query query = ref;

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SportEvent sportevent = dataSnapshot.getValue(SportEvent.class);
                rendering(sportevent.getEventname(),sportevent.getEventplace(),sportevent.getEventsport(),sportevent.getEventdate(),sportevent.getEventhour(),sportevent.getEventprice(),sportevent.getEventplayersnumber(),sportevent.getEventnumberofplayers(),iduser,idevento);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("EventsingoloFragment", "onCancelled", databaseError.toException());
            }
        });
    }

    public void listUserEvent(ArrayList<User> userList){
        //ListView myListView = new ListView(getActivity().getApplicationContext());
        ListView myListView = getActivity().findViewById(R.id.listviewexp);
        ListUserAdapter adapter = new ListUserAdapter(getActivity().getApplicationContext(), R.layout.user_list_element, userList);
        myListView.setAdapter(adapter);
    }
}
