package com.citysavers.savethecity.ui.playscreen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlayscreenViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PlayscreenViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is playscreen fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}