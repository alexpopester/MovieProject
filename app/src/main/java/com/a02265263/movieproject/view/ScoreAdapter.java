package com.a02265263.movieproject.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;

import com.a02265263.movieproject.R;
import com.a02265263.movieproject.model.Score;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {

    public interface OnScoreClick {
        public void onClick(Score score);
    }

    ObservableArrayList<Score> scores;
    OnScoreClick eventListener;

    public ScoreAdapter(
            ObservableArrayList<Score> scores,
            OnScoreClick eventListener
    ) {
        this.scores = scores;
        this.eventListener = eventListener;
        scores.addOnListChangedCallback(new ObservableList.OnListChangedCallback() {
            @Override
            public void onChanged(ObservableList sender) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
                notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
                notifyItemRangeRemoved(positionStart, itemCount);
            }
        });
    }

    @NonNull
    @Override
    public ScoreAdapter.ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScoreViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.score_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreAdapter.ScoreViewHolder holder, int position) {
        Score score = scores.get(position);


    }

    @Override
    public int getItemCount() {
        return scores.size();
    }
    class ScoreViewHolder extends RecyclerView.ViewHolder {

        public ScoreViewHolder(@NonNull View itemView){
            super(itemView);
        }
    }
}
