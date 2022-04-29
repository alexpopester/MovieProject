package com.a02265263.movieproject.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a02265263.movieproject.R;
import com.a02265263.movieproject.view.autoScrollRecyclerView.AutoScrollAdapter;
import com.a02265263.movieproject.view.autoScrollRecyclerView.NoScrollRecyclerView;
import com.a02265263.movieproject.viewmodel.StartScreenViewModel;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.IOUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class StartScreenFragment extends Fragment {
    private static final float MILLISECONDS_PER_INCH = 6000f;
    ArrayList<String> popMoviesTop;
    ArrayList<String> popMoviesBottom;
    NoScrollRecyclerView recyclerViewTop;
    NoScrollRecyclerView recyclerViewBottom;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        StartScreenViewModel startScreenViewModel = new ViewModelProvider(this).get(StartScreenViewModel.class);

        View view = inflater.inflate(R.layout.fragment_start_screen, container,false);

        recyclerViewTop = (NoScrollRecyclerView) view.findViewById(R.id.popMoviesTop);
        recyclerViewBottom = (NoScrollRecyclerView) view.findViewById(R.id.popMoviesBottom);

        view.findViewById(R.id.startGameButton).setOnClickListener(button -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_startScreenFragment_to_levelSelectFragment);
        });

        popMoviesTop = new ArrayList<>();
        popMoviesBottom = new ArrayList<>();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject tmpMoviesTop = callJsonApi("https://api.themoviedb.org/3/movie/popular?api_key=10216e19b889e6cba38a744f25087a68&language=en-US&page=1");
                    JSONObject tmpMoviesBottom = callJsonApi("https://api.themoviedb.org/3/movie/popular?api_key=10216e19b889e6cba38a744f25087a68&language=en-US&page=2");

                    JSONArray tmpMoviesTopJSONArray = tmpMoviesTop.getJSONArray("results");
                    JSONArray tmpMoviesBottomJSONArray = tmpMoviesBottom.getJSONArray("results");

                    for (int i = 0; i < tmpMoviesTopJSONArray.length(); i++) {
                        JSONObject current = (JSONObject) tmpMoviesTopJSONArray.get(i);
                        String imageURL = "https://image.tmdb.org/t/p/w500/" + current.getString("poster_path");
                        popMoviesTop.add(imageURL);
                    }
                    for (int i = 0; i < tmpMoviesBottomJSONArray.length(); i++) {
                        JSONObject current = (JSONObject) tmpMoviesBottomJSONArray.get(i);
                        String imageURL = "https://image.tmdb.org/t/p/w500/" + current.getString("poster_path");
                        popMoviesBottom.add(imageURL);
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setRecyclerView(recyclerViewTop, popMoviesTop, false);
        setRecyclerView(recyclerViewBottom, popMoviesBottom, true);



        return view;
    }

    public static JSONObject callJsonApi(String url)
            throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            String jsonText = IOUtils.toString(is, "UTF-8");
            JSONObject json = new JSONObject(jsonText);
//            System.out.println(json.toString(4));
            return json;
        } finally {
            is.close();
        }
    }

    private void setRecyclerView(RecyclerView recyclerView, ArrayList<String> images, Boolean reverse) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, reverse);
        recyclerView.setLayoutManager(layoutManager);

        AutoScrollAdapter autoScrollAdapter = new AutoScrollAdapter(this.getContext(), images);
        recyclerView.setAdapter(autoScrollAdapter);

        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {
            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
            }
        };
        linearSmoothScroller.setTargetPosition(autoScrollAdapter.getItemCount());
        layoutManager.startSmoothScroll(linearSmoothScroller);

    }

}