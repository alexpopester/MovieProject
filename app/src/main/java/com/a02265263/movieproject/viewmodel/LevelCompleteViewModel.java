package com.a02265263.movieproject.viewmodel;

import androidx.lifecycle.ViewModel;

import com.a02265263.movieproject.repo.ScoresRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LevelCompleteViewModel extends ViewModel {
    GameScreenViewModel gameScreenViewModel;
    ScoresRepository repository;
    int moves;
    int level;
    long endTime;
    String name;

    @Inject
    public LevelCompleteViewModel(ScoresRepository repository) {
        this.gameScreenViewModel = new GameScreenViewModel().getInstance();
        this.repository = repository;
        this.moves = gameScreenViewModel.getMoves();
        this.level = gameScreenViewModel.getLevel();
        this.endTime = gameScreenViewModel.getEndTime();
    }

    public void setName(String name){
        this.name = name;
    }
    public void newScore(String name, int moves, long endTime, int level) {
        this.repository.saveScore(name, moves, endTime, level);
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getMoves() {
        return moves;
    }

    public String getEndTime() {
        long minutes = (long) endTime / (60*1000);
        long seconds = (long) (endTime / 1000) % 60;
        long milliseconds = (long) (endTime / 100) % 10;
        return String.format("%02d:%02d:%02d", minutes, seconds, milliseconds);
    }
    public long getEndTimeLong() {
        return endTime;
    }
}
