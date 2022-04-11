package com.a02265263.movieproject.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.RecoverySystem;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.a02265263.movieproject.R;
import com.a02265263.movieproject.viewmodel.GameScreenViewModel;
import com.a02265263.movieproject.viewmodel.LevelSelectViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class GameScreenFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        GameScreenViewModel gameScreenViewModel = new ViewModelProvider(this).get(GameScreenViewModel.class);

        View view = inflater.inflate(R.layout.fragment_game_screen, container,false);

        JSONObject jsonObject;

        String url = "https://image.tmdb.org/t/p/w500/aQvJ5WPzZgYVDrxLX4R6cLJCEaQ.jpg";
        ImageView imageView = view.findViewById(R.id.gameScreenCurrentImage);
        Glide.with(view.getContext()).load(url).into(imageView);


        view.findViewById(R.id.gameButton).setOnClickListener(button -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_gameScreenFragment_to_scoreScreenFragment);
        });

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        //TODO finish setAdapter
        recyclerView.setAdapter(new GameItemAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return view;
    }
}