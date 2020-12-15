package com.example.ifarm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ifarm.account.RegisterActivity;
import com.example.ifarm.utils.AnimationUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText email, password;
    private Button login, register;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Please wait..");
        mDialog.setIndeterminate(true);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
    }

    public void onLogin(View view) {
        performLogin();
    }

    public void onRegister(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void onForgotPassword(View view) {

    }

    public void performLogin() {

        final String email_, pass_;
        email_ = email.getText().toString();
        pass_ = password.getText().toString();

        if (!TextUtils.isEmpty(email_) && !TextUtils.isEmpty(pass_)) {
            mDialog.show();
            //TODO: code here to sign in

            String url = "http://192.168.43.74:8080/ibm/webapi/login";

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonArrayRequest jsArrayRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {


                        @Override
                        public void onResponse(JSONArray responseArray) {

                            try {

                                for (int i = 0; i < responseArray.length(); i++) {
                                    JSONObject response = responseArray.getJSONObject(i);
                                    int idClient = response.getInt("idClient");
                                    String login = response.getString("login");
                                    String password = response.getString("password");
                                    Log.e("saat",login + password);

                                    if(login.equals(email_) && password.equals(pass_)) {
                                        Intent intent =new Intent(getApplicationContext(), menuActivity.class);
                                        intent.putExtra("id",Integer.toString(idClient));
                                        startActivity(intent);
                                        finish();
                                    }

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

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        } else if (TextUtils.isEmpty(email_)) {


            AnimationUtil.shakeView(email, this);

        } else if (TextUtils.isEmpty(pass_)) {

            AnimationUtil.shakeView(password, this);

        } else {

            AnimationUtil.shakeView(email, this);
            AnimationUtil.shakeView(password, this);

        }

    }
}
