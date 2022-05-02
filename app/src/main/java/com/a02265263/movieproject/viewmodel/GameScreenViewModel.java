package com.a02265263.movieproject.viewmodel;

import android.os.Handler;
import android.widget.TextView;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.ViewModel;

import com.a02265263.movieproject.R;
import com.a02265263.movieproject.repo.ScoresRepository;
import com.a02265263.movieproject.view.GameItemModel;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.lifecycle.HiltViewModel;

public class GameScreenViewModel extends ViewModel {

    private static GameScreenViewModel ourInstance = new GameScreenViewModel();
    private static Boolean gameActive = false;
    private Long startTime = System.currentTimeMillis();
    private Long endTime = 0L;
    private Handler timerHandler = new Handler();
    private int endID = 0;
    private int moves = 0;
    ScoresRepository repository;

    public static GameScreenViewModel getInstance() {
        return ourInstance;
    }


    public void startGame(){
        if (!gameActive){
            gameActive = true;
        }
    }

    public Boolean getGameActive() {
        return gameActive;
    }

    public void endGame(){
        gameActive = false;
    }

    public void setEndID(int id){
        endID = id;
    }

    public boolean checkIfEnd(int id){
        return endID == id;
    }

    // Running timer
    public void startTimer(TextView timerTextView){
        timerHandler.post(new Runnable() {
            @Override
            public void run() {
                long millis = System.currentTimeMillis() - startTime;
                int seconds = (int) (millis / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                timerTextView.setText(String.format("%d:%02d", minutes, seconds));
                timerHandler.postDelayed(this, 500);
            }
        });
    }

    public long stopTimer(){
        this.endTime = System.currentTimeMillis() - startTime;
        timerHandler.removeCallbacksAndMessages(null);
        return endTime;
    }

    public int getMoves(){
        return moves;
    }
    public void newMove(){
        moves++;
    }
    public void resetMoves(){
        moves = 0;
    }
    public Long getEndTime() {
        return endTime;
    }

    public void resetTimer(){
        startTime = System.currentTimeMillis();
    }
}
