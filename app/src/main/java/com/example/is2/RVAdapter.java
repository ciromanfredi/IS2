package com.example.is2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.SportEventViewHolder>{

    ArrayList<SportEvent> sportEvents;

    RVAdapter(ArrayList<SportEvent> sportEvents){
        this.sportEvents = sportEvents;
    }

    @Override
    public SportEventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_element, viewGroup, false);
        final Context context = v.getContext();
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SportEventActivity.class);
                intent.putExtra("nome", sportEvents.get(i).getEventname()); //Optional parameters
                intent.putExtra("luogo", sportEvents.get(d).getEventplace());
                intent.putExtra("ora", sportEvents.get(d).getEventhour());
                intent.putExtra("prezzo", sportEvents.get(d).getEventprice());
                intent.putExtra("maxpartecipanti", sportEvents.get(d).getEventplayersnumber());
                intent.putExtra("currentpartecipanti", sportEvents.get(d).getPartecipanticorrenti());
                intent.putExtra("sport", sportEvents.get(d).getEventsport());
                intent.putExtra("proprietario", sportEvents.get(d).getEventowner());
                intent.putExtra("data", sportEvents.get(d).getEventdate());
                context.startActivity(intent);
            }
        });
        SportEventViewHolder pvh = new SportEventViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(SportEventViewHolder sportEventViewHolder, int i) {
        sportEventViewHolder.sportevent_name.setText(sportEvents.get(i).getEventname());
        sportEventViewHolder.sportevent_luogo.setText(sportEvents.get(i).getEventplace());
        sportEventViewHolder.sportevent_ora.setText(sportEvents.get(i).getEventhour());
        sportEventViewHolder.sportevent_prezzo.setText(sportEvents.get(i).getEventprice());
        sportEventViewHolder.sportevent_numeropartecipanti.setText(sportEvents.get(i).getPartecipanticorrenti()+"/"+sportEvents.get(i).getEventplayersnumber());

        sportEventViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SportEventActivity.class);
                intent.putExtra("nome", sportEvents.get(i).getEventname()); //Optional parameters
                intent.putExtra("luogo", sportEvents.get(d).getEventplace());
                intent.putExtra("ora", sportEvents.get(d).getEventhour());
                intent.putExtra("prezzo", sportEvents.get(d).getEventprice());
                intent.putExtra("maxpartecipanti", sportEvents.get(d).getEventplayersnumber());
                intent.putExtra("currentpartecipanti", sportEvents.get(d).getPartecipanticorrenti());
                intent.putExtra("sport", sportEvents.get(d).getEventsport());
                intent.putExtra("proprietario", sportEvents.get(d).getEventowner());
                intent.putExtra("data", sportEvents.get(d).getEventdate());
                context.startActivity(intent);
            }
        });
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
        TextView sportevent_luogo;
        TextView sportevent_ora;
        TextView sportevent_prezzo;
        TextView sportevent_numeropartecipanti;

        SportEventViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            sportevent_name = (TextView)itemView.findViewById(R.id.name);
            sportevent_luogo = (TextView)itemView.findViewById(R.id.luogo);
            sportevent_ora = (TextView)itemView.findViewById(R.id.ora);
            sportevent_prezzo = (TextView)itemView.findViewById(R.id.prezzo);
            sportevent_numeropartecipanti = (TextView)itemView.findViewById(R.id.numeropartecipanti);

        }


    }

}