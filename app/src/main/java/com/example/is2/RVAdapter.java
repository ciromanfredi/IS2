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

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.SportEventViewHolder>{

    ArrayList<SportEvent> sportEvents;

    RVAdapter(ArrayList<SportEvent> sportEvents){
        this.sportEvents = sportEvents;
    }

    @Override
    public SportEventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_element, viewGroup, false);
        /*
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
        */
        SportEventViewHolder pvh = new SportEventViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(SportEventViewHolder sportEventViewHolder, int i) {
        sportEventViewHolder.sportevent_name.setText(sportEvents.get(i).getEventname());
        sportEventViewHolder.sportevent_luogo.setText(sportEvents.get(i).getEventplace());
        sportEventViewHolder.sportevent_ora.setText(sportEvents.get(i).getEventhour());
        sportEventViewHolder.sportevent_date.setText(sportEvents.get(i).getEventdate());
        sportEventViewHolder.sportevent_prezzo.setText(sportEvents.get(i).getEventprice()+"€");
        sportEventViewHolder.sportevent_numeropartecipanti.setText(sportEvents.get(i).getPartecipanticorrenti()+"/"+sportEvents.get(i).getEventplayersnumber());
        switch(sportEvents.get(i).getEventsport()) {
            case "Jogging":
                sportEventViewHolder.sportevent_immagine.setImageResource(R.drawable.ic_jogging);
                break;
            case "Tennis":
                sportEventViewHolder.sportevent_immagine.setImageResource(R.drawable.ic_tennis);
                break;
            case "Basket":
                sportEventViewHolder.sportevent_immagine.setImageResource(R.drawable.ic_basket);
                break;
            case "Soccer":
                sportEventViewHolder.sportevent_immagine.setImageResource(R.drawable.ic_soccer);
                break;
            case "Rugby":
                sportEventViewHolder.sportevent_immagine.setImageResource(R.drawable.ic_rugby);
                break;
            case "Volley":
                sportEventViewHolder.sportevent_immagine.setImageResource(R.drawable.ic_volley);
                break;
            default:
                break;
        }
      //  sportEventViewHolder.sportevent_immagine.setImageResource(path);
/*
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
 */

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
        TextView sportevent_date;
        TextView sportevent_prezzo;
        TextView sportevent_numeropartecipanti;
        ImageView sportevent_immagine;

        SportEventViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            sportevent_name = (TextView)itemView.findViewById(R.id.sportevent_name);
            sportevent_luogo = (TextView)itemView.findViewById(R.id.sportevent_place);
            sportevent_date = (TextView)itemView.findViewById(R.id.sportevent_date);
            sportevent_ora = (TextView)itemView.findViewById(R.id.sportevent_hour);
            sportevent_prezzo = (TextView)itemView.findViewById(R.id.sportevent_price);
            sportevent_numeropartecipanti = (TextView)itemView.findViewById(R.id.sportevent_npartecipanti);
            sportevent_immagine = itemView.findViewById(R.id.sportevent_immagine);
        }


    }

}