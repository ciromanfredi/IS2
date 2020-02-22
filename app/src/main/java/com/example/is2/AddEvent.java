package com.example.is2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddEvent extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    EditText c_data,c_time,c_citta,c_indirizzo,c_maxPartecipanti,c_prezzo, c_titolo;
    Button creaButton;
    String indirizzo,citta,data,time,prezzo,titolo;
    String maxPartecipanti;


    TimePickerDialog pickerTime;
    DatePickerDialog picker;
    EditText eText, eTextTime;
    TextView tvw;
    String tipoEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);


        Bundle extras = getIntent().getExtras();


        Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{"Tennis", "Rugby", "Soccer", "Jogging","Basket"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                switch (position) {
                    case 0:
                        tipoEvento = "Tennis";
                        break;
                    case 1:
                        tipoEvento = "Rugby";
                        break;
                    case 2:
                        tipoEvento = "Soccer";
                        break;
                    case 3:
                        tipoEvento = "Jogging";
                        break;
                    case 4:
                        tipoEvento = "Basket";
                        break;
                }
                System.out.println(" Evento tipo : " + tipoEvento);
                //  Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        // Initialize Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        eText = (EditText) findViewById(R.id.editText1);
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AddEvent.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        eTextTime = (EditText) findViewById(R.id.newEvent_time);
        eTextTime.setInputType(InputType.TYPE_NULL);
        eTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldrTime = Calendar.getInstance();
                int hour = cldrTime.get(Calendar.HOUR_OF_DAY);
                int minutes = cldrTime.get(Calendar.MINUTE);
                // time picker dialog
                pickerTime = new TimePickerDialog(AddEvent.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                eTextTime.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                pickerTime.show();
            }
        });
        c_titolo =findViewById(R.id.newEvent_titolo);
        c_prezzo = findViewById(R.id.newEvent_costo);
        c_citta = findViewById(R.id.newEvent_Citta);

        c_indirizzo = findViewById(R.id.newEvent_Indirizzo);
        c_maxPartecipanti = findViewById(R.id.newEvent_MaxPartecipanti);

        creaButton = findViewById(R.id.buttonCrea);
        creaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                indirizzo = c_indirizzo.getText().toString().trim();
                maxPartecipanti = c_maxPartecipanti.getText().toString().trim();
                citta = c_citta.getText().toString().trim();
                prezzo = c_prezzo.getText().toString().trim();
                titolo = c_titolo.getText().toString().trim();
                System.out.println("citta  " + citta);
                data = eText.getText().toString().trim();
                System.out.println("data  " + data);
                time = eTextTime.getText().toString().trim();

                if (TextUtils.isEmpty(data)) {
                    c_data.setError("Inserisci una data.");
                    return;
                }
                if (TextUtils.isEmpty(titolo)) {
                    c_titolo.setError("Inserisci un titolo");
                    return;
                }
                if (TextUtils.isEmpty(time)) {
                    c_time.setError("Inserisci un orario");
                    return;
                }
                if (TextUtils.isEmpty(indirizzo)) {
                    c_indirizzo.setError("Inserisci un indirizzo");
                    return;
                }
                if (TextUtils.isEmpty(citta)) {
                    c_citta.setError("Inserisci una città");
                    return;
                }


                String key = mDatabase.child("SportEvents").push().getKey();

                mDatabase.child("SportEvents").child(key).child("sporteventdate").setValue(data);
                mDatabase.child("SportEvents").child(key).child("sporteventhour").setValue(time);
                mDatabase.child("SportEvents").child(key).child("sporteventplace").setValue(indirizzo);
                mDatabase.child("SportEvents").child(key).child("sporteventsport").setValue(tipoEvento);
                mDatabase.child("SportEvents").child(key).child("sporteventname").setValue(titolo);
                mDatabase.child("SportEvents").child(key).child("sporteventowner").setValue(citta);
                mDatabase.child("SportEvents").child(key).child("eventplayersnumber").setValue(maxPartecipanti);
                mDatabase.child("SportEvents").child(key).child("eventprice").setValue(prezzo);
            }
        }
        );
    }
}