package com.iutvalence.malasiewicz_pauzin_pradon.projetandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class HeroDetailActivity extends AppCompatActivity {
    JSONObject heroJsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_detail);

        Intent intent = getIntent();
        String json_object = intent.getStringExtra("HERO_JSON_OBJECT");
        String heroNameString = intent.getStringExtra("HERO_NAME");
        setTitle(heroNameString);

        try {
            heroJsonObject = new JSONObject(json_object);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView heroName = findViewById(R.id.heroNameTextView);
        heroName.setText(heroNameString);

        TextView timePlayed = findViewById(R.id.heroTimePLayedValue);
        TextView avgdmg = findViewById(R.id.avgdmgvalue);
        TextView avgkills = findViewById(R.id.avgKillsValue);
        TextView avgdeath = findViewById(R.id.avgDeathValue);
        TextView mostdmg = findViewById(R.id.mostDmgValue);
        TextView mostTime = findViewById(R.id.mostTimeObjValue);

        try {
            JSONObject avg = heroJsonObject.getJSONObject("average");
            JSONObject game = heroJsonObject.getJSONObject("game");
            JSONObject best = heroJsonObject.getJSONObject("best");

            timePlayed.setText(game.get("timePlayed").toString());
            avgdmg.setText(avg.get("allDamageDoneAvgPer10Min").toString());
            avgkills.setText(avg.get("eliminationsAvgPer10Min").toString());
            avgdeath.setText(avg.get("deathsAvgPer10Min").toString());

            mostdmg.setText(best.get("allDamageDoneMostInGame").toString());
            mostTime.setText(best.get("objectiveTimeMostInGame").toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
