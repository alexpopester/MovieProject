package com.a02265263.movieproject.view.autoScrollRecyclerView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoScrollRecyclerView extends RecyclerView {
    public NoScrollRecyclerView(@NonNull Context context) {
        super(context);
    }

    public NoScrollRecyclerView(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollRecyclerView(@NonNull Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (getLayoutManager() != null && getLayoutManager().isSmoothScrolling()) {
            return true;
        }
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (getLayoutManager() != null && getLayoutManager().isSmoothScrolling()) {
            return true;
        }
        return super.onTouchEvent(e);
    }
}
