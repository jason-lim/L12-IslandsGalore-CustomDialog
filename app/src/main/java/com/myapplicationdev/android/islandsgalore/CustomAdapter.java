package com.myapplicationdev.android.islandsgalore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {
    Context parent_context;
    int layout_id;
    ArrayList<Item> items;

    public CustomAdapter(Context context, int resource, ArrayList<Item> objects) {
        super(context, resource, objects);
        this.parent_context = context;
        this.layout_id = resource;
        this.items = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtain the LayoutInflater object
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // "Inflate" the View for each row
        View rowView = inflater.inflate(layout_id, parent, false);

        // Obtain the UI components and do the necessary binding
        TextView tvName = rowView.findViewById(R.id.tvName);
        TextView tvDescription = rowView.findViewById(R.id.tvDescription);
        TextView tvSquareKm = rowView.findViewById(R.id.tvSquareKm);

        RatingBar ratingBar = rowView.findViewById(R.id.ratingBar);

        // Obtain the Android Version information based on the position
        Item currentItem = items.get(position);

        // Set values to the TextView to display the corresponding information
        tvName.setText(currentItem.getName());
        tvDescription.setText(currentItem.getDescription());
        ratingBar.setRating(currentItem.getStars());
        tvSquareKm.setText(currentItem.getSquarekm() + "");

        return rowView;
    }

}
