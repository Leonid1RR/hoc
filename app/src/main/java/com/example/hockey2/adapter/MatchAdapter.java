package com.example.hockey2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hockey2.Match;
import com.example.hockey2.R;
import com.example.hockey2.listener.OnItemClickListener;

import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchNamesViewHolder> {

    private final List<Match> matches;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MatchAdapter(List<Match> matches) {
        this.matches = matches;
    }

    @NonNull
    @Override
    public MatchNamesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_match, parent, false);
        return new MatchNamesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchNamesViewHolder holder, int position) {
        Match match = matches.get(position);

        holder.bind(match);
        holder.matchLayout.setOnClickListener(v -> {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(v, match);
        });
    }

    @Override
    public int getItemCount() {
        return  matches.size();
    }

    public static class MatchNamesViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout matchLayout;
        TextView matchName;

        public MatchNamesViewHolder(@NonNull View itemView) {
            super(itemView);

            matchLayout   = itemView.findViewById(R.id.creatorLayout);
            matchName     = itemView.findViewById(R.id.name);
        }

        public void bind(Match match) {
            if (match.getMatchName() != null) matchName.setText(match.getMatchName());
            else matchName.setText("Empty");
        }
    }
}
