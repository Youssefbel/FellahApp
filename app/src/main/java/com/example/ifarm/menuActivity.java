package com.example.ifarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ifarm.weather.activities.GraphActivity;
import com.example.ifarm.weather.activities.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class menuActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView moveToStatistics, moveToFields, moveToWeather, moveToUpdate,
            moveToTips, moveToSettings,contactUs;

    private ImageView iv_record;
    int flag[]=new int[50];
    TextView tv1;

    public static menuActivity fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                Log.e("tag", "A Kiss every 5 seconds on create");
                verifieNotification();
            }
        },0,20000);


        fa =this;
        tv1=(TextView) findViewById(R.id.tv1);
        String a = getIntent().getStringExtra("name");
        String b = getIntent().getStringExtra("NAME");
        if((a==null)||(b==null)){
            a="Rabat";
            b="";
        }
        tv1.setText(a+" "+b);

        //Defining CARDS
        moveToStatistics = findViewById(R.id.statistiques);
        moveToFields = findViewById(R.id.fields);
        moveToWeather = findViewById(R.id.weather);

        moveToTips = findViewById(R.id.tips);
        moveToSettings = findViewById(R.id.settings);
        iv_record = findViewById(R.id.iv_record);
        contactUs = findViewById(R.id.contactUs);


        //ADDING EVENTS LISTENER TO THE CARDS
        moveToStatistics.setOnClickListener(this);
        moveToFields.setOnClickListener(this);
        moveToWeather.setOnClickListener(this);
        contactUs.setOnClickListener(this);

        moveToTips.setOnClickListener(this);
        moveToSettings.setOnClickListener(this);
        iv_record.setOnClickListener(this);

    }


    @Override
    public void onBackPressed() {
        //No codes at all
    }

  /*  @Override
    public void onResume(){
        super.onResume();
        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                Log.e("tag", "A Kiss every 5 seconds on resume");
                verifieNotification();
            }
        },0,5000);
    }

    @Override
    public void onPause(){
        super.onPause();
        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                Log.e("tag", "A Kiss every 5 seconds on pause");
                verifieNotification();
            }
        },0,5000);
    }*/


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.contactUs:
                intent = new Intent(this, contactUs.class);
                startActivity(intent);
                break;
            case R.id.statistiques:
                intent = new Intent(this, GraphActivity.class);
                startActivity(intent);
                break;
            case R.id.fields:
                String id = getIntent().getStringExtra("id");
               // Log.e("lhrba",id);
                intent = new Intent(this, myFieldsActivity.class);
                intent.putExtra("id_client",id);
                startActivity(intent);
                break;
            case R.id.weather:
                intent = new Intent(this, MainActivity.class);
                String id1 = getIntent().getStringExtra("id");
                intent.putExtra("id1",id1);
                intent.putExtra("fromMain", true);
                startActivity(intent);
                break;
            case R.id.tips:
                intent = new Intent(this, tipsActivity.class);
                startActivity(intent);
                break;
            case R.id.settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;

            case R.id.iv_record:
                intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, 10);
                } else {
                    Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    //TODO: hadi dyal  l management des res
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    if(result.contains("وريني الحقل") || result.contains("وريني الارض") || result.contains("انشوف الارض") || result.contains("هنشوف الارض") || result.contains("نشوف الارض") || result.contains("انشوف الحقل") || result.contains("هنشوف الحقل") || result.contains("نشوف الحقل") ){
                        String id = getIntent().getStringExtra("id");
                        // Log.e("lhrba",id);
                        Intent intent = new Intent(this, myFieldsActivity.class);
                        intent.putExtra("id_client",id);
                        startActivity(intent);
                    }

                    if(result.contains("اسقي الارض") || result.contains("سقي الارض") || result.contains("اسقي الحقل") || result.contains("سقي الحقل") || result.contains("اطلق الماء") || result.contains("طلق الماء") || result.contains("كب الماء") ){
                        //TODO : hna code dyal activation relais !!
                        final WebView web =(WebView) findViewById(R.id.webx);
                        web.loadUrl("http://192.168.43.183/RELAYON");
                    }

                    if(result.contains("حبس الماء") || result.contains("قطع الماء")){
                        //TODO : hna code dyal desactivation relais !!
                        final WebView web =(WebView) findViewById(R.id.webx);
                        web.loadUrl("http://192.168.43.183/RELAYOFF");
                    }

                    if(result.contains("انشوف الجو") || result.contains("هنشوف الجو") || result.contains("نشوف الجو") || result.contains("واش كاينه شي شتاء") ){
                        Intent intent = new Intent(this, MainActivity.class);
                        String id1 = getIntent().getStringExtra("id");
                        intent.putExtra("id1",id1);
                        intent.putExtra("fromMain", true);
                        startActivity(intent);
                    }

                    if(result.contains("وريني الربيع") || result.contains("وريني الخضره")){
                        Intent intent = new Intent(this, tipsActivity.class);
                        startActivity(intent);
                    }

                    if(result.contains("صيفط ميساج") || result.contains("ارا عليها شي ميساج")){
                        Intent intent = new Intent(this, contactUs.class);
                        startActivity(intent);
                    }

                    if(result.contains("وريني الاحصائيات")){
                        Intent intent = new Intent(this, GraphActivity.class);
                        startActivity(intent);
                    }

                    if(result.contains("حمزه زوين")){
                        Toast.makeText(getApplicationContext(),"7miiiime9",Toast.LENGTH_SHORT).show();
                        //addNotification();
                    }
                }
                break;
        }

    }

    //fonction dyal  creation de notification
    private void addNotification(String title, String content,int id) {
        // Builds your notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(content);

        // Creates the intent needed to show the notification
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(id, builder.build());
    }

    // Verifie notification

    private void verifieNotification() {
        String idc = getIntent().getStringExtra("id");
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
                                Double temp_f= response.getDouble("temperature_f");
                                Double humidite_f= response.getDouble("humidite_f");
                                String nomPlante = response.getString("nomPlante");
                                String idff=Integer.toString(id);


                                if(temp > temp_f ) {

                                    addNotification("Alerte arrosage "+nomPlante, "Appuyez sur le bouton arroser(turn on) pour activer l arrosage de votre champ!",i);
                                   // addNotification("Alertegf arrosage "+nomPlante, "wffgsat",1);
                                    flag[i] = 1;
                                   }
                                else {
                                    if(flag[i]==1){
                                        //tfi
                                        addNotification("Alerte Stop arrosage "+nomPlante, "Appuyez sur le bouton arroser(turn off) pour désactiver l arrosage de votre champ",i);
                                        flag[i]=0;
                                    }
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
    }

}
