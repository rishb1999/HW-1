package com.example.hw1;

import android.location.Address;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ResponseDelivery;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.google.android.gms.common.api.Response;
import com.android.volley.Response;

import java.util.List;

public class WeatherFragment extends Fragment  {
    private RequestQueue queue;
    private View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(this.getContext());
        System.out.println("Hello from Weather");
        v = inflater.inflate(R.layout.fragment_weather, null);
        return v;
    }

    public void getWeather(List<Address> coord) {
        final TextView temp = (TextView)v.findViewById(R.id.temperature_text);
        final TextView humid = (TextView)v.findViewById(R.id.humidity_text);
        final TextView wind = (TextView)v.findViewById(R.id.wind_text);
        final TextView pre = (TextView)v.findViewById(R.id.precipitation_text);
        String lat = Double.toString(coord.get(0).getLatitude());
        String longi = Double.toString(coord.get(0).getLongitude());
        String url ="https://api.darksky.net/forecast/01b25a9e57421f565abfa1e14e759ab1/" + lat + "," + longi;

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        System.out.println("Response is: "+ response.substring(0,500));
                        String [] json = response.split(",");
                        for(String s: json) {
                            System.out.println(s);
                            if(s.length() > 14) {
                                if(s.substring(0,13).equals("\"temperature\"")) {
                                    String[] test = s.split(":");
                                    temp.setText("Temperature: {test[1]}");
                                    System.out.println("IT GETS TEMP");
                                }
                            }
                            if(s.length() > 20) {
                                if (s.substring(0, 19).equals("\"precipProbability\"")) {
                                    String[] test = s.split(":");
                                    pre.setText("Precipitation =  {test[1]}");
                                    System.out.println("IT GETS PRECIP");
                                }
                            }
                            if(s.length() > 13) {
                                if(s.substring(0, 11).equals("\"windSpeed\"")) {
                                    String[] test = s.split(":");
                                    wind.setText("Wind Speed = {test[1]}");
                                    System.out.println("IT GETS WIND");
                                }
                            }
                            if(s.length() > 12) {
                                if(s.substring(0,10).equals("\"humidity\"")) {
                                    String[] test = s.split(":");
                                    humid.setText("Humidity = {test[1]} %");
                                    System.out.println("IT GETS HUMID");
                                }
                            }

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
