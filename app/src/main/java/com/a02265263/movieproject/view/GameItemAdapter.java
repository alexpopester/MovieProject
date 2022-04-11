package com.a02265263.movieproject.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.a02265263.movieproject.R;

public class GameItemAdapter extends RecyclerView.Adapter<GameItemAdapter.GameItemViewHolder> {
    @NonNull
    @Override
    public GameItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GameItemViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.game_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull GameItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class GameItemViewHolder extends RecyclerView.ViewHolder {

        public GameItemViewHolder (@NonNull View itemView) {
            super(itemView);
        }
    }
}
