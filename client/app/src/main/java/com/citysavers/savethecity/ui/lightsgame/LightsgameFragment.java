package com.citysavers.savethecity.ui.lightsgame;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.citysavers.savethecity.R;

public class LightsgameFragment extends Fragment {

    private LightsgameViewModel mViewModel;

    public static LightsgameFragment newInstance() {
        return new LightsgameFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lightsgame, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LightsgameViewModel.class);
        // TODO: Use the ViewModel
    }

}