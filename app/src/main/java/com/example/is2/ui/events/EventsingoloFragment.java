package com.example.is2.ui.events;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.is2.R;
import com.example.is2.RVAdapter.RVAdapterUser;
import com.example.is2.javaclass.SportEvent;
import com.example.is2.javaclass.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class EventsingoloFragment extends Fragment  implements OnMapReadyCallback {

    DatabaseReference databaseSportEvents;
    DatabaseReference databaseUsers;
    StorageReference storageRef;
    private GoogleMap mMap;
    View mView;
    SupportMapFragment mapFragment;
    FusedLocationProviderClient mFusedLocationProviderClient;
    SportEvent sportevent;

    public EventsingoloFragment() {
        // Required empty public constructor
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // this.setHasOptionsMenu(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println("on options item");
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //@Override
    public void onBackPressed() {
        System.out.println("on back pressed");
        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_navigation_eventsingolo_to_navigation_events);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sport_event, container, false);
        System.out.println("Debug onCreateView");

        mapFragment=(SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.mapsingleevent);
        mapFragment.getMapAsync(this);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        return rootView;
    }

    @Override
    public void onViewCreated( View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String iduser = user.getUid();

        final String idevento=getArguments().getString("idevento");
        System.out.println("id evento"+idevento+" id user"+iduser);

        databaseSportEvents = FirebaseDatabase.getInstance().getReference("SportEvents");
        storageRef = FirebaseStorage.getInstance().getReference();

        getDBData(iduser,idevento);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setHasOptionsMenu(true);

       /*
        OnBackPressedCallback callback = new OnBackPressedCallback(true ) {

            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                System.out.println("BackPressed");
                //   Intent intent = new Intent(getActivity().onBackPressed(), EventsFragment.class);
               // startActivity(intent);
                Navigation.findNavController(view).navigate(R.id.action_navigation_eventsingolo_to_navigation_events);

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        */


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }

    public void rendering(SportEvent sportevent, final String iduser, final String idevento){
        System.out.println("Inizio rendering oggetto... "+sportevent);
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

        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");
        final ArrayList<User> partecipantiObj=new ArrayList<>();
        System.out.println("Lista size..."+partecipantiObj.size());
        listUserEvent(partecipantiObj);
            databaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //System.out.println("Datasnapshot: "+dataSnapshot);
                    //System.out.println("Num Children: "+dataSnapshot.getChildrenCount());
                    int i=0;

                    for (final DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                        System.out.println("i: "+i);
                        //System.out.println(singleSnapshot);
                        if (partecipanti.contains(singleSnapshot.getKey())) {
                            final User user=singleSnapshot.getValue(User.class);
                            user.setUid(singleSnapshot.getKey());
                            System.out.println("user->nome: "+user.getNome());
                            System.out.println("Inizio a scaricare la foto per l'utente: "+user.getEmail());

                            try{

                                System.out.println("Scarico storage da: "+singleSnapshot.getKey());
                                storageRef.child("uploads").child(singleSnapshot.getKey()+".jpeg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        System.out.println("Uri: "+uri);
                                        user.setUrimmagine(uri);
                                        partecipantiObj.add(user);
                                        if(partecipantiObj.size()==partecipanti.size()) {
                                            System.out.println("Sottometto lista");
                                            listUserEvent(partecipantiObj);
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        System.out.println("debug here");
                                    }
                                });

                            }catch(Exception e){
                                e.printStackTrace();
                            }

                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("EventsingoloFragment", "onCancelled", databaseError.toException());
                }
            });


            final boolean eventfull=(numbercurrentpartecipanti==Integer.parseInt(numbermaxpartecipanti));

            final boolean partecipa;
        partecipa = partecipanti.contains(iduser);

            final Button button = getActivity().findViewById(R.id.button);
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
                            databaseSportEvents.child(idevento).child("eventnumberofplayers").setValue(partecipanti);
                            getDBData(iduser,idevento);
                        }
                    }
                    else{
                        button.setText("Partecipa");
                        partecipanti.remove(iduser);
                        databaseSportEvents.child(idevento).child("eventnumberofplayers").setValue(partecipanti);
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
                sportevent = dataSnapshot.getValue(SportEvent.class);
                if(sportevent.getCoordinate().get(0)!=null && sportevent.getCoordinate().get(1)!=null){
                    final LatLng positionMarker = new LatLng(sportevent.getCoordinate().get(0),sportevent.getCoordinate().get(1));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(positionMarker,12));
                    mMap.addMarker(new MarkerOptions().position(positionMarker).title(sportevent.getEventname()));
                }
                else
                {
                    if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                        mFusedLocationProviderClient.getLastLocation()
                                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                                    @Override
                                    public void onSuccess(Location location) {
                                        // Got last known location. In some rare situations this can be null.
                                        if (location != null) {
                                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 12));
                                        }
                                    }
                                });
                    }
                }
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
        RecyclerView mRecyclerView = getActivity().findViewById(R.id.recyclerviewusers);
        RVAdapterUser rvadapteruser=new RVAdapterUser(userList);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(rvadapteruser);
    }

    //@Override
   // public void OnBackPressed() {

      //  Navigation.navigate().findNavController(R.id.action_navigation_eventsingolo_to_navigation_events);
    //}
}