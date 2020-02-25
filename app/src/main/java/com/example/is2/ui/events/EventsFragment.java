package com.example.is2.ui.events;

import com.example.is2.AddEventActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.is2.FilterEvents;
import com.example.is2.R;
import com.example.is2.RVAdapter.RVAdapterSportEvent;
import com.example.is2.javaclass.SportEvent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class EventsFragment extends Fragment {

    private static final String TAG = "EventsFragment";
    public static ArrayList<String> dati=null;

    public static ArrayList<String> getDati()
    {
        return dati;
    }

    android.widget.ListView ListView;
    FirebaseDatabase database;
    DatabaseReference sportEvents;
    ArrayList<SportEvent> list;
    ArrayList<SportEvent> listaFiltrata;
    ArrayAdapter<SportEvent> adapter;
    ArrayList<String> preferenze;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.event_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.create_event){
            Intent intent = new Intent(getActivity().getApplicationContext(), AddEventActivity.class);
            startActivity(intent);
        }
        if (id == R.id.filtra){
            Intent intent = new Intent(getActivity().getApplicationContext(), FilterEvents.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_events, container, false);
        return root;

    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferenze = new ArrayList<>();


        System.out.println("PREFERENZE FILTRI : "+preferenze);


        final RecyclerView rv = (RecyclerView)getActivity().findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        //GridLayoutManager glm= new GridLayoutManager(this,10);

        rv.setLayoutManager(llm);

        database = FirebaseDatabase.getInstance();
        sportEvents = database.getReference("SportEvents");

        list = new ArrayList<>();

        dati = new ArrayList<>();

        listaFiltrata = new ArrayList<>();

        sportEvents.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                System.out.println("numero figli: "+dataSnapshot.getChildrenCount());
                //Map<String, User> dati=dataSnapshot.getValue(Map<String.class,User.class>));
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    //System.out.println("DS"+ds);
                    if (dataSnapshot.hasChildren()) {
                        //System.out.println("Sono dentro figlio: " + ds.getValue());
                        String key=ds.getKey();
                        Map<String,SportEvent> ListaEventi = (Map<String, SportEvent>) ds.getValue();
                        System.out.println("LISTA EVENTI"+ListaEventi);
                        SportEvent sportevent = ds.getValue(SportEvent.class);
                        if((getActivity().getIntent().getStringArrayListExtra("preferenze")!=null)) {
                            preferenze=getActivity().getIntent().getStringArrayListExtra("preferenze");
                            if(!preferenze.isEmpty()) {
                                for (int i = 0; i < preferenze.size(); i++) {
                                    if (ListaEventi.containsValue(preferenze.get(i))) {
                                        dati.add(key);
                                        list.add(sportevent);
                                    }
                                }
                            }else {
                                dati.add(key);
                                list.add(sportevent);
                            }
                        }else {
                            dati.add(key);
                            list.add(sportevent);
                        }
                    }

                }
                RVAdapterSportEvent adapter = new RVAdapterSportEvent(list);
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });

    }
}