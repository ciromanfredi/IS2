package com.example.is2.ui.events;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.is2.AddEventActivity;
import com.example.is2.FilterEvents;
import com.example.is2.R;
import com.example.is2.RVAdapter.RVAdapterSportEvent;
import com.example.is2.javaclass.SportEvent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class EventsFragment extends Fragment {

    private static final String TAG = "EventsFragment";
    public static ArrayList<String> dati = null;

    public static ArrayList<String> getDati() {
        return dati;
    }

    android.widget.ListView ListView;
    FirebaseDatabase database;
    DatabaseReference sportEvents;
    ArrayList<SportEvent> list;
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
        if (id == R.id.create_event) {
            Intent intent = new Intent(getActivity().getApplicationContext(), AddEventActivity.class);
            startActivity(intent);
        }
        if (id == R.id.filtra) {
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

        //System.out.println("PREFERENZE FILTRI : " + preferenze);

        final RecyclerView rv = (RecyclerView) getActivity().findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        //GridLayoutManager glm= new GridLayoutManager(this,10);

        rv.setLayoutManager(llm);

        database = FirebaseDatabase.getInstance();
        sportEvents = database.getReference("SportEvents");

        list = new ArrayList<>();

        sportEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //System.out.println("numero figli: " + dataSnapshot.getChildrenCount());
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //System.out.println("DS"+ds);
                    if (dataSnapshot.hasChildren()) {
                        //System.out.println("Sono dentro figlio: " + ds.getValue());
                        String key = ds.getKey();
                        Map<String, SportEvent> ListaEventi = (Map<String, SportEvent>) ds.getValue();
                        //System.out.println("LISTA EVENTI" + ListaEventi);
                        SportEvent sportevent = ds.getValue(SportEvent.class);
                        sportevent.setKey(key);
                        if ((getActivity().getIntent().getStringArrayListExtra("preferenze") != null)) {
                            preferenze = getActivity().getIntent().getStringArrayListExtra("preferenze");
                            if (!preferenze.isEmpty()) {
                                for (int i = 0; i < preferenze.size(); i++) {
                                    if (ListaEventi.containsValue(preferenze.get(i))) {
                                        list.add(sportevent);
                                    }
                                }
                            } else {
                                list.add(sportevent);
                            }
                        } else {
                            list.add(sportevent);
                        }
                    }
                }
                try {
                    ordinaData(list);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                RVAdapterSportEvent adapter = new RVAdapterSportEvent(list);
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

    static final Comparator<SportEvent> byDate = new Comparator<SportEvent>() {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        @Override
        public int compare(SportEvent ord1, SportEvent ord2) {
            String ev1 = ord1.getEventdate();
            String ev2 = ord2.getEventdate();
            Date d1 = null;
            Date d2 = null;
            try {
                d1 = format.parse(ev1);
                d2 = format.parse(ev2);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            //return (d1.getTime() > d2.getTime() ? -1 : 1);     //descending
            return (d1.getTime() > d2.getTime() ? 1 : -1);     //ascending
        }
    };

    public void ordinaData(ArrayList<SportEvent> listaeventi) throws ParseException {
        Collections.sort(listaeventi,byDate);
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        //System.out.println("Data current: "+currentDate+" Lista size: "+listaeventi.size());
        Date current = format.parse(currentDate);
        ArrayList<SportEvent> listaeventiok=new ArrayList<SportEvent>();
        for(SportEvent s : listaeventi){
            String data_lista = s.getEventdate();
            Date data_cast = format.parse(data_lista);
            System.out.println("Data_cast: "+data_cast.getTime()+" Currenttime:"+current.getTime());
            if( data_cast.getTime() >= current.getTime() )
                listaeventiok.add(s);
        }

        listaeventi.clear();
        listaeventi.addAll(listaeventiok);

    }

}