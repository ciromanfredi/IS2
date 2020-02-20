package com.example.is2;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    //Map<String,Map<String, Object>> EventSport=new HashMap<String, Map<String, Object>>();
    //Map<String,Map<String, Object>> user=new HashMap<String, Map<String, Object>>();


   ListView listView;
    FirebaseDatabase database;
    DatabaseReference sportEvents;
    ArrayList<SportEvent> list;
    ArrayAdapter<SportEvent>  adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //ListView = (ListView) findViewById(R.id.ListView);
        final RecyclerView rv = (RecyclerView)findViewById(R.id.rv);



        LinearLayoutManager llm = new LinearLayoutManager(this);
        GridLayoutManager glm= new GridLayoutManager(this,10);

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


    @Override
    public void onBackPressed() {
        //super.onBackPressed(); //commented this line in order to disable back press
        //Write your code here
        //Toast.makeText(getApplicationContext(), "Back press disabled!", Toast.LENGTH_SHORT).show();
    }

}
