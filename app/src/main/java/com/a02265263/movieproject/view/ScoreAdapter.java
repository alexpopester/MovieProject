package com.a02265263.movieproject.view;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
        if (score.getRanking() == 1) {
            holder.getMedal().setImageResource(R.drawable.gold);
            holder.getStanding().setText("\t\t");
        }
        else if (score.getRanking() == 2) {
            holder.getMedal().setImageResource(R.drawable.silver);
            holder.getStanding().setText("\t\t");
        }
        else if (score.getRanking() == 3) {
            holder.getMedal().setImageResource(R.drawable.bronze);
            holder.getStanding().setText("\t\t");
        }
        else {
            holder.getMedal().setImageDrawable(null);
            holder.getStanding().setText("" + score.getRanking() + ".");
        }
        holder.getName().setText(score.getName());
        holder.getMoves().setText("" + score.getMoves());
        long time = score.getTime();
        long minutes = (long) time / (60*1000);
        long seconds = (long) (time / 1000) % 60;
        long milliseconds = (long) (time / 100) % 10;
        holder.getTime().setText(String.format("%02d:%02d.%02d", minutes, seconds, milliseconds));



    }

    @Override
    public int getItemCount() {
        return scores.size();
    }
    class ScoreViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView moves;
        private TextView time;
        private TextView standing;
        private ImageView medal;


        public ScoreViewHolder(@NonNull View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.nameTextView);
            moves = itemView.findViewById(R.id.movesTextView);
            time = itemView.findViewById(R.id.timeTextView);
            standing = itemView.findViewById(R.id.standingTextView);
            medal = itemView.findViewById(R.id.scoreScreenImageView);
        }

        public TextView getMoves() {
            return moves;
        }

        public TextView getTime() {
            return time;
        }

        public TextView getStanding() {
            return standing;
        }

        public TextView getName() {
            return name;
        }

        public ImageView getMedal() {
            return medal;
        }
    }
}
