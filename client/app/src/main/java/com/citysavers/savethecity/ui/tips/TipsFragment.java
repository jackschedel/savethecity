package com.citysavers.savethecity.ui.tips;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.citysavers.savethecity.databinding.FragmentTipsBinding;

public class TipsFragment extends Fragment {

    private FragmentTipsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TipsViewModel tipsViewModel =
                new ViewModelProvider(this).get(TipsViewModel.class);

        binding = FragmentTipsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textTips;
        tipsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}