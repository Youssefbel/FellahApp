package com.example.ifarm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ifarm.adapter.RecyclerViewAdapterProduct;
import com.example.ifarm.model.ProductModel;
import com.example.ifarm.ui.FormAddField;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class myFieldsActivity extends AppCompatActivity {

    private RecyclerView rv_items;
    FloatingActionButton fab;
    double temperature;

    private RecyclerViewAdapterProduct adapter;
    private ArrayList<ProductModel> productModel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fields);

        final String idc = getIntent().getStringExtra("id_client");
        //final int idclient = Integer.parseInt(idc);

        String url = "http://192.168.43.74:8080/ibm/webapi/fields/field/"+idc;

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {


                    @Override
                    public void onResponse(JSONArray responseArray) {

                        try {
                            for (int i = 0; i < responseArray.length(); i++) {
                                JSONObject response = responseArray.getJSONObject(i);
                                int id = response.getInt("id");
                                Double temp = response.getDouble("temperature");
                                Double humidite = response.getDouble("humidite");
                                int superficie = response.getInt("superficie");
                                String icon = response.getString("icon");
                                String background = response.getString("background");
                                String nomPlante = response.getString("nomPlante");
                                String idff=Integer.toString(id);
                                initView();
                                initData(nomPlante,temp,humidite,superficie,icon,background,idff,idc);
                                initRecyclerView();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest response", error.toString());
                    }
                });
        requestQueue.add(jsArrayRequest);

        initView();
        //initData();
        initRecyclerView();



    }

    private void initView() {
        rv_items = findViewById(R.id.rl_fields);

    }

    private void initData(String name,double temperature,double humidite, int superficie, String icon, String bg,String idf,String idc) {


        String temp= Double.toString(temperature);
        String hum = Double.toString(humidite);
        String superf = Integer.toString(superficie);

        Log.e("marouane",temp);

        productModel.add(new ProductModel(name, "Superficie: " +superf+ "m²","temperature: "+ temp + " °C", "+0.12 %","humidite: "+ hum +"%", getResources().getIdentifier(bg,"drawable",getPackageName()),
                getResources().getIdentifier(icon,"drawable",getPackageName()) , R.drawable.ic_trending_up_green_24dp,idf,idc));

    }


    private void initRecyclerView() {
        rv_items.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapterProduct(this, productModel);
        rv_items.setAdapter(adapter);
    }
}
