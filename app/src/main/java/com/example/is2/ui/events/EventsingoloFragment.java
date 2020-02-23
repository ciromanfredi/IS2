package com.example.is2.ui.events;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.is2.R;
import com.example.is2.RVAdapter.RVAdapterUser;
import com.example.is2.javaclass.SportEvent;
import com.example.is2.javaclass.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

public class EventsingoloFragment extends Fragment {

    DatabaseReference mDatabase;
    FirebaseStorage storage;

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

    public void rendering(SportEvent sportevent, final String iduser, final String idevento){
        System.out.println(sportevent);
        String nome=sportevent.getEventname();
        String luogo=sportevent.getEventplace();
        String sport=sportevent.getEventsport();
        String data=sportevent.getEventdate();
        String ora=sportevent.getEventhour();
        String prezzo=sportevent.getEventprice();
        String numbermaxpartecipanti=sportevent.getEventplayersnumber();
        final ArrayList<String> partecipanti=sportevent.getEventnumberofplayers();
        int numbercurrentpartecipanti=sportevent.getnumberofpartecipanticorrenti();

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
        sportevent_numeropartecipanti.setText(numbercurrentpartecipanti + "/" + numbermaxpartecipanti);

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
        System.out.println("non va"+partecipantiObj.size());
        storage = FirebaseStorage.getInstance();
        final StorageReference storageRef = storage.getReferenceFromUrl("gs://is-2-268215.appspot.com").child("imagepdf.png");

        try {
            final File localFile = File.createTempFile("images", "png");
            database.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    System.out.println(dataSnapshot+"dsdd"+dataSnapshot.getChildrenCount());
                    for (final DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                        System.out.println("hey tu");
                        System.out.println(singleSnapshot);
                        if(partecipanti.contains(singleSnapshot.getKey())){
                                storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        System.out.println("Tutto con successo");
                                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                        User user = singleSnapshot.getValue(User.class);
                                        user.setBitmap(bitmap);
                                        partecipantiObj.add(user);
                                        System.out.println("sonoquiiiiiii " + partecipantiObj);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        System.out.println("OnFailureListener Debug");
                                    }
                                });
                        }
                    }
                    System.out.println("aggiorno visuale di "+partecipantiObj.size());
                    listUserEvent(partecipantiObj);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("EventsingoloFragment", "onCancelled", databaseError.toException());
                }
            });

            final boolean eventfull=(numbercurrentpartecipanti==Integer.parseInt(numbermaxpartecipanti));

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
        }catch (Exception e){
            System.out.println("Problemi con file...");
        }


    }

    public void getDBData(final String iduser, final String idevento){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("SportEvents");
        DatabaseReference ref = database.child(idevento);

        Query query = ref;

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SportEvent sportevent = dataSnapshot.getValue(SportEvent.class);
                rendering(sportevent,iduser,idevento);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("EventsingoloFragment", "onCancelled", databaseError.toException());
            }
        });
    }

    public void listUserEvent(ArrayList<User> userList){

        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerviewusers);
        RVAdapterUser rvadapteruser=new RVAdapterUser(userList);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(rvadapteruser);
    }
}
