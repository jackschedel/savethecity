package com.citysavers.savethecity.ui.playscreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.citysavers.savethecity.databinding.FragmentPlayscreenBinding;

public class PlayscreenFragment extends Fragment {

    private FragmentPlayscreenBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PlayscreenViewModel playscreenViewModel =
                new ViewModelProvider(this).get(PlayscreenViewModel.class);

        binding = FragmentPlayscreenBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textPlayscreen;
        playscreenViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}