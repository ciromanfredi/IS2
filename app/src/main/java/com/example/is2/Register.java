package com.example.is2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Bundle extras=getIntent().getExtras();
        String usernamedata=extras.getString("username");
        String passworddata=extras.getString("password");
        EditText username=(EditText) findViewById(R.id.editTextUsername);
        EditText password=(EditText) findViewById(R.id.editTextPassword);
        username.setText(usernamedata);
        password.setText(passworddata);
    }


}
