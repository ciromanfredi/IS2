package com.example.is2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.example.is2.ui.events.EventsFragment;

import java.util.ArrayList;

public class FilterEvents extends AppCompatActivity implements View.OnClickListener {
    CheckBox check_soccer,check_tennis,check_basket,check_jogging,check_rugby;
    String soccer,tennis,basket,jogging,rugby;
    ArrayList<String> lista_preferenze;
    Button apply_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_events);
        lista_preferenze = new ArrayList<>();
        check_soccer = (CheckBox) findViewById(R.id.checkBox_Soccer);
        check_soccer.setOnClickListener(this);

        check_tennis = (CheckBox)findViewById(R.id.checkBox_Tennis);
        check_tennis.setOnClickListener(this);

        check_basket = (CheckBox)findViewById(R.id.checkBox_Basket);
        check_basket.setOnClickListener(this);

        check_jogging = (CheckBox)findViewById(R.id.checkBox_Jogging);
        check_jogging.setOnClickListener(this);

        check_rugby = (CheckBox)findViewById(R.id.checkBox_Rugby);
        check_rugby.setOnClickListener(this);

        apply_btn = (Button)findViewById(R.id.filter_apply);

        /*check_soccer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check_soccer.isChecked()){
                    soccer = "Soccer";
                    lista_preferenze.add(soccer);
                    System.out.println("AGGIUNGO SOCCER AI FILTRI" +lista_preferenze);
                }
            }
        });
        check_soccer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check_tennis.isChecked()){
                    tennis = "Tennis";
                    lista_preferenze.add(tennis);
                }
            }
        });
        check_soccer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check_tennis.isChecked()){
                    basket = "Basket";
                    lista_preferenze.add(basket);
                }
            }
        });
        check_soccer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check_tennis.isChecked()){
                    jogging = "Jogging";
                    lista_preferenze.add(jogging);
                }
            }
        });
        check_soccer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check_tennis.isChecked()){
                    rugby = "Rugby";
                    lista_preferenze.add(rugby);
                }
            }
        });*/
        apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Bundle b = new Bundle();
                //b.putStringArrayList("preferenze", lista_preferenze);
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                System.out.println("FILTER:"+lista_preferenze);
                intent.putStringArrayListExtra("preferenze", lista_preferenze);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View view) {

        if(check_soccer.isChecked()){
            soccer = "Soccer";
            lista_preferenze.add(soccer);
            System.out.println("AGGIUNGO SOCCER AI FILTRI" +lista_preferenze);
        }
        if(check_tennis.isChecked()){
            tennis = "Tennis";
            lista_preferenze.add(tennis);
        }
        if(check_basket.isChecked()){
            basket = "Basket";
            lista_preferenze.add(basket);
        }
        if(check_jogging.isChecked()){
            jogging = "Jogging";
            lista_preferenze.add(jogging);
        }
        if(check_rugby.isChecked()){
            rugby = "Rugby";
            lista_preferenze.add(rugby);
        }


    }
}
