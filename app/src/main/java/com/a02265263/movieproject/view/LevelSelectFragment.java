package com.a02265263.movieproject.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a02265263.movieproject.R;
import com.a02265263.movieproject.viewmodel.LevelSelectViewModel;

public class LevelSelectFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LevelSelectViewModel levelSelectViewModel = new ViewModelProvider(this).get(LevelSelectViewModel.class);

        View view = inflater.inflate(R.layout.fragment_level_select, container,false);

        view.findViewById(R.id.level1Button).setOnClickListener(button -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_levelSelectFragment_to_gameScreenFragment);
        });
        view.findViewById(R.id.level2Button).setOnClickListener(button -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_levelSelectFragment_to_gameScreenFragment);
        });
        view.findViewById(R.id.level3Button).setOnClickListener(button -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_levelSelectFragment_to_gameScreenFragment);
        });
        return view;
    }
}