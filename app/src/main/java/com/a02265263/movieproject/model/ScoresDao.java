package com.a02265263.movieproject.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ScoresDao {

    @Query("SELECT * FROM score")
    List<Score> getScores();

    @Insert
    long createScore(Score score);

    @Update
    void updateScore(Score score);

    @Delete
    void deleteScore(Score score);
}
