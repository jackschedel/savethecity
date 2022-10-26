package com.example.savethecity.ui.play;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlayViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PlayViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is play fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}