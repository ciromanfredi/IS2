package com.example.is2;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class User {

    private String username;
    private String password;
    private String nome;
    private String cognome;
    private String email;
    private String telefono;
    private String citta;
    ArrayList <String> preferenze;

    public User(){

    }

    public User(String username,){

    }

}
