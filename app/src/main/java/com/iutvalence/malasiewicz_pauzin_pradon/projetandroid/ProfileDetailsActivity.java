package com.iutvalence.malasiewicz_pauzin_pradon.projetandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileDetailsActivity extends AppCompatActivity {

    String requestResponse;
    JSONObject dataJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);

        Intent intent = getIntent();
        this.requestResponse = intent.getStringExtra("API_REQUEST_RESPONSE");


        TextView json = findViewById(R.id.JsonTV);
        json.setText(requestResponse);

        try {

            dataJson = new JSONObject(requestResponse);

            Log.d("My App", dataJson.toString());

        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + json + "\"");
        }

        try {
            String truc = dataJson.get("level").toString();
            Log.e("truc", truc);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
