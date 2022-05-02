package com.a02265263.movieproject.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a02265263.movieproject.R;
import com.a02265263.movieproject.viewmodel.GameScreenViewModel;
import com.a02265263.movieproject.viewmodel.ScoreScreenViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ScoreScreenFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ScoreScreenViewModel scoreScreenViewModel = new ViewModelProvider(this).get(ScoreScreenViewModel.class);
        GameScreenViewModel gameScreenViewModel = new ViewModelProvider(this).get(GameScreenViewModel.class).getInstance();
        scoreScreenViewModel.newScore(gameScreenViewModel.getMoves(), gameScreenViewModel.getEndTime());

        View view = inflater.inflate(R.layout.fragment_score_screen, container,false);

        view.findViewById(R.id.levelSelectButton).setOnClickListener(button -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_scoreScreenFragment_to_levelSelectFragment);
        });
        view.findViewById(R.id.retryButton).setOnClickListener(button -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_scoreScreenFragment_to_gameScreenFragment);
        });
        int level = gameScreenViewModel.getLevel();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewScoreScreen);
        recyclerView.setAdapter(new ScoreAdapter(scoreScreenViewModel.getScores(level), (score) -> {
            System.out.println("touched");
        }));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}