package com.example.is2.ui.aroundme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.is2.R;

public class AroundmeFragment extends Fragment {

    private AroundmeViewModel aroundmeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_aroundme, container, false);
        /*
        aroundmeViewModel =
                ViewModelProviders.of(this).get(AroundmeViewModel.class);
        final TextView textView = root.findViewById(R.id.text_aroundme);
        aroundmeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        */
        return root;
    }

}