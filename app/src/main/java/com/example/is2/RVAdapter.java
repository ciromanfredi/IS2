package com.example.is2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.SportEventViewHolder>{

    ArrayList<SportEvent> sportEvents;

    RVAdapter(ArrayList<SportEvent> sportEvents){
        this.sportEvents = sportEvents;
    }

    @Override
    public SportEventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_element, viewGroup, false);
        SportEventViewHolder pvh = new SportEventViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(SportEventViewHolder sportEventViewHolder, int i) {
        sportEventViewHolder.sportevent_name.setText(sportEvents.get(i).getEventname());
    }

    @Override
    public int getItemCount() {
        return sportEvents.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public static class SportEventViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView sportevent_name;

        SportEventViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            sportevent_name = (TextView)itemView.findViewById(R.id.sportevent_name);

        }


    }

}