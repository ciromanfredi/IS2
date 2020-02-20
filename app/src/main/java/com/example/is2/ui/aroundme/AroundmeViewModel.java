package com.example.is2.ui.aroundme;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AroundmeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AroundmeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is around fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}