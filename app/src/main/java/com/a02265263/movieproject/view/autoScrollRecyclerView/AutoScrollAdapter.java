package com.a02265263.movieproject.view.autoScrollRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.a02265263.movieproject.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AutoScrollAdapter extends RecyclerView.Adapter<AutoScrollAdapter.AutoScrollViewHolder> {
    private ArrayList<String> images;
    private Context context;

    public AutoScrollAdapter(Context context, ArrayList<String> images) {
        this.images = images;
        this.context = context;
    }

    @NonNull
    @Override
    public AutoScrollViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AutoScrollViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.auto_scroll_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull AutoScrollViewHolder holder, int position) {
        int realPosition = position % images.size();
        Glide.with(context)
                .load(images.get(realPosition))
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    class AutoScrollViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        public AutoScrollViewHolder (@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.autoScrollImage);
        }
    }
}
