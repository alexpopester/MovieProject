package com.a02265263.movieproject.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a02265263.movieproject.R;
import com.a02265263.movieproject.viewmodel.ScoreScreenViewModel;

public class ScoreScreenFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ScoreScreenViewModel scoreScreenViewModel = new ViewModelProvider(this).get(ScoreScreenViewModel.class);

        View view = inflater.inflate(R.layout.fragment_score_screen, container,false);

        view.findViewById(R.id.returnToLevelSelectButton).setOnClickListener(button -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_scoreScreenFragment_to_levelSelectFragment);
        });
        view.findViewById(R.id.retryGameButton).setOnClickListener(button -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_scoreScreenFragment_to_gameScreenFragment);
        });
        return view;
    }
}