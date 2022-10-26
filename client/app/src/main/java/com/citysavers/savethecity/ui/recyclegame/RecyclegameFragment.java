package com.citysavers.savethecity.ui.recyclegame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.citysavers.savethecity.databinding.FragmentRecyclegameBinding;

public class RecyclegameFragment extends Fragment {

    private FragmentRecyclegameBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RecyclegameViewModel recyclegameViewModel =
                new ViewModelProvider(this).get(RecyclegameViewModel.class);

        binding = FragmentRecyclegameBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textRecyclegame;
        recyclegameViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}