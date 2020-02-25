package com.example.is2.ui.aroundme;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.is2.R;
import com.example.is2.RVAdapter.RVAdapterSportEvent;
import com.example.is2.javaclass.SportEvent;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class AroundmeFragment extends Fragment implements OnMapReadyCallback
{

    private GoogleMap mMap;
    View mView;
    SupportMapFragment mapFragment;
    String TAG="MapFragment";
    boolean permissionAccessFineLocationApproved;
    FusedLocationProviderClient mFusedLocationProviderClient;
    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Construct a GeoDataClient.
        //mGeoDataClient = Places.getGeoDataClient(this, null);

        // Construct a PlaceDetectionClient.
        //mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        mView = inflater.inflate(R.layout.fragment_aroundme, container, false);
        mapFragment=(SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);

        if(mapFragment==null){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);

        permissionAccessFineLocationApproved =
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED;

        if (permissionAccessFineLocationApproved) {
            System.out.println("Ho gia il permesso per coarse location");

        } else {
            // App doesn't have access to the device's location at all. Make full request
            // for permission.
            System.out.println("Non ho il permesso per fine location");
            requestPermissions(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        return mView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions,@NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        System.out.println("onRequestPermissionsResult");
        permissionAccessFineLocationApproved = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                //System.out.println("DEBUG1");
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0) {//System.out.println("DEBUG2");
                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED){//System.out.println("DEBUG3");
                        permissionAccessFineLocationApproved = true;
                        mMap.setMyLocationEnabled(true);
                        mFusedLocationProviderClient.getLastLocation()
                                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                                    @Override
                                    public void onSuccess(Location location) {
                                        // Got last known location. In some rare situations this can be null.
                                        if (location != null) {
                                            // Logic to handle location object
                                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),12));
                                        }
                                    }
                                });
                    }
                }
                break;
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(permissionAccessFineLocationApproved==true){
            mMap.setMyLocationEnabled(true);
            mFusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),12));
                            }
                        }
                    });
        }

        FirebaseDatabase.getInstance().getReference("SportEvents").addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                //System.out.println("numero figli: "+dataSnapshot.getChildrenCount());
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                        //System.out.println("DS"+ds);
                        SportEvent sportevent = ds.getValue(SportEvent.class);
                        if(sportevent.getCoordinate().get(0)!=null && sportevent.getCoordinate().get(1)!=null) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(sportevent.getCoordinate().get(0), sportevent.getCoordinate().get(1))).title(sportevent.getEventname()));
                        }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });
    }

}