package com.example.is2.ui.events;
import com.example.is2.RVAdapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.is2.R;
import com.example.is2.RVAdapter;
import com.example.is2.SportEvent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventsFragment extends Fragment {

    private EventsViewModel eventsViewModel;
    private static final String TAG = "EventsFragment";
    //Map<String,Map<String, Object>> EventSport=new HashMap<String, Map<String, Object>>();
    //Map<String,Map<String, Object>> user=new HashMap<String, Map<String, Object>>();

    android.widget.ListView ListView;
    FirebaseDatabase database;
    DatabaseReference sportEvents;
    ArrayList<SportEvent> list;
    ArrayAdapter<SportEvent> adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        eventsViewModel =
                ViewModelProviders.of(this).get(EventsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_events, container, false);

        /*
        final TextView textView = root.findViewById(R.id.text_);
        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        */

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

        sportEvents.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                System.out.println("numero figli"+dataSnapshot.getChildrenCount());
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    //System.out.println("DS"+ds);
                    if (dataSnapshot.hasChildren()) {
                        System.out.println("Sono dentro figlio: " + ds.getValue());
                        SportEvent sportevent = ds.getValue(SportEvent.class);
                        System.out.println(sportevent.getEventname());
                        list.add(sportevent);
                        System.out.println(sportevent);
                    }
                }
                RVAdapter adapter = new RVAdapter(list);
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });

    }
}