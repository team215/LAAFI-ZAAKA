package com.example.laafizaaka;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.laafizaaka.Fragment.HopitalFragment;
import com.example.laafizaaka.Fragment.NumeroVertFragment;
import com.example.laafizaaka.Fragment.PharmacieFragment;
import com.example.laafizaaka.databinding.ActivityMapsBinding;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest ;
    private Location lastLocation;
    private Marker currentUserLocationMarker;
    private final static int request_user_location_code= 99;

    private ImageView PharBtn, HopBtn, NumVertBtn, PharBtnClick, HopBtnClick, NumVertBtnClick;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());**/
        setContentView(R.layout.activity_maps);

        PharBtn= findViewById(R.id.id_pharmacie);
        PharBtnClick= findViewById(R.id.id_pharmacie_clique);
        HopBtn= findViewById(R.id.id_hopital);
        HopBtnClick= findViewById(R.id.id_hopital_click);
        NumVertBtn= findViewById(R.id.id_numero_vert);
        NumVertBtnClick= findViewById(R.id.id_numero_vert_click);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            CheckUserLocationPermission();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Appui sur le bouton pharmacie
        PharBtn.setOnClickListener(v -> {
            PharBtnClick.setVisibility(View.VISIBLE);
            PharBtn.setVisibility(View.GONE);

            HopBtnClick.setVisibility(View.GONE);
            HopBtn.setVisibility(View.VISIBLE);

            NumVertBtnClick.setVisibility(View.GONE);
            NumVertBtn.setVisibility(View.VISIBLE);

            replaceFragment(new PharmacieFragment());
        });

        //Appui sur le bouton hopital
        HopBtn.setOnClickListener(v -> {
            HopBtnClick.setVisibility(View.VISIBLE);
            HopBtn.setVisibility(View.GONE);

            PharBtnClick.setVisibility(View.GONE);
            PharBtn.setVisibility(View.VISIBLE);

            NumVertBtnClick.setVisibility(View.GONE);
            NumVertBtn.setVisibility(View.VISIBLE);

            replaceFragment(new HopitalFragment());
        });

        //Appui sur le bouton numéro vert
        NumVertBtn.setOnClickListener(v -> {
            NumVertBtnClick.setVisibility(View.VISIBLE);
            NumVertBtn.setVisibility(View.GONE);

            HopBtnClick.setVisibility(View.GONE);
            HopBtn.setVisibility(View.VISIBLE);

            PharBtnClick.setVisibility(View.GONE);
            PharBtn.setVisibility(View.VISIBLE);

            replaceFragment(new NumeroVertFragment());
        });

    }

    //Méthode qui permet de remplacer les fragments quand on appui sur les différents boutons
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
        {

            buildGoogleapiClient();

            mMap.setMyLocationEnabled(true);
        }

    }

    public boolean CheckUserLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        request_user_location_code);
            }else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        request_user_location_code);
            }
            return false;
        }else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode){
            case request_user_location_code:
                if (grantResults.length >0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==
                    PackageManager.PERMISSION_GRANTED){
                        if (googleApiClient== null){
                            buildGoogleapiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }else {
                    Toast.makeText(this, "Permission refusée...", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    protected synchronized void buildGoogleapiClient(){

        googleApiClient= new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

        lastLocation= location;
        if (currentUserLocationMarker!= null){
            currentUserLocationMarker.remove();
        }
        LatLng latLng= new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions= new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Votre  position actuelle");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

        currentUserLocationMarker= mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(12));

        if (googleApiClient!= null){
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest= new LocationRequest();
        locationRequest.setInterval(1100);
        locationRequest.setFastestInterval(1100);
        locationRequest.setPriority(locationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==
                PackageManager.PERMISSION_GRANTED){

            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,
                    locationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}