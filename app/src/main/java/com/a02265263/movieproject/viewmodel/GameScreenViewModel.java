package com.a02265263.movieproject.viewmodel;

import android.os.Handler;
import android.widget.TextView;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.ViewModel;

import com.a02265263.movieproject.R;
import com.a02265263.movieproject.view.GameItemModel;

import javax.inject.Singleton;

public class GameScreenViewModel extends ViewModel {

    private static GameScreenViewModel ourInstance = new GameScreenViewModel();
    private static Boolean gameActive = false;
    private Long startTime = System.currentTimeMillis();
    private Handler timerHandler = new Handler();

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

    // Running timer

}
