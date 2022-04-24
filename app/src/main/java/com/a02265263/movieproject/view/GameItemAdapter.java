package com.a02265263.movieproject.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.a02265263.movieproject.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class GameItemAdapter extends RecyclerView.Adapter<GameItemAdapter.GameItemViewHolder> {

    private Context mContext;
    private List<GameItemModel> mData;

    public GameItemAdapter(Context mContext, List<GameItemModel> mData) {
        this.mContext= mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public GameItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GameItemViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.game_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull GameItemViewHolder holder, int position) {
        holder.title.setText(mData.get(position).getTitle());
        holder.role.setText(mData.get(position).getRole());

        Glide.with(mContext)
                .load(mData.get(position).getImage())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class GameItemViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView role;
        ImageView image;

        public GameItemViewHolder (@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.gameItemTitle);
            role = itemView.findViewById(R.id.gameItemRole);
            image = itemView.findViewById(R.id.gameItemImage);
        }
    }
}
