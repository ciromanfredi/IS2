package com.example.is2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SportEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_event);

        Bundle extras=getIntent().getExtras();
        String nome=extras.getString("nome");
        //String passworddata=extras.getString("password");
        System.out.println(nome);
    }
}
