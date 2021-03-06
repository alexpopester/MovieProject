package com.a02265263.movieproject.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Score implements Comparable<Score>{
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public long moves;

    @ColumnInfo
    public long time;

    @ColumnInfo
    public long level;

    @ColumnInfo
    public int ranking;

    @Override
    public boolean equals(Object o) {
        Score other = (Score) o;
        return other.id == this.id;
    }

    @Override
    public int compareTo(Score o) {
        if (this.moves == o.moves){
            if (this.time < o.time){
                return -1;
            }
            else if (this.time > o.time){
                return 1;
            }
            else return 0;
        }
        else if (this.moves < o.moves){
            return -1;
        }
        else return 1;
    }

    public long getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public long getMoves() {
        return moves;
    }

    public long getLevel() {
        return level;
    }

    public int getRanking(){
        return ranking;
    }

    public void setRanking(int ranking){
        this.ranking = ranking;
    }

    public long getId() {
        return id;
    }
}
