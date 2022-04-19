package com.a02265263.movieproject.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Score implements Comparable<Score>{
    @PrimaryKey
    public long id;

    @ColumnInfo
    public String standing;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public long moves;

    @ColumnInfo
    public long time;

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
}
