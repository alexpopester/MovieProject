package com.a02265263.movieproject.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavHost;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.a02265263.movieproject.R;
import com.a02265263.movieproject.repo.ScoresRepository;
import com.a02265263.movieproject.viewmodel.LevelCompleteViewModel;
import com.a02265263.movieproject.viewmodel.ScoreScreenViewModel;
import com.airbnb.lottie.LottieAnimationView;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class levelCompleteFragment extends Fragment {

    LottieAnimationView animationView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LevelCompleteViewModel levelCompleteViewModel = new ViewModelProvider(this).get(LevelCompleteViewModel.class);

        View view = inflater.inflate(R.layout.fragment_level_complete, container, false);
        animationView = view.findViewById(R.id.animationView);
        animationView.playAnimation();
        TextView levelTextView = view.findViewById(R.id.congratsLevelTextView);
        levelTextView.setText("" + levelCompleteViewModel.getLevel());
        TextView movesTextView = view.findViewById(R.id.congratsMovesTextView);
        movesTextView.setText("" + levelCompleteViewModel.getMoves());
        TextView timeTextView = view.findViewById(R.id.congratsTimeTextView);
        timeTextView.setText("" + levelCompleteViewModel.getEndTime());
        view.findViewById(R.id.toScoreScreenButton).setOnClickListener(button -> {
            EditText nameEditText = view.findViewById(R.id.editTextPersonName);
            String name = nameEditText.getText().toString();
            levelCompleteViewModel.newScore(name, levelCompleteViewModel.getMoves(), levelCompleteViewModel.getEndTimeLong(), levelCompleteViewModel.getLevel());
            NavHostFragment.findNavController(levelCompleteFragment.this).navigate(R.id.action_levelCompleteFragment_to_scoreScreenFragment);
        });
        return view;
    }
}