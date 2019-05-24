package com.iutvalence.malasiewicz_pauzin_pradon.projetandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileDetailsActivity extends AppCompatActivity {

    String requestResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);

        Intent intent = getIntent();
        this.requestResponse = intent.getStringExtra("API_REQUEST_RESPONSE");


        TextView json = findViewById(R.id.JsonTV);
        json.setText(requestResponse);


    }
}
