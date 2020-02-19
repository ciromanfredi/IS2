package com.example.is2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;
    EditText username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        //mAuth.signOut();
        username=(EditText) findViewById(R.id.editTextUsername);
        password=(EditText) findViewById(R.id.editTextPassword);
    }

    // controlla ogni volta che viene visualizzata quell'activity se autenticato!
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        checkautenticazione(currentUser);
    }

    public void register(View view) {
        String usernamestr=username.getText().toString();
        String passwordstr=password.getText().toString();
        Intent registerPage = new Intent(LoginActivity.this,RegisterActivity.class);
        registerPage.putExtra("username",usernamestr);
        registerPage.putExtra("password",passwordstr);
        startActivity(registerPage);
    }

    public void  checkautenticazione(FirebaseUser account){
        if(account != null){
            //Toast.makeText(this,"Loggato con successo",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,HomeActivity.class));
        }else {
            //Toast.makeText(this,"Non Loggato",Toast.LENGTH_LONG).show();
        }
    }

    public void login(View view){
        String usernamestr = username.getText().toString();
        String passwordstr = password.getText().toString();
        if(usernamestr.isEmpty() || passwordstr.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Authentication failed. Inserisci username e password", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(usernamestr, passwordstr)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed. "+task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                    }
                });
    }

    public void passwordlost(View view){
        System.out.println("Sono stato premuto password dimenticata");
        FirebaseAuth.getInstance().sendPasswordResetEmail(username.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            System.out.println("Email inviata con successo");
                            Log.d(TAG, "Email sent.");
                            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(LoginActivity.this);
                            dlgAlert.setMessage(R.string.emailinviataconsuccesso);
                            dlgAlert.setTitle(R.string.ripristinopassword);
                            dlgAlert.setPositiveButton("OK", null);
                            dlgAlert.setCancelable(true);
                            dlgAlert.create().show();
                        }
                        else
                        {
                            System.out.println("completato senza successo");
                            Log.w(TAG, "Email sent:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Email sent failed. "+task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
