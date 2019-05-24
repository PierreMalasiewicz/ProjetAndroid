package com.iutvalence.malasiewicz_pauzin_pradon.projetandroid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



public class HeroesAdapter extends ArrayAdapter {

    private String[] heroes;
    public HeroesAdapter(@NonNull Context context, int resource, @NonNull Object[] objects) {
        super(context, resource, objects);
        this.heroes = (String[]) objects;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null)
        {
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.hero_lv_layout, parent, false);
        }

        TextView heroName = convertView.findViewById(R.id.heroNameTextView);
        heroName.setText(heroes[position]);

        return convertView;
    }
}
