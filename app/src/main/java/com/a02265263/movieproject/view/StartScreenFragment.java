package com.a02265263.movieproject.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a02265263.movieproject.R;
import com.a02265263.movieproject.viewmodel.StartScreenViewModel;

public class StartScreenFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        StartScreenViewModel startScreenViewModel = new ViewModelProvider(this).get(StartScreenViewModel.class);

        View view = inflater.inflate(R.layout.fragment_start_screen, container,false);

        view.findViewById(R.id.startGameButton).setOnClickListener(button -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_startScreenFragment_to_levelSelectFragment);
        });
        return view;
    }
}