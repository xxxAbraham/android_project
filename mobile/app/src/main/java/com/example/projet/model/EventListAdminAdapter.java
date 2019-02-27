package com.example.projet.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projet.R;

import java.util.ArrayList;

public class EventListAdminAdapter extends ArrayAdapter {


    ArrayList<Evenement> eventList = new ArrayList<Evenement>();

    public EventListAdminAdapter(Context context, int textViewResourceId, ArrayList objects) {
        super(context, textViewResourceId, objects);
        eventList = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.grid_item, null);
        TextView aName = v.findViewById(R.id.tvnameGrid);
        aName.setText(eventList.get(position).getNom());
        TextView aDate = v.findViewById(R.id.tvdateGrid);
        aDate.setText(eventList.get(position).getDate());
        TextView aAdresse = v.findViewById(R.id.tvadresseGrid);
        aAdresse.setText(eventList.get(position).getAdresse());
        return v;

    }
}
