package com.example.projet.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PseudoListAdminAdapter extends ArrayAdapter {


    ArrayList<User> pseudoList = new ArrayList<User>();

    public PseudoListAdminAdapter(Context context, int textViewResourceId, ArrayList objects) {
        super(context, textViewResourceId, objects);
        pseudoList = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.item_listpseudoadmin, null);
        TextView pseudo = v.findViewById(R.id.tvListPseudo);
        pseudo.setText(pseudoList.get(position).getNom());
        return v;

    }
}
