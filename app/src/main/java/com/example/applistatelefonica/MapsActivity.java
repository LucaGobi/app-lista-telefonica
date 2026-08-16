package com.example.applistatelefonica;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.applistatelefonica.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        i = getIntent();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng marker = getLatLngFromLocationName(MapsActivity.this, i.getExtras().getString("endereco"));
        mMap.addMarker(new MarkerOptions().position(marker).title(i.getExtras().getString("endereco")));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
        mMap.setMinZoomPreference(10);
    }

    public LatLng getLatLngFromLocationName(Context context, String endereco){
        Geocoder geocoder = new Geocoder(context);
        List<Address> lista;
        LatLng adress = null;
        try {
            lista = geocoder.getFromLocationName(endereco, 1);
            if(lista!=null){
                 adress = new LatLng(lista.get(0).getLatitude(), lista.get(0).getLongitude());
            }else{
                Toast.makeText(context, "Endereço inexistente", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Toast.makeText(context, "A rede esta indisponivel ou algum outro problema I/O ocorreu", Toast.LENGTH_SHORT).show();
        }
        return adress;
    }

}