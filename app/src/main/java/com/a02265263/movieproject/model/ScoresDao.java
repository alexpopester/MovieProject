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
    public List<Score> getScores();

    @Insert
    public long createScore(Score score);

    @Delete
    public void deleteScore(Score score);
}
