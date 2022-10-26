package com.citysavers.savethecity.ui.city;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CityViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CityViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is city fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}