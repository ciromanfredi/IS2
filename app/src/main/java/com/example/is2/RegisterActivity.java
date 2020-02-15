package com.example.is2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    EditText username,password,confermapassword;
    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Bundle extras=getIntent().getExtras();
        String usernamedata=extras.getString("username");
        String passworddata=extras.getString("password");
        username=(EditText) findViewById(R.id.editTextUsername);
        password=(EditText) findViewById(R.id.editTextPassword);
        username.setText(usernamedata);
        password.setText(passworddata);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    public void goregister(View view) {
        String email=username.getText().toString();
        confermapassword=(EditText) findViewById(R.id.editTextConfermaPassword);
        String passwordstr=password.getText().toString();
        String confermapasswordstr=confermapassword.getText().toString();
        if(!confermapasswordstr.equals(passwordstr)){
            Toast.makeText(this,"La password non corrisponde",Toast.LENGTH_SHORT).show();
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
    }

}
