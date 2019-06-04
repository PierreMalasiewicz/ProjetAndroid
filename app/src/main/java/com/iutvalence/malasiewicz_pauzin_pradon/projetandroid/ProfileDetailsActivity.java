package com.iutvalence.malasiewicz_pauzin_pradon.projetandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileDetailsActivity extends AppCompatActivity {

    String requestResponse;
    JSONObject dataJson;
    String[] heroes = { "ana", "baptiste", "bastion", "brigitte", "dVa", "doomfist", "genji", "hanzo", "junkrat", "lucio", "mccree", "mei", "mercy", "moira", "orisa", "pharah", "reaper", "reinhardt", "soldier76", "sombra", "symmetra", "torbjorn", "tracer", "widowmaker", "winston", "wreckingBall", "zarya", "zenyatta"};
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

        setTitle(battletag);

        heroobj = new ArrayList<JSONObject>();

        /*TextView json = findViewById(R.id.JsonTV);
        json.setText(requestResponse);*/
        String level = "??";
        String wins = "??";
        String KDA = "??";
        String playTime = "??";
        String AVG_DMG = "??";
        String AVG_HEAL = "??";
        int assists = 0;

        try {
            level = new JSONObject(requestResponse).getString("level");
            JSONObject QP_Data = new JSONObject(requestResponse).getJSONObject("quickPlayStats");
            wins = QP_Data.getJSONObject("games").getString("won");
            assists = QP_Data.getJSONObject("careerStats").getJSONObject("allHeroes").getJSONObject("assists").getInt("defensiveAssists") + QP_Data.getJSONObject("careerStats").getJSONObject("allHeroes").getJSONObject("assists").getInt("offensiveAssists");
            KDA = QP_Data.getJSONObject("careerStats").getJSONObject("allHeroes").getJSONObject("combat").getString("eliminations") + " / "
                    + QP_Data.getJSONObject("careerStats").getJSONObject("allHeroes").getJSONObject("combat").getString("deaths") + " / "
                    + assists;
            playTime = QP_Data.getJSONObject("careerStats").getJSONObject("allHeroes").getJSONObject("game").getString("timePlayed");
            AVG_DMG = QP_Data.getJSONObject("careerStats").getJSONObject("allHeroes").getJSONObject("average").getString("allDamageDoneAvgPer10Min");
            AVG_HEAL = QP_Data.getJSONObject("careerStats").getJSONObject("allHeroes").getJSONObject("average").getString("healingDoneAvgPer10Min");
        }
        catch (Exception ex) {
            Log.e("err:", ex.getMessage());
        }
        TextView levelTV = findViewById(R.id.LevelField);
        levelTV.setText(level);

        TextView winsTV = findViewById(R.id.WinsField);
        winsTV.setText(wins);

        TextView KDA_TV = findViewById(R.id.KDA_Field);
        KDA_TV.setText(KDA);

        TextView PT = findViewById(R.id.playTime);
        PT.setText(playTime);

        TextView DMG_TV = findViewById(R.id.avgDMG);
        DMG_TV.setText(AVG_DMG);

        TextView HEAL_TV = findViewById(R.id.avgHeal);
        HEAL_TV.setText(AVG_HEAL);

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
            Log.e("Error", "Could not parse malformed JSON: \"" + dataJson + "\"");
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

        heroesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String heroJSonObjString = heroobj.get(position).toString();
                Intent heroDetailIntent = new Intent(getBaseContext(), HeroDetailActivity.class);
                heroDetailIntent.putExtra("HERO_JSON_OBJECT", heroJSonObjString);
                heroDetailIntent.putExtra("HERO_NAME",heroes[position]);
                startActivity(heroDetailIntent);
            }
        });


    }
}
