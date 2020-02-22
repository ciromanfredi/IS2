package com.example.is2.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.is2.LoginActivity;
import com.example.is2.R;
import com.example.is2.javaclass.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    //private ProfileViewModel profileViewModel;
    Button logout_btn;
    TextView c_nome;
    DatabaseReference users;
    User userobj=null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.user_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout){
            Toast.makeText(getActivity(),"Logout", Toast.LENGTH_SHORT).show();
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            getActivity().finish();
            Intent logout_int = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
            startActivity(logout_int);
        }
        return super.onOptionsItemSelected(item);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        c_nome = getActivity().findViewById(R.id.nome);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        /*
        final TextView textView = root.findViewById(R.id.text_profile);
        profileViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        */

        return root;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseUser userfirebase = FirebaseAuth.getInstance().getCurrentUser();
        users = FirebaseDatabase.getInstance().getReference("Users").child(userfirebase.getUid());

        users.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                System.out.println("numero figli"+dataSnapshot.getChildrenCount());
                userobj=dataSnapshot.getValue(User.class);
                System.out.println(userobj.getEmail());

            }

            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });

        /*Button modificabtn = getActivity().findViewById(R.id.modifica);
        modificabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ModProfileActivity.class);
                intent.putExtra("username", userobj.getUsername()); //Optional parameters
                intent.putExtra("nome", userobj.getNome()); //Optional parameters
                intent.putExtra("cognome", userobj.getCognome()); //Optional parameters
                intent.putExtra("telefono", userobj.getTelefono()); //Optional parameters
                intent.putExtra("citta", userobj.getCitta()); //Optional parameters
                intent.putExtra("preferenze",userobj.getPreferenze());
                v.getContext().startActivity(intent);
            }
        });*/

    }


}