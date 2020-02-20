package com.example.is2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SportEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_event);

        Bundle extras=getIntent().getExtras();
        String nome=extras.getString("nome");
        String luogo=extras.getString("luogo");
        String ora=extras.getString("ora");
        String prezzo=extras.getString("prezzo");
        String maxpartecipanti=extras.getString("maxpartecipanti");
        String currentpartecipanti=extras.getString("currentpartecipanti");
        String sport=extras.getString("sport");
        String proprietario=extras.getString("proprietario");
        String data=extras.getString("data");
        ArrayList<String> partecipanti=extras.getStringArrayList("partecipanti");
        boolean partecipa=false;
        System.out.println(nome);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();
        for(String s : partecipanti)
            System.out.println(s);
        if(partecipanti.contains(userid)) {
            System.out.println("Partecipa a quest evento l'utente corrente");
            partecipa=true;
        }
        else {
            System.out.println("NON Partecipa a quest evento l'utente corrente");
            partecipa=false;
        }
        System.out.println(userid);
        Button button=(Button) findViewById(R.id.button);
        if(partecipa)
            button.setText("Leave");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //button.setText("Leave");
            }
        });
    }
}
