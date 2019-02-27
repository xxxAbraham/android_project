package com.example.projet;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projet.model.User;

import java.util.ArrayList;
import java.util.List;

public class ArrayAdapterDetail extends ArrayAdapter<User> {

    private Context mContext;
    private List<User> moviesList = new ArrayList<>();

    public ArrayAdapterDetail(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<User> list) {
        super(context, 0 , list);
        mContext = context;
        moviesList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.item_event_participants,parent,false);

        User currentMovie = moviesList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.name);
        name.setText(currentMovie.getNom());

        return listItem;
    }
}