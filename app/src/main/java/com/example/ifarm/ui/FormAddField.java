package com.example.ifarm.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ifarm.MainActivity;
import com.example.ifarm.R;
import com.example.ifarm.menuActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormAddField extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_add_field);

        final String idc = getIntent().getStringExtra("idc");
        final String idf = getIntent().getStringExtra("idf");
        String superf= getIntent().getStringExtra("superf");
        final TextView superfi =(TextView) findViewById(R.id.superf);
        //superfi.setText(superf);
        Button btn = (Button) findViewById(R.id.btn);

        String url = "http://192.168.43.74:8080/ibm/webapi/fields";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {


                    @Override
                    public void onResponse(JSONArray responseArray) {
                        List<String> plante =  new ArrayList<String>();
                        try {
                            for (int i = 0; i < responseArray.length(); i++) {
                                JSONObject response = responseArray.getJSONObject(i);
                                String nomPlante = response.getString("nomPlante");
                                plante.add(nomPlante);

                            }


                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,plante);

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            Spinner sItems = (Spinner) findViewById(R.id.spinner);
                            sItems.setAdapter(adapter);
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






        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String superff = superfi.getText().toString();
                Spinner sItems = (Spinner) findViewById(R.id.spinner);

                String text = sItems.getSelectedItem().toString();
                String url = "http://192.168.43.74:8080/ibm/webapi/fields/update/"+idf+"/"+superff+"/"+text;
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                // String url ="http://www.google.com";

// Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                // Log.e("Response is: ", response.substring(0,500));
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Log.e("error","That didn't work!");
                    }
                });

// Add the request to the RequestQueue.
                queue.add(stringRequest);
                Intent intent = new Intent(FormAddField.this, menuActivity.class);
                intent.putExtra("id",idc);
                startActivity(intent);

            }
        });



    }
}
