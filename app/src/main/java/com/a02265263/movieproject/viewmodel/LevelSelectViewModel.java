package com.a02265263.movieproject.viewmodel;

import android.view.View;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.ViewModel;

import com.a02265263.movieproject.model.Score;
import com.a02265263.movieproject.repo.ScoresRepository;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;


@HiltViewModel
public class LevelSelectViewModel extends ViewModel {
    private static int level = 0;
    private ScoresRepository repository;
    private int size;

    @Inject
    public LevelSelectViewModel(ScoresRepository repository) {
        this.repository = repository;
    }
    /*
    levelDetails includes, in order
    starting id, starting title, starting image path, ending id, ending title, ending image path, start/end type
     */
    static String[][] levelDetails = {
            {"454626","Sonic the Hedgehog", "/5OjBz8BR1yEfmNyC1oOzDdjv8KN.jpg", "24428", "The Avengers", "/yFSIUVTCvgYrpalUktulvk3Gi5Y.jpg", "MOVIE"},
            {"18918", "Dwayne Johnson", "/kuqFzlYMc2IrsOyPznMd1FroeGq.jpg", "5064", "Meryl Streep", "/xqL5IJxV0fDeD3OfkS3eWqwJoGV.jpg", "PERSON"},
            {"2316","The Office","/qWnJzyZhyy74gjpSjIXWmuk0ifX.jpg","19885","Sherlock","/7WTsnHkbA0FaG6R9twfFde0I9hl.jpg","TV"},
            {"13", "Forrest Gump", "/saHP97rTPS5eLmrLQEcANmKrsFl.jpg", "8587", "The Lion King", "/sKCr78MXSLixwmZ8DyJLrpMsd15.jpg", "MOVIE"},
            {"1245","Scarlett Johansson","/6NsMbJXRlDZuDzatN2akFdGuTvx.jpg","887","Owen Wilson","/op8sGD20k3EQZLR92XtaHoIbW0o.jpg","PERSON"},
            {"62861","Andy Samberg","/uDHHDEoySchljXtIMxjha0Odyfj.jpg","31","Tom Hanks","/xndWFsBlClOJFRdhSt4NBwiPq2o.jpg","PERSON"},
            {"1447","Psych","/fDI15gTVbtW5Sbv5QenqecRxWKJ.jpg","85271","WandaVision","/glKDfE6btIRcVB5zrjspRIs4r52.jpg","TV"},
            {"603","The Matrix","/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg","674","Harry Potter and the Goblet of Fire","/fECBtHlr0RB3foNHDiCBXeg9Bv9.jpg","MOVIE"},
            {"4607","Lost","/og6S0aTZU6YUJAbqxeKjCa3kY1E.jpg","8592","Parks and Recreation","/dFs6yHxheEGoZSoA0Fdkgy6Jxh0.jpg","TV"},
            {"1891","The Empire Strikes Back","/y8kozeXuFDRKGCBRJGfZY0KbGi1.jpg","812","Aladdin (1992)","/oakAd8syy7jNQ4ZoaAGCQkTqcOV.jpg","MOVIE"},
            {"18277","Sandra Bullock","/u2tnZ0L2dwrzFKevVANYT5Pb1nE.jpg","84223","Anna Kendrick","/yirl6fEmeXY5xcvJw3nTcCNq9Cw.jpg","PERSON"},
            {"27205","Inception","/edv5CZvWj09upOsy2Y6IwDhK8bt.jpg","120","The Lord of the Rings: The Fellowship of the Ring","/6oom5QYQ2yQTMJIbnvbkBL9cHo6.jpg","MOVIE"}
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
