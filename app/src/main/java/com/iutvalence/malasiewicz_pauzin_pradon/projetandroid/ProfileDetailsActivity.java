package com.iutvalence.malasiewicz_pauzin_pradon.projetandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileDetailsActivity extends AppCompatActivity {

    String requestResponse;
    JSONObject dataJson;
    String[] heroes = { "allHeroes", "ana", "baptiste", "bastion", "brigitte", "dVa", "doomfist", "genji", "hanzo", "junkrat", "lucio", "mccree", "mei", "mercy", "moira", "orisa", "pharah", "reaper", "reinhardt", "soldier76", "sombra", "symmetra", "torbjorn", "tracer", "widowmaker", "winston", "wreckingBall", "zarya", "zenyatta"};
    ArrayList<JSONObject> heroobj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);

        Intent intent = getIntent();
        this.requestResponse = intent.getStringExtra("API_REQUEST_RESPONSE");
        String platform = intent.getStringExtra("USER_PLATFORM");
        String region = intent.getStringExtra("USER_REGION");
        String battletag = intent.getStringExtra("USER_BATTLETAG");

        heroobj = new ArrayList<JSONObject>();

        TextView json = findViewById(R.id.JsonTV);
        json.setText(requestResponse);

        try {
            dataJson = new JSONObject(requestResponse);
            JSONObject QPStats = dataJson.getJSONObject("quickPlayStats");
            JSONObject carreerStats = QPStats.getJSONObject("careerStats");
            for(String hero : heroes)
            {
                JSONObject heroStats = carreerStats.getJSONObject(hero);
                heroobj.add(heroStats);
            }
        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + json + "\"");
        }

        try {
            String truc = dataJson.get("level").toString();
            Log.e("truc", truc);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final ListView heroesListView = findViewById(R.id.heroesListView);
        HeroesAdapter heroesAdapter = new HeroesAdapter(this, android.R.layout.simple_list_item_2, heroes, heroobj);
        heroesListView.setAdapter(heroesAdapter);

        heroesListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String heroJSonObjString = heroobj.get(heroesListView.getSelectedItemPosition()).toString();
                Intent heroDetailIntent = new Intent(getBaseContext(), HeroDetailActivity.class);
                heroDetailIntent.putExtra("HERO_JSON_OBJECT", heroJSonObjString);
                startActivity(heroDetailIntent);

            }
        });


    }
}
