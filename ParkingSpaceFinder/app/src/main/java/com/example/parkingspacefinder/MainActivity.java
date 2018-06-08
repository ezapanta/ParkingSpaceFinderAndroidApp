package com.example.parkingspacefinder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Garage> garageList = new ArrayList<>();
    private RecyclerView recyclerView;
    private GarageAdapter mAdapter;
    Button startSearch;
    public TextView garageResults;
    public SearchView searchQuery;

    RequestQueue rq;

    String name;
    String city;
    String description;
    int spaces;
    int id;
    String detailsURL = "http://75.187.77.37:8080/16";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       searchQuery = (SearchView) findViewById(R.id.search_view);

        recyclerView = findViewById(R.id.recycler_view);
        mAdapter = new GarageAdapter(garageList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        startSearch = findViewById(R.id.button);
        rq = Volley.newRequestQueue(this);
        initGarageData();
        startSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.clear();
                String garageQuery = (searchQuery.getQuery()).toString();
                sendJsonGarageRequest(garageQuery);
                startSearch.setText("Refresh Garages");
                searchQuery.onActionViewCollapsed();
            }
        });


    }

    public void sendJsonGarageRequest(String garageQuery) {
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, detailsURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse (JSONObject response){
                try {
                    JSONArray garageBlock = response.getJSONArray("blockList");
                    JSONObject floor = garageBlock.getJSONObject(1);

                    name = response.getString("name");
                    id = response.getInt("id");
                    city = response.getString("city");
                    description = response.getString("description");
                    spaces = floor.getInt("currentSpaces");
                    String openTime = response.getString("openTime");

                    Garage garage = new Garage(id, name, city, spaces, 1, description, openTime);
                    garageList.add(garage);
                    mAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("No Garages Were Found");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });
        rq.add(jsonObjectRequest);
    }


    public void initGarageData() {
        /*
        Garage garage = new Garage(1, "Campus Garage", "Oxford", 200, 1,  "A standard Parking Garage", "Open from 7AM to 10PM");
        garageList.add(garage);
        mAdapter.notifyDataSetChanged();
        */
    }

}
