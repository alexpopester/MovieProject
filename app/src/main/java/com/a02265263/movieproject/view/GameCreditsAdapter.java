package com.a02265263.movieproject.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import com.a02265263.movieproject.R;
import com.bumptech.glide.Glide;

public class GameCreditsAdapter extends RecyclerView.Adapter<GameCreditsAdapter.GameCreditsViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(GameItemModel item);
    }

    ObservableArrayList<GameItemModel> items;
    OnItemClickListener listener;
    Context context;

    public GameCreditsAdapter(ObservableArrayList<GameItemModel> items, Context context, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public GameCreditsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_credits_item, parent, false);
        return new GameCreditsViewHolder((ViewGroup) view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameCreditsAdapter.GameCreditsViewHolder holder, int position) {
        GameItemModel item = items.get(position);
        holder.getName().setText(item.getTitle());
        Glide.with(context).load(item.getImage()).into(holder.getImage());
        holder.getRole().setText(item.getRole());
        View view = holder.itemView.findViewById(R.id.actorConstraintView);
        view.setOnClickListener(v -> listener.onItemClick(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class GameCreditsViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView name;
        private final TextView role;

        public GameCreditsViewHolder(@NonNull ViewGroup parent) {
            super(parent);

            image = parent.findViewById(R.id.gameCreditsItemImage);
            name = parent.findViewById(R.id.actorName);
            role = parent.findViewById(R.id.actorRoleTextView);
        }

        public ImageView getImage() {
            return image;
        }

        public TextView getName() {
            return name;
        }

        public TextView getRole() {
            return role;
        }
    }
}
