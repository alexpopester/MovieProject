package com.a02265263.movieproject.viewmodel;

import android.os.Handler;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.a02265263.movieproject.model.Score;
import com.a02265263.movieproject.repo.ScoresRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ScoreScreenViewModel extends ViewModel {
    ScoresRepository repository;
    ObservableArrayList<Score> scores = new ObservableArrayList<>();
    MutableLiveData<String> errorMessage = new MutableLiveData<>();
    int level;
    GameScreenViewModel gameScreenViewModel = new GameScreenViewModel().getInstance();

    @Inject
    public ScoreScreenViewModel(ScoresRepository repository) {this.repository = repository;}

    public MutableLiveData<String> getErrorMessage() { return errorMessage; }

    public ObservableArrayList<Score> getScores() {
        this.scores.clear();
        this.repository.getScores(scores -> {
            this.scores.addAll(scores);
        });
        return this.scores;
    }
    public ObservableArrayList<Score> getScoresByLevel(){
        level = gameScreenViewModel.getLevel();
        this.scores.clear();
        this.repository.getScoresByLevel(level, scores -> {
            this.scores.addAll(scores);
        });
        return this.scores;
    }
}
