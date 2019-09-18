package com.example.hw1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Geocoder code;
    List<Address> loc;

    @Nullable


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, null);



        MapView mapview = (MapView) rootView.findViewById(R.id.mapView);
        if(mapview != null) {
            mapview.onCreate(null);
            mapview.onResume();
            mapview.getMapAsync(this);
        }
        System.out.println("in onCreateView");
        return rootView;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        System.out.println("It gets here");

    }

    public List<Address> updateMap(String s) {
        System.out.println("in update map");
        mMap.clear();
        code = new Geocoder(this.getContext());
        try {
            loc = code.getFromLocationName(s, 1);
        } catch(Exception E) {
            //do nothing
        }

        LatLng location = new LatLng(loc.get(0).getLatitude(),loc.get(0).getLongitude());
        mMap.addMarker(new MarkerOptions().position(location).title(s));
        return loc;
    }

}
