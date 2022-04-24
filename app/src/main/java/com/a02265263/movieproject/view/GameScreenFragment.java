package com.a02265263.movieproject.view;

import android.hardware.lights.LightState;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.RecoverySystem;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a02265263.movieproject.R;
import com.a02265263.movieproject.viewmodel.GameScreenViewModel;
import com.a02265263.movieproject.viewmodel.LevelSelectViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GameScreenFragment extends Fragment {
    List<GameItemModel> gameItemList;
    RecyclerView recyclerView;
    TextView timerTextView;
    long startTime = 0;
    Handler timerHandler = new Handler();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        GameScreenViewModel gameScreenViewModel = new ViewModelProvider(this).get(GameScreenViewModel.class);

        View view = inflater.inflate(R.layout.fragment_game_screen, container, false);

        // Setting level number
        int level = LevelSelectViewModel.getLevel();
        TextView levelView = view.findViewById(R.id.levelTxt);
        levelView.setText("Level " + level);

        // Getting details about level
        String[] levelDetails = LevelSelectViewModel.getLevelDetails(level);

        // Setting end goal text
        TextView endTitle = view.findViewById(R.id.endMovieTxt);
        endTitle.setText(levelDetails[4]);

        // Running timer
        TextView timerTextView = view.findViewById(R.id.timerTxt);
        startTime = System.currentTimeMillis();
        Runnable timerRunnable = new Runnable() {
            @Override
            public void run() {
                long millis = System.currentTimeMillis() - startTime;
                int seconds = (int) (millis / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;

                timerTextView.setText(String.format("%d:%02d", minutes, seconds));

                timerHandler.postDelayed(this, 500);
            }
        };
        timerHandler.postDelayed(timerRunnable, 0);


        // Current item picture
        String url = "https://image.tmdb.org/t/p/w500/" + levelDetails[2];
        ImageView imageView = view.findViewById(R.id.gameScreenCurrentImage);
        Glide.with(view.getContext()).load(url).into(imageView);

        // Temporary button at the bottm
        view.findViewById(R.id.gameButton).setOnClickListener(button -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_gameScreenFragment_to_scoreScreenFragment);
        });

        // Everything under here is a mess, I am sorry
        gameItemList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView);

        GetData getData = new GetData();
        getData.execute();

        GameItemAdapter adapter = new GameItemAdapter(this.getContext(), gameItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        recyclerView.setAdapter(adapter);

        return view;
    }


    // This stuff doesn't work and I don't know what I am doing
    public class GetData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";

            try {
                URL url;
                HttpURLConnection urlConnection = null;

                try {
                    url = new URL("https://api.themoviedb.org/3/movie/454626?api_key=10216e19b889e6cba38a744f25087a68&language=en-US&append_to_response=credits");
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int data = isr.read();
                    while (data != -1) {
                        current += (char) data;
                        data = isr.read();
                    }

                    return current;


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONObject("credits").getJSONArray("cast");
//                JSONArray jsonArray = jsonObject.getJSONArray("credits");
//                JSONArray jsonArray = jsonObject2.getJSONArray("cast");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    GameItemModel model = new GameItemModel(
                            jsonObject1.getString("id"),
                            jsonObject1.getString("name"),
                            jsonObject1.getString("profile_path"),
                            jsonObject1.getString("character"),
                            GameItemModel.Type.PERSON
                    );

                    gameItemList.add(model);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

//            PutDataIntoRecyclerView(gameItemList);
        }
    }

//    private void PutDataIntoRecyclerView(List<GameItemModel> gameItemList) {
//        GameItemAdapter adapter = new GameItemAdapter(this.getContext(), gameItemList);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
//
//        recyclerView.setAdapter(adapter);
//    }

}