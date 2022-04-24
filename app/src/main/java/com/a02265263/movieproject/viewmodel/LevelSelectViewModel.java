package com.a02265263.movieproject.viewmodel;

import android.view.View;

import androidx.lifecycle.ViewModel;

public class LevelSelectViewModel extends ViewModel {
    private static int level = 0;
    /*
    levelDetails includes, in order
    starting id, starting title, starting image path, ending id, ending title, ending image path, start/end type
     */
    static String[][] levelDetails = {
            {"454626","Sonic the Hedgehog", "/5OjBz8BR1yEfmNyC1oOzDdjv8KN.jpg", "24428", "The Avengers", "/yFSIUVTCvgYrpalUktulvk3Gi5Y.jpg", "MOVIE"},
            {"18918", "Dwayne Johnson", "/kuqFzlYMc2IrsOyPznMd1FroeGq.jpg", "5064", "Meryl Streep", "/xqL5IJxV0fDeD3OfkS3eWqwJoGV.jpg", "PERSON"},
            {"13", "Forrest Gump", "/saHP97rTPS5eLmrLQEcANmKrsFl.jpg", "8587", "The Lion King", "/sKCr78MXSLixwmZ8DyJLrpMsd15.jpg", "MOVIE"}
    };

    public static String[] getLevelDetails(int level) {
        return levelDetails[level - 1];
    }

    public static int getLevel() {
        return level;
    }

    public static void setLevel(int level) {
        LevelSelectViewModel.level = level;
    }
}
