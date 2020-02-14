package com.example.is2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void register(View view) {
        System.out.println("Mi hai premuto");
        EditText username=(EditText) findViewById(R.id.editTextUsername);
        EditText password=(EditText) findViewById(R.id.editTextPassword);
        String usernamestr=username.getText().toString();
        String passwordstr=password.getText().toString();
        Intent registerPage = new Intent(MainActivity.this,Register.class);
        registerPage.putExtra("username",usernamestr);
        registerPage.putExtra("password",passwordstr);
        startActivity(registerPage);
    }
}
