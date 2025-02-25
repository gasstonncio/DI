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

    private final List<Game> gameList; // Lista de juegos a mostrar en el RecyclerView
    private final OnItemClickListener clickListener; // Listener para manejar clics en los elementos

    // Interfaz para manejar el evento de clic en un ítem del RecyclerView
    public interface OnItemClickListener {
        void onItemClick(Game game);
    }

    // Constructor para inicializar la lista de juegos y el listener de clics
    public GameAdapter(List<Game> gameList, OnItemClickListener clickListener) {
        this.gameList = gameList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflamos el layout del ítem del RecyclerView desde el XML
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        // Obtenemos el juego en la posición actual y lo vinculamos a la vista
        Game game = gameList.get(position);
        holder.bind(game, clickListener);
    }

    @Override
    public int getItemCount() {
        return gameList.size(); // Retorna el número de elementos en la lista
    }

    // ViewHolder que almacena las vistas de cada elemento del RecyclerView
    public static class GameViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final ImageView gameImageView;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            // Referencias a las vistas dentro del ítem del RecyclerView
            titleTextView = itemView.findViewById(R.id.gameTitle);
            gameImageView = itemView.findViewById(R.id.gameImage);
        }

        // Método para asociar los datos del juego con la vista
        public void bind(Game game, OnItemClickListener clickListener) {
            titleTextView.setText(game.getTitulo()); // Asignamos el título del juego al TextView
            Picasso.get().load(game.getImagen()).into(gameImageView); // Cargamos la imagen con Picasso

            // Configuramos el clic en el ítem para abrir los detalles del juego
            itemView.setOnClickListener(v -> clickListener.onItemClick(game));
        }
    }
}