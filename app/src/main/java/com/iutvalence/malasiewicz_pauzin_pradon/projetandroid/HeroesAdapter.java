package com.iutvalence.malasiewicz_pauzin_pradon.projetandroid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HeroesAdapter extends ArrayAdapter {

    private String[] heroes;
    private View cview;
    ArrayList<JSONObject> heroObjs;
    JSONObject dataJson;
    Boolean gotData = false;
    int position;

    public HeroesAdapter(@NonNull Context context, int resource, @NonNull Object[] objects, ArrayList<JSONObject> heroObjs) {
        super(context, resource, objects);
        this.heroes = (String[]) objects;
        this.heroObjs = heroObjs;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        this.position = position;

        if(convertView==null)
        {
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.hero_lv_layout, parent, false);
        }

        this.cview = convertView;

        TextView heroName = convertView.findViewById(R.id.heroNameTextView);
        heroName.setText(heroes[position]);

        TextView timePlayed = convertView.findViewById(R.id.heroTimePLayedValue);
        TextView avgdmg = convertView.findViewById(R.id.avgdmgvalue);
        TextView avgkills = convertView.findViewById(R.id.avgKillsValue);

        try {
            JSONObject avg = heroObjs.get(position).getJSONObject("average");
            JSONObject game = heroObjs.get(position).getJSONObject("game");
            timePlayed.setText(game.get("timePlayed").toString());
            avgdmg.setText(avg.get("allDamageDoneAvgPer10Min").toString());
            avgkills.setText(avg.get("eliminationsAvgPer10Min").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return convertView;
    }

}
