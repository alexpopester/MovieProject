package com.a02265263.movieproject.view;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.a02265263.movieproject.R;
import com.a02265263.movieproject.viewmodel.GameScreenViewModel;
import com.a02265263.movieproject.viewmodel.LevelSelectViewModel;
import com.bumptech.glide.Glide;

import java.util.concurrent.atomic.AtomicInteger;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LevelSelectFragment extends Fragment {
    private int level = 0;
    private ConstraintLayout levelDetails;
    private FrameLayout rulesPopUp;
    private View view;
    private boolean isUp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LevelSelectViewModel levelSelectViewModel = new ViewModelProvider(this).get(LevelSelectViewModel.class);
        GameScreenViewModel gameScreenViewModel = new ViewModelProvider(this).get(GameScreenViewModel.class);

        view = inflater.inflate(R.layout.fragment_level_select, container,false);

        gameScreenViewModel.endGame();

        rulesPopUp = view.findViewById(R.id.rulePopUp);

        //rules button
        view.findViewById(R.id.rulesPopUpButton).setOnClickListener(button -> {
            rulesPopUp();
        });

        isUp = false;
        levelDetails = view.findViewById(R.id.levelDetails);

        //Level select buttons
        view.findViewById(R.id.level1Button).setOnClickListener(button -> {
            levelSelectOnClick(1);
        });
        view.findViewById(R.id.level2Button).setOnClickListener(button -> {
            levelSelectOnClick(2);
        });
        view.findViewById(R.id.level3Button).setOnClickListener(button -> {
            levelSelectOnClick(3);
        });
        view.findViewById(R.id.level4Button).setOnClickListener(button -> {
            levelSelectOnClick(4);
        });
        view.findViewById(R.id.level5Button).setOnClickListener(button -> {
            levelSelectOnClick(5);
        });
        view.findViewById(R.id.level6Button).setOnClickListener(button -> {
            levelSelectOnClick(6);
        });
        view.findViewById(R.id.level7Button).setOnClickListener(button -> {
            levelSelectOnClick(7);
        });
        view.findViewById(R.id.level8Button).setOnClickListener(button -> {
            levelSelectOnClick(8);
        });
        view.findViewById(R.id.level9Button).setOnClickListener(button -> {
            levelSelectOnClick(9);
        });
        view.findViewById(R.id.level10Button).setOnClickListener(button -> {
            levelSelectOnClick(10);
        });
        view.findViewById(R.id.level11Button).setOnClickListener(button -> {
            levelSelectOnClick(11);
        });
        view.findViewById(R.id.level12Button).setOnClickListener(button -> {
            levelSelectOnClick(12);
        });

        // X button
        view.findViewById(R.id.levelDetailsExitButton).setOnClickListener(button -> {
            slideDown(levelDetails);
        });

        // Start level button
        view.findViewById(R.id.startLevelButton).setOnClickListener(button -> {
            LevelSelectViewModel.setLevel(level);
            NavHostFragment.findNavController(this).navigate(R.id.action_levelSelectFragment_to_gameScreenFragment);
        });

        return view;
    }

    private void ShowLevelDetails() {
        String[] levelDetails = LevelSelectViewModel.getLevelDetails(level);

        TextView levelNumber = view.findViewById(R.id.levelDetailsLevelNumber);
        levelNumber.setText("Level " + level);

        TextView startTitle = view.findViewById(R.id.levelDetailsStartTitle);
        startTitle.setText(levelDetails[1]);

        TextView endTitle = view.findViewById(R.id.levelDetailsEndTitle);
        endTitle.setText(levelDetails[4]);
        
        ImageView startImage = view.findViewById(R.id.levelDetailsStartImage);
        Glide.with(view)
                .load("https://image.tmdb.org/t/p/w500" + levelDetails[2])
                .into(startImage);

        ImageView endImage = view.findViewById(R.id.levelDetailsEndImage);
        Glide.with(view)
                .load("https://image.tmdb.org/t/p/w500" + levelDetails[5])
                .into(endImage);
    }

    // slide the view from below itself to the current position
    public void slideUp(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(750);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        isUp = true;
    }

    // slide the view from its current position to below itself
    public void slideDown(View view){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(750);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        isUp = false;
    }

    //slides up level details
    private void levelSelectOnClick(int level) {
        this.level = level;
        ShowLevelDetails();
        if(!isUp) {
            slideUp(levelDetails);
        }
    }

    // brings up rules pop up
    private void rulesPopUp() {
        rulesPopUp.setVisibility(View.VISIBLE);

        ImageView rulesImage = rulesPopUp.findViewById(R.id.rulesExplanation);
        rulesImage.setImageResource(R.drawable.rules_page_1);
        AtomicInteger page = new AtomicInteger(1);

        view.findViewById(R.id.rulesExitButton).setOnClickListener(button -> {
            rulesPopUp.setVisibility(View.INVISIBLE);
        });

        rulesPopUp.setOnClickListener(button -> {
            if (page.get() == 1) {
                page.getAndIncrement();
                rulesImage.setImageResource(R.drawable.rules_page_2);
            } else if (page.get() == 2) {
                page.getAndIncrement();
                rulesImage.setImageResource(R.drawable.rules_page_3);
            } else if (page.get() == 3) {
                page.getAndIncrement();
                rulesImage.setImageResource(R.drawable.rules_page_4);
            } else if (page.get() == 4) {
                page.getAndIncrement();
                rulesImage.setImageResource(R.drawable.rules_page_5);
            } else {
                rulesPopUp.setVisibility(View.INVISIBLE);
            }
        });
    }

}