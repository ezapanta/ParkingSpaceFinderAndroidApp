package com.example.parkingspacefinder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class GarageAdapter extends RecyclerView.Adapter<GarageAdapter.MyViewHolder> {

    private List<Garage> garageList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, location, spacesAvailable;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            location = (TextView) view.findViewById(R.id.location);
            spacesAvailable = (TextView) view.findViewById(R.id.spacesAvailable);
        }
    }


    public GarageAdapter(List<Garage> garageList) {
        this.garageList = garageList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.garage_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Garage garage = garageList.get(position);
        holder.title.setTextColor(Color.RED);
        holder.title.setText(garage.getName());
        holder.location.setText(garage.getCity());
        holder.spacesAvailable.setText("Spaces Left: " + garage.getSpacesAvailable());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(v.getContext());
                mBuilder.setTitle(garage.getName());
                mBuilder.setMessage("Spaces Available: " + garage.getSpacesAvailable() +
                        "\nFloors:" + garage.getFloors() +
                        "\nCity: " + garage.getCity() +
                        "\nHours: 7:00 AM - 10:00PM" +
                        "\n\n" + garage.getDescription());
                mBuilder.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = mBuilder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return garageList.size();
    }


    public void clear() {
        final int size = garageList.size();
        garageList.clear();
        notifyItemRangeRemoved(0, size);
    }

}