package com.example.projet.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.projet.R;

import java.util.ArrayList;

public class PseudoListAdminAdapter extends ArrayAdapter {

    private Context mContext;

    ArrayList<String> pseudoList = new ArrayList<>();

    public PseudoListAdminAdapter(Context context, int textViewResourceId, ArrayList objects) {
        super(context, textViewResourceId, objects);
        pseudoList = objects;
        mContext = context;

    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_listpseudoadmin,parent,false);

        TextView pseudo = v.findViewById(R.id.tvListPseudo);
        pseudo.setText(pseudoList.get(position));
        return v;

    }
}
