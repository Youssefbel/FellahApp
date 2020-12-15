package com.example.ifarm.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ifarm.R;
import com.example.ifarm.model.ProductModel;

public class ProductDetails extends AppCompatActivity {

    ImageView iv_icon,update;
    TextView tv_name, tv_superficie, tv_production, tv_percentage, tv_alert_message, tv_humidite;
   //Button btn_arroser;

    ProductModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        update=findViewById(R.id.update);
        iv_icon = findViewById(R.id.iv_icon);
        tv_name = findViewById(R.id.tv_name);
        tv_superficie = findViewById(R.id.tv_superficie);
        tv_production = findViewById(R.id.tv_production);
        tv_percentage = findViewById(R.id.tv_percentage);
        tv_humidite = findViewById(R.id.tv_humidite);
        tv_alert_message = findViewById(R.id.tv_alert_message);
        final Button btn= findViewById(R.id.btn_arroser);
        // Button btn = (Button) findViewById(R.id.btn);
        btn.setBackgroundColor(Color.GREEN);
        final WebView web =(WebView) findViewById(R.id.web);
        web.setVisibility(View.GONE);


        model = (ProductModel) getIntent().getSerializableExtra("model");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn.getText()=="Turn ON"){
                    web.loadUrl("http://192.168.43.183/RELAYON");
                    btn.setText("Turn Off");
                    btn.setBackgroundColor(Color.RED);

                }
                else{
                    web.loadUrl("http://192.168.43.183/RELAYOFF");
                    btn.setText("Turn ON");
                    btn.setBackgroundColor(Color.GREEN);
                }
            }
        });
        //update button
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetails.this,FormAddField.class);
                intent.putExtra("idc",model.getIdc());
                intent.putExtra("idf",model.getIdf());
                intent.putExtra("superf",model.getSuperficie());
                startActivity(intent);
            }
        });
        iv_icon.setImageResource(model.getIcon());
        tv_name.setText(model.getName());
        tv_superficie.setText(model.getSuperficie());
        tv_production.setText(model.getProduction());
        tv_percentage.setText(model.getPercentage());
        tv_humidite.setText(model.getHumidite());
        Log.e("ff",model.getIdc());
        Log.e("eer",model.getIdf());
        Log.e("ffffs","efefefe");

        showAlertMessage();
    }



    private void showAlertMessage() {
        //TODO: add logic to display an alert according to data
        tv_alert_message.setText("Some text...");
    }
}
