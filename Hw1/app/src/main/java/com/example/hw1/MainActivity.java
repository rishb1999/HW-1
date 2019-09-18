package com.example.hw1;

import android.location.Address;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {


    private String query;
    private SearchView mySearch;
    private MapFragment mf;
    private WeatherFragment wf;
    private List<Address> loc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(this);
        mf = new MapFragment();
        wf = new WeatherFragment();
        loadFragment(wf);
        loadFragment(mf);
        mySearch = (SearchView)findViewById(R.id.searchBar);
        mySearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                query = s;
                loc = mf.updateMap(query);
                wf.getWeather(loc);
                loadFragment(mf);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }
    private boolean loadFragment(Fragment fragment) {
        if(fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch(menuItem.getItemId()) {
            case R.id.navigation_map:
                fragment = mf;
                break;
            case R.id.navigation_weather:
                fragment = wf;
                break;

        }
        return loadFragment(fragment);
    }

    public String getQuery(){
        return query;
    }

}
