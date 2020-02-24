
package com.example.is2.ui.events;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.is2.R;
import com.example.is2.RVAdapter;
import com.example.is2.javaclass.SportEvent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
    ArrayAdapter<SportEvent> adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_events, container, false);
        return root;

    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        final RecyclerView rv = (RecyclerView)getActivity().findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        //GridLayoutManager glm= new GridLayoutManager(this,10);

        rv.setLayoutManager(llm);

        database = FirebaseDatabase.getInstance();
        sportEvents = database.getReference("SportEvents");

        list = new ArrayList<>();

        dati = new ArrayList<>();

        sportEvents.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                System.out.println("numero figli: "+dataSnapshot.getChildrenCount());
                //Map<String, User> dati=dataSnapshot.getValue(Map<String.class,User.class>));
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    System.out.println("DS"+ds);
                    if (dataSnapshot.hasChildren()) {
                        System.out.println("Sono dentro figlio: " + ds.getValue());
                        String key=ds.getKey();
                        SportEvent sportevent = ds.getValue(SportEvent.class);
                        dati.add(key);
                        list.add(sportevent);
                        System.out.println(sportevent.getEventname());
                        System.out.println(sportevent);
                    }
                }
                RVAdapter adapter = new RVAdapter(list);
//                adapter.setRVAdapterfa(getActivity());
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });

    }
}