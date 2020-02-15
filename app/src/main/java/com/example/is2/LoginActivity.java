package com.example.is2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
            Toast.makeText(this,"U Signed In successfully",Toast.LENGTH_LONG).show();
            //startActivity(new Intent(this,AnotherActivity.class));
        }else {
            Toast.makeText(this,"U Didnt signed in",Toast.LENGTH_LONG).show();
        }
    }

    public void login(View view){
        String usernamestr = username.getText().toString();
        String passwordstr = password.getText().toString();
        mAuth.signInWithEmailAndPassword(usernamestr, passwordstr)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
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
        FirebaseAuth.getInstance().sendPasswordResetEmail(username.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            Toast.makeText(LoginActivity.this, "Email sent.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
