package com.citysavers.savethecity.ui.city;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.citysavers.savethecity.R;
import com.citysavers.savethecity.databinding.FragmentCityBinding;

public class CityFragment extends Fragment {

    private FragmentCityBinding binding;

    public double recycle_meter = 0.99;
    public double water_meter = 0.99;
    public double lights_meter = 0.99;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CityViewModel cityViewModel =
                new ViewModelProvider(this).get(CityViewModel.class);

        binding = FragmentCityBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textCity;
        cityViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        ImageView tree = (ImageView) root.findViewById(R.id.tree);
        if(recycle_meter > 0.75) {
            tree.setAlpha(1.0f);
        } else {
            tree.setAlpha(0.0f);
        }
        ImageView river = (ImageView) root.findViewById(R.id.river);
        if(water_meter > 0.75) {
            river.setAlpha(1.0f);
        } else {
            river.setAlpha(0.0f);
        }
        ImageView lights = (ImageView) root.findViewById(R.id.lights);
        ImageView panels = (ImageView) root.findViewById(R.id.panel);
        if(lights_meter > 0.75) {
            lights.setAlpha(1.0f);
            panels.setAlpha(1.0f);
        } else {
            lights.setAlpha(0.0f);
            panels.setAlpha(0.0f);
        }
        if(lights_meter > 0.75 && water_meter > 0.75 && recycle_meter > 0.75){
            FrameLayout clean = (FrameLayout) root.findViewById(R.id.bround);
            int id = getResources().getIdentifier("@drawable/city_screen_cleanpng", null, getActivity().getPackageName());
            Drawable res = getResources().getDrawable(id);
            clean.setBackground(res);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}