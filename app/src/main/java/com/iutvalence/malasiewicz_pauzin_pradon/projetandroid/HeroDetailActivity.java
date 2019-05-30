package com.iutvalence.malasiewicz_pauzin_pradon.projetandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;

public class HeroDetailActivity extends AppCompatActivity {
    JSONArray heroJsonArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_detail);

        Intent intent = getIntent();
        String jsonArray = intent.getStringExtra("HERO_JSON_OBJECT");

        try {
            heroJsonArray = new JSONArray(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
