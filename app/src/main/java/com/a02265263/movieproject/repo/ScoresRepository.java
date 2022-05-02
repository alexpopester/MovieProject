package com.a02265263.movieproject.repo;

import android.content.Context;
import android.os.Handler;

import androidx.room.Room;

import com.a02265263.movieproject.model.AppDatabase;
import com.a02265263.movieproject.model.Score;

import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class ScoresRepository {

    AppDatabase db;

    ArrayList<Score> scores = new ArrayList<>();

    private Handler handler = new Handler();

    public class ScoresRepositoryException extends RuntimeException {
        public ScoresRepositoryException(String message) { super(message);}
    }

    public interface ScoresCallback {
        public void call(ArrayList<Score> scores);
    }

    public interface ScoreCallback {
        public void call(Score score);
    }

    public interface ExceptionCallback {
        public void call(ScoresRepositoryException exception);
    }

    @Inject
    public ScoresRepository(@ApplicationContext Context context){
        db = Room.databaseBuilder(context, AppDatabase.class, "scores-database").build();
    }

    public void saveScore(String name, long moves, long time){
        Score newScore = new Score();
        newScore.name = name;
        newScore.moves = moves;
        newScore.time = time;
        new Thread(() -> {
            newScore.id = db.getScoresDao().createScore(newScore);
        }).start();
        scores.add(newScore);
    }

    public void getScores(ScoresCallback callback){
        new Thread(() -> {
            scores.clear();
            scores = (ArrayList<Score>) db.getScoresDao().getScores();
            Collections.sort(scores);
            handler.post(() -> {
                callback.call(scores);
            });
        }).start();
    }
}
