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


public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText c_username,c_password,c_nome,c_cognome,c_email,c_telefono,c_citta;
    Button registerButton;
    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Bundle extras=getIntent().getExtras();
        //String usernamedata=extras.getString("username");
        //String passworddata=extras.getString("password");
        c_username = findViewById(R.id.username);
        c_password = findViewById(R.id.password);
        c_nome = findViewById(R.id.nome);
        c_cognome = findViewById(R.id.cognome);
        c_email = findViewById(R.id.email);
        c_telefono = findViewById(R.id.telefono);
        c_citta = findViewById(R.id.citta);
        registerButton = findViewById(R.id.registerBtn);
        //username.setText(usernamedata);
        //password.setText(passworddata);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            finish();
        }

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String username = c_username.getText().toString().trim();
               String password = c_password.getText().toString().trim();
               String nome = c_nome.getText().toString().trim();
               String cognome = c_cognome.getText().toString().trim();
               String email = c_email.getText().toString().trim();
               String telefono = c_telefono.getText().toString().trim();
               String citta = c_citta.getText().toString().trim();

               if(TextUtils.isEmpty(username)){
                    c_username.setError("Inserisci un Username.");
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
               if(TextUtils.isEmpty(email)){
                   c_email.setError("Inserisci la tua Email.");
                   return;
               }
               if(TextUtils.isEmpty(telefono)){
                   c_telefono.setError("Inserisci il tuo numero di Telefono.");
                   return;
               }

               mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           // Sign in success, update UI with the signed-in user's information
                           Log.d(TAG, "createUserWithEmail:success");
                           FirebaseUser user = mAuth.getCurrentUser();
                           Toast.makeText(RegisterActivity.this, "Register success.", Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                       }else{
                           Toast.makeText(RegisterActivity.this,"Error ! "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                       }
                   }
               });

            }
        });
    }

    /*public void goregister(View view) {
        String email=username.getText().toString();
        confermapassword=(EditText) findViewById(R.id.editTextConfermaPassword);
        String passwordstr=password.getText().toString();
        String confermapasswordstr=confermapassword.getText().toString();
        if(!confermapasswordstr.equals(passwordstr)){
            Toast.makeText(this,"Le password non corrispondono",Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, passwordstr)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(RegisterActivity.this, "Register success.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Register failed. "+task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                    }
                });
    }*/

}
