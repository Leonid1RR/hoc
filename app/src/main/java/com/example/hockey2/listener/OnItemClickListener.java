package com.example.hockey2.listener;

import android.view.View;

@FunctionalInterface
public interface OnItemClickListener {
    void onItemClick(View view, Object data);
}
