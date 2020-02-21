package com.example.is2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class ModProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_profile);

        Bundle extras=getIntent().getExtras();
        String username=extras.getString("username");
        String nome=extras.getString("nome");
        String cognome=extras.getString("cognome");
        String telefono=extras.getString("telefono");
        String citta=extras.getString("citta");
        ArrayList<String> preferenze=extras.getStringArrayList("preferenze");


    }
}
