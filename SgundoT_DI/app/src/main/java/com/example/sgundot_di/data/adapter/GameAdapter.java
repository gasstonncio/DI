package com.example.sgundot_di.data.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sgundot_di.R;
import com.example.sgundot_di.data.models.Game;
import com.squareup.picasso.Picasso;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

    private final List<Game> gameList;
    private final OnItemClickListener clickListener;

    public interface OnItemClickListener {
        void onItemClick(Game game);
    }

    public GameAdapter(List<Game> gameList, OnItemClickListener clickListener) {
        this.gameList = gameList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        Game game = gameList.get(position);
        holder.bind(game, clickListener);
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public static class GameViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final ImageView gameImageView;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.gameTitle);
            gameImageView = itemView.findViewById(R.id.gameImage);
        }

        public void bind(Game game, OnItemClickListener clickListener) {
            titleTextView.setText(game.getTitulo());
            Picasso.get().load(game.getImagen()).into(gameImageView);

            itemView.setOnClickListener(v -> clickListener.onItemClick(game));
        }
    }
}
