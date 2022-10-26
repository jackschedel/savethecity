package com.citysavers.savethecity.ui.recyclegame;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecyclegameViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RecyclegameViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is recyclegame fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}