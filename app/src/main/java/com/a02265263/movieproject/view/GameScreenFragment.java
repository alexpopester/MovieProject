package com.a02265263.movieproject.view;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.a02265263.movieproject.R;
import com.a02265263.movieproject.viewmodel.GameScreenViewModel;
import com.a02265263.movieproject.viewmodel.LevelSelectViewModel;
import com.bumptech.glide.Glide;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.IOUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class GameScreenFragment extends Fragment {
    RecyclerView recyclerView;
    TextView timerTextView;
    long startTime = 0;
    Handler timerHandler = new Handler();
    JSONObject currentGameItem;
    List<GameItemModel> gameItemList;
    int id;
    boolean gameActive = false;



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
        String currentType = levelDetails[6];
        if (!gameActive) {
            id = Integer.parseInt(levelDetails[0]);
            gameActive = true;
        }

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


        // Temporary button at the bottom
        view.findViewById(R.id.gameButton).setOnClickListener(button -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_gameScreenFragment_to_scoreScreenFragment);
        });

        // Fetch the JSONObject, different call depending on type
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    switch (currentType) {
                        case "MOVIE":
                            currentGameItem = callJsonApi("https://api.themoviedb.org/3/movie/" + id + "?api_key=10216e19b889e6cba38a744f25087a68&language=en-US&append_to_response=release_dates,credits");
                            break;
                        case "PERSON":
                            currentGameItem = callJsonApi("https://api.themoviedb.org/3/person/" + id + "?api_key=10216e19b889e6cba38a744f25087a68&language=en-US&append_to_response=combined_credits");
                            break;
                        case "TV":
                            currentGameItem = callJsonApi(" https://api.themoviedb.org/3/tv/" + id + "?api_key=10216e19b889e6cba38a744f25087a68&language=en-US");
                            break;
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        // Makes it so stuff underneath doesn't happen before the JSONObject is loaded
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Calls the correct method for the type to fill in all the UI with info from JSONObject
        switch (currentType) {
            case "MOVIE":
                showMovieInfo(currentGameItem, view);
                break;
            case "PERSON":
                showPersonInfo(currentGameItem, view);
                break;
            case "TV":
                showTVInfo(currentGameItem, view);
                break;
        }


        gameItemList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView);

        GameItemAdapter adapter = new GameItemAdapter(this.getContext(), gameItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        recyclerView.setAdapter(adapter);

        return view;
    }

    public static JSONObject callJsonApi(String url)
            throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            String jsonText = IOUtils.toString(is, "UTF-8");
            JSONObject json = new JSONObject(jsonText);
            System.out.println(json.toString(4));
            return json;
        } finally {
            is.close();
        }
    }

    private void showMovieInfo(JSONObject currentGameItem, View view) {
        try {
            // Current item picture
            String imageUrl = "https://image.tmdb.org/t/p/w500/" + currentGameItem.get("poster_path");
            ImageView imageView = view.findViewById(R.id.gameScreenCurrentImage);
            Glide.with(view.getContext()).load(imageUrl).into(imageView);

            // Current item title
            String itemTitle = currentGameItem.getString("original_title");
            TextView titleView = view.findViewById(R.id.currentTitleTxt);
            titleView.setText(itemTitle);

            // Current item date
            String itemDate = currentGameItem.getString("release_date");
            itemDate = itemDate.substring(0,4);
            TextView dateView = view.findViewById(R.id.gameItemDate);
            dateView.setText(itemDate);

            // Current runtime
            int runTime = currentGameItem.getInt("runtime");
            String runTimeString = String.format("%sh %sm", runTime / 60, runTime % 60);
            TextView runTimeView = view.findViewById(R.id.runTimeTxt);
            runTimeView.setText(runTimeString);

            // Current item description
            String description = currentGameItem.getString("overview");
            TextView descriptionView = view.findViewById(R.id.descriptionTxt);
            descriptionView.setText(description);

            // Current item rating
            String rating = " ";
            JSONObject temp = currentGameItem.getJSONObject("release_dates");
            JSONArray releaseDates = temp.getJSONArray("results");
            boolean found = false;
            int i = 0;
            while (!found && i < releaseDates.length()) {
                JSONObject releaseDate = releaseDates.getJSONObject(i);
                if (releaseDate.getString("iso_3166_1").equals("US")) {
                    JSONArray temp2 = releaseDate.getJSONArray("release_dates");
                    JSONObject releaseInfo = temp2.getJSONObject(0);
                    rating = releaseInfo.getString("certification");
                    found = true;
                }
                i++;
            }
            TextView ratingsView = view.findViewById(R.id.ratingsTxt);
            ratingsView.setText(rating);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showPersonInfo(JSONObject currentGameItem, View view) {
        try {
            // Current person picture
            String imageUrl = "https://image.tmdb.org/t/p/w500/" + currentGameItem.get("profile_path");
            ImageView imageView = view.findViewById(R.id.gameScreenCurrentImage);
            Glide.with(view.getContext()).load(imageUrl).into(imageView);

            // Current person name
            String itemTitle = currentGameItem.getString("name");
            TextView titleView = view.findViewById(R.id.currentTitleTxt);
            titleView.setText(itemTitle);

            // Current person birthday
            String itemDate = currentGameItem.getString("birthday");
            TextView dateView = view.findViewById(R.id.gameItemDate);
            dateView.setText(itemDate);

            // Current runtime
//                int runTime = currentGameItem.getInt("runtime");
//                String runTimeString = String.format("%sh %sm", runTime / 60, runTime % 60);
            TextView runTimeView = view.findViewById(R.id.runTimeTxt);
            runTimeView.setVisibility(View.INVISIBLE);
//                runTimeView.setText(runTimeString);

            // Current person biography
            String description = currentGameItem.getString("biography");
            TextView descriptionView = view.findViewById(R.id.descriptionTxt);
            descriptionView.setText(description);

            // Current item rating
            TextView ratingsView = view.findViewById(R.id.ratingsTxt);
            ratingsView.setVisibility(View.INVISIBLE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showTVInfo(JSONObject currentGameItem, View view) {
        try {
            // Current item picture
            String imageUrl = "https://image.tmdb.org/t/p/w500/" + currentGameItem.get("poster_path");
            ImageView imageView = view.findViewById(R.id.gameScreenCurrentImage);
            Glide.with(view.getContext()).load(imageUrl).into(imageView);

            // Current item title
            String itemTitle = currentGameItem.getString("original_name");
            TextView titleView = view.findViewById(R.id.currentTitleTxt);
            titleView.setText(itemTitle);

            // Current item date
            String itemDate;
            String startDate = currentGameItem.getString("first_air_date");
            if (currentGameItem.getBoolean("in_production")) {
                itemDate = String.format("%s-", startDate.substring(0,4));
            } else {
                String endDate = currentGameItem.getString("last_air_date");
                itemDate = String.format("%S-%s", startDate.substring(0,4), endDate.substring(0,4));
            }
            TextView dateView = view.findViewById(R.id.gameItemDate);
            dateView.setText(itemDate);

            // Current runtime
            TextView runTimeView = view.findViewById(R.id.runTimeTxt);
            runTimeView.setVisibility(View.INVISIBLE);

            // Current item description
            String description = currentGameItem.getString("overview");
            TextView descriptionView = view.findViewById(R.id.descriptionTxt);
            descriptionView.setText(description);

            // Current item rating
            TextView ratingsView = view.findViewById(R.id.ratingsTxt);
            ratingsView.setVisibility(View.INVISIBLE);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }






}