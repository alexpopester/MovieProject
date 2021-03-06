package com.a02265263.movieproject.view;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.ObservableArrayList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameScreenFragment extends Fragment {
    RecyclerView recyclerView;
    TextView timerTextView;
    long startTime = 0;
    Handler timerHandler = new Handler();
    JSONObject currentGameItem;
    JSONObject currentTvCredits;
    List<GameItemModel> gameItemList;
    int id;
    boolean gameActive = false;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        GameScreenViewModel gameScreenViewModel = new ViewModelProvider(this).get(GameScreenViewModel.class).getInstance();

        View view = inflater.inflate(R.layout.fragment_game_screen, container, false);


        // Setting level number
        int level = LevelSelectViewModel.getLevel();
        TextView levelView = view.findViewById(R.id.levelTxt);
        levelView.setText("Level " + level);


        // Getting details about level
        String[] levelDetails = LevelSelectViewModel.getLevelDetails(level);
        String currentType = levelDetails[6];
        gameScreenViewModel.setEndID(Integer.parseInt(levelDetails[3]));
        id = Integer.parseInt(levelDetails[0]);
        if (!gameScreenViewModel.getGameActive()) {
            gameScreenViewModel.startGame(level);
            gameScreenViewModel.resetTimer();
            gameScreenViewModel.resetMoves();
        }
        else {
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                gameScreenViewModel.newMove();
                id = Integer.parseInt(bundle.getString("id"));
                if (gameScreenViewModel.checkIfEnd(id)){
                    gameScreenViewModel.endGame();
                    gameScreenViewModel.stopTimer();
                    NavHostFragment.findNavController(this).navigate(R.id.action_gameScreenFragment_to_levelCompleteFragment);
                    System.out.println("error");
                }
                currentType = bundle.getString("type");
            }
        }

        // Setting end goal text
        TextView endTitle = view.findViewById(R.id.endMovieTxt);
        endTitle.setText(levelDetails[4]);

        // Running timer
        TextView timerTextView = view.findViewById(R.id.timerTxt);
        gameScreenViewModel.startTimer(timerTextView);

        // Temporary button at the bottom
        view.findViewById(R.id.gameButton).setOnClickListener(button -> {
            gameScreenViewModel.endGame();
            NavHostFragment.findNavController(this).navigate(R.id.action_gameScreenFragment_to_scoreScreenFragment);
        });

        // Fetch the JSONObject, different call depending on type
        String finalCurrentType = currentType;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    switch (finalCurrentType) {
                        case "MOVIE":
                            currentGameItem = callJsonApi("https://api.themoviedb.org/3/movie/" + id + "?api_key=10216e19b889e6cba38a744f25087a68&language=en-US&append_to_response=release_dates,credits");
                            break;
                        case "PERSON":
                            currentGameItem = callJsonApi("https://api.themoviedb.org/3/person/" + id + "?api_key=10216e19b889e6cba38a744f25087a68&language=en-US&append_to_response=combined_credits");
                            break;
                        case "TV":
                            currentGameItem = callJsonApi("https://api.themoviedb.org/3/tv/" + id + "?api_key=10216e19b889e6cba38a744f25087a68&language=en-US&append_to_response=aggregate_credits");
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


        return view;
    }

    public static JSONObject callJsonApi(String url)
            throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            String jsonText = IOUtils.toString(is, "UTF-8");
            JSONObject json = new JSONObject(jsonText);
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
            if (itemDate.equals("")) {
                itemDate = "Unknown";
            }
            else {
                itemDate = itemDate.substring(0, 4);
            }
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

            // cast icons
             JSONObject temp2 = currentGameItem.getJSONObject("credits");
            JSONArray cast = temp2.getJSONArray("cast");
            ObservableArrayList<GameItemModel> castList = new ObservableArrayList<>();
            for (int j = 0; j < cast.length(); j++) {
                if (j < cast.length()) {
                    try {
                        JSONObject actor = cast.getJSONObject(j);
                        String name = actor.getString("name");
                        String role = actor.getString("character");
                        String imageUrl2 = actor.getString("profile_path");
                        String id = actor.getString("id");
                        Double popularity = actor.getDouble("popularity");
                        GameItemModel actorModel = new GameItemModel(id, name, imageUrl2, role, "person", popularity);
                        castList.add(actorModel);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            RecyclerView recyclerView = view.findViewById(R.id.recyclerViewGame);
            Bundle bundle = new Bundle();
            recyclerView.setAdapter(new GameCreditsAdapter(castList, getContext(), (actor) -> {
                bundle.putString("id", actor.getId());
                bundle.putString("type", actor.getType().toString());
                NavHostFragment.findNavController(this).navigate(R.id.action_gameScreenFragment_self, bundle);
            }));
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false));
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
            String birthDate = "Born: " + getDate(itemDate);
            TextView dateView = view.findViewById(R.id.gameItemDate);
            dateView.setText(birthDate);

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

            // get Movie credits for current person
            JSONObject credits = currentGameItem.getJSONObject("combined_credits");
            JSONArray cast = credits.getJSONArray("cast");
            ObservableArrayList<GameItemModel> movieCredits = new ObservableArrayList<>();
            ArrayList<String> listOfUsedIds = new ArrayList<>();
            System.out.println(cast.length());
            for (int i = 0; i < cast.length(); i++) {
                try {
                    JSONObject movie = cast.getJSONObject(i);
                    String id = movie.getString("id");
                    String title = "";
                    String date = "";
                    String type = movie.getString("media_type");
                    if (type.equals("movie")) {
                        title = movie.getString("title");
                        date = movie.getString("release_date");
                    }
                    else {
//                    if (title.equals("The Simpsons")){
//                        System.out.println("found");
//                    }
                        title = movie.getString("name");
                        date = movie.getString("first_air_date");
                    }
                    String imageUrl2 = "https://image.tmdb.org/t/p/w500/" + movie.getString("poster_path");
                    Double popularity = movie.getDouble("popularity");
                    if (!listOfUsedIds.contains(id)) {
                        GameItemModel movieModel = new GameItemModel(id, title, imageUrl2, getDate(date), type, popularity);
                        movieCredits.add(movieModel);
                        listOfUsedIds.add(id);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Collections.sort(movieCredits);
            RecyclerView recyclerView = view.findViewById(R.id.recyclerViewGame);
            Bundle bundle = new Bundle();
            recyclerView.setAdapter(new GameCreditsAdapter(movieCredits, getContext(), (movie) -> {
                bundle.putString("id", movie.getId());
                bundle.putString("type", movie.getType().toString());
                NavHostFragment.findNavController(this).navigate(R.id.action_gameScreenFragment_self, bundle);
            }));
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false));
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
            String seasons = currentGameItem.getString("number_of_seasons");
            if (Integer.parseInt(seasons) > 1) {
                seasons += " seasons";
            } else {
                seasons += " season";
            }
            TextView seasonsView = view.findViewById(R.id.runTimeTxt);
            seasonsView.setText(seasons);

            // Current item description
            String description = currentGameItem.getString("overview");
            TextView descriptionView = view.findViewById(R.id.descriptionTxt);
            descriptionView.setText(description);

            // Current item rating
            TextView ratingsView = view.findViewById(R.id.ratingsTxt);
            ratingsView.setVisibility(View.INVISIBLE);

            // current item cast credits
            JSONObject aggregateCredits = currentGameItem.getJSONObject("aggregate_credits");
            JSONArray cast = aggregateCredits.getJSONArray("cast");
            ObservableArrayList<GameItemModel> castList = new ObservableArrayList<>();
            for (int j = 0; j < cast.length(); j++) {
                if (j < cast.length()) {
                    try {
                        JSONObject actor = cast.getJSONObject(j);
                        String name = actor.getString("name");
                        JSONArray roles = actor.getJSONArray("roles");
                        JSONObject role = roles.getJSONObject(0);
                        String roleName = role.getString("character");
                        String imageUrl2 = actor.getString("profile_path");
                        String id = actor.getString("id");
                        Double popularity = actor.getDouble("popularity");
                        GameItemModel actorModel = new GameItemModel(id, name, imageUrl2, roleName, "person", popularity);
                        castList.add(actorModel);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            RecyclerView recyclerView = view.findViewById(R.id.recyclerViewGame);
            Bundle bundle = new Bundle();
            recyclerView.setAdapter(new GameCreditsAdapter(castList, getContext(), (actor) -> {
                bundle.putString("id", actor.getId());
                bundle.putString("type", actor.getType().toString());
                NavHostFragment.findNavController(this).navigate(R.id.action_gameScreenFragment_self, bundle);
            }));
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getDate(String date) {
        String[] dateParts = date.split("-");
        String finalDate = "";
        if (dateParts.length == 3) {
            switch (dateParts[1]) {
                case "01":
                    finalDate += "Jan ";
                    break;
                case "02":
                    finalDate += "Feb ";
                    break;
                case "03":
                    finalDate += "Mar ";
                    break;
                case "04":
                    finalDate += "Apr ";
                    break;
                case "05":
                    finalDate += "May ";
                    break;
                case "06":
                    finalDate += "Jun ";
                    break;
                case "07":
                    finalDate += "Jul ";
                    break;
                case "08":
                    finalDate += "Aug ";
                    break;
                case "09":
                    finalDate += "Sep ";
                    break;
                case "10":
                    finalDate += "Oct ";
                    break;
                case "11":
                    finalDate += "Nov ";
                    break;
                case "12":
                    finalDate += "Dec ";
                    break;
            }
            finalDate += dateParts[2] + ", " + dateParts[0];
        } else {
            finalDate = dateParts[0];
        }
        return finalDate;
    }





}