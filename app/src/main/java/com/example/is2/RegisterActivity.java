package com.example.is2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.*;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    EditText c_username,c_password,c_confermapassword,c_nome,c_cognome,c_email,c_telefono,c_citta;
    Button registerButton;
    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Bundle extras=getIntent().getExtras();
        String emaildata=extras.getString("email");
        String passworddata=extras.getString("password");

        c_email = findViewById(R.id.email);
        c_password = findViewById(R.id.password);
        c_confermapassword=findViewById(R.id.confermapassword);
        c_username = findViewById(R.id.username);
        c_nome = findViewById(R.id.nome);
        c_cognome = findViewById(R.id.cognome);
        c_telefono = findViewById(R.id.telefono);
        c_citta = findViewById(R.id.citta);
        registerButton = findViewById(R.id.registerBtn);

        c_email.setText(emaildata);
        c_password.setText(passworddata);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Initialize Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            finish();
        }

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = c_email.getText().toString().trim();
               final String password = c_password.getText().toString().trim();
               final String confermapassword = c_confermapassword.getText().toString().trim();
               final String username = c_username.getText().toString().trim();
               final String nome = c_nome.getText().toString().trim();
               final String cognome = c_cognome.getText().toString().trim();
               final String telefono = c_telefono.getText().toString().trim();
               final String citta = c_citta.getText().toString().trim();
/*
                if(TextUtils.isEmpty(email)){
                    c_email.setError("Inserisci la tua Email.");
                    return;
                }
               if(TextUtils.isEmpty(password)){
                    c_password.setError("Inserisci una Password.");
                    return;
               }
               if(password.length()<6){
                    c_password.setError("La Password deve avere almeno 6 caratteri.");
                    return;
               }
               if(!confermapassword.equals(password)) {
                   c_confermapassword.setError("Le due password non corrispondono");
                   return;
               }
                if(TextUtils.isEmpty(username)){
                    c_username.setError("Inserisci un Username.");
                    return;
                }
               if(TextUtils.isEmpty(nome)){
                   c_nome.setError("Inserisci il tuo Nome.");
                   return;
               }
               if(TextUtils.isEmpty(cognome)){
                    c_cognome.setError("Inserisci il tuo Cognome.");
                    return;
               }
               if(TextUtils.isEmpty(citta)){
                   c_citta.setError("Inserisci una Città");
                   return;
               }
               if(TextUtils.isEmpty(telefono)){
                   c_telefono.setError("Inserisci il tuo numero di Telefono.");
                   return;
               }
*/
               mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           // Sign in success, update UI with the signed-in user's information
                           Log.d(TAG, "createUserWithEmail:success");
                           FirebaseUser user = mAuth.getCurrentUser();
                           System.out.println("user "+user.getUid());
                           Toast.makeText(RegisterActivity.this, "Register success.", Toast.LENGTH_SHORT).show();
                           User userobj=new User(email,password,username,nome,cognome,telefono,citta);
                           mDatabase.child("Users").child(String.valueOf(user.getUid())).setValue(userobj);
                           startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                       }else{
                           Toast.makeText(RegisterActivity.this,"Error ! "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                       }
                   }
               });

            }
        });
    }

}
