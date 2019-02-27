package com.example.projet.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projet.R;
import com.example.projet.model.Evenement;

public class EventAdapter extends ArrayAdapter<Evenement> {

    private int myItemLayout;
    private LayoutInflater li;

    public EventAdapter(Context context, int resourceId) {
        super(context, resourceId);
        this.myItemLayout = resourceId;
        this.li = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null)
        {
            view = this.li.inflate(this.myItemLayout, null);
        }

        Evenement v = this.getItem(position);
        if(v != null)
        {

            TextView tv1 = (TextView)view.findViewById(R.id.pseudo_createur);
            tv1.setText(String.valueOf(v.getCreator()));

            TextView tv2 = (TextView)view.findViewById(R.id.nom_event);
            tv2.setText(String.valueOf(v.getNom()));

            TextView tv3 = (TextView)view.findViewById(R.id.adresse);
            tv3.setText(String.valueOf(v.getAdresse()));

            TextView tv4 = (TextView)view.findViewById(R.id.date);
            tv4.setText(String.valueOf(v.getDate()));

        }
        return view;
    }

}
