package com.example.sgundot_di.data.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.sgundot_di.data.models.Game;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class FavoritesRepository {

    private final DatabaseReference favoritesRef;
    private final DatabaseReference gamesRef;
    private final MutableLiveData<List<Game>> favoriteGamesLiveData;

    public FavoritesRepository() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            favoritesRef = FirebaseDatabase.getInstance().getReference("favoritos").child(user.getUid());
            gamesRef = FirebaseDatabase.getInstance().getReference("juegos");
        } else {
            favoritesRef = null;
            gamesRef = null;
        }
        favoriteGamesLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Game>> getFavoriteGamesLiveData() {
        return favoriteGamesLiveData;
    }

    public void loadFavorites() {
        if (favoritesRef == null || gamesRef == null) return;

        favoritesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Game> favoriteGames = new ArrayList<>();
                List<String> favoriteTitles = new ArrayList<>();

                // 🔹 Obtener los títulos de los juegos favoritos
                for (DataSnapshot favSnapshot : snapshot.getChildren()) {
                    String tituloJuego = favSnapshot.getKey(); // 🔹 La clave es el título en Firebase
                    Boolean isFavorite = favSnapshot.getValue(Boolean.class);

                    if (tituloJuego != null && isFavorite != null && isFavorite) {
                        favoriteTitles.add(tituloJuego);
                    }
                }

                // 🔹 Buscar los detalles de cada juego en la base de datos "juegos"
                gamesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot gamesSnapshot) {
                        favoriteGames.clear(); // 🔹 Limpiar antes de agregar nuevos juegos

                        for (DataSnapshot gameSnapshot : gamesSnapshot.getChildren()) {
                            String titulo = gameSnapshot.child("titulo").getValue(String.class);

                            if (titulo != null && favoriteTitles.contains(titulo)) {
                                String descripcion = gameSnapshot.child("descripcion").getValue(String.class);
                                String imagen = gameSnapshot.child("imagen").getValue(String.class);

                                if (descripcion != null && imagen != null) {
                                    Game game = new Game(titulo, titulo, descripcion, imagen, true);
                                    favoriteGames.add(game);
                                }
                            }
                        }

                        favoriteGamesLiveData.setValue(favoriteGames); // 🔹 Actualizar LiveData
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // 🔹 Manejo de error si ocurre un problema
                        favoriteGamesLiveData.setValue(null);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 🔹 Si Firebase cancela la solicitud, actualizamos LiveData con `null`
                favoriteGamesLiveData.setValue(null);
            }
        });
    }

    public void agregarAFavoritos(Game game) {
        if (favoritesRef == null) return;

        favoritesRef.child(game.getTitulo()).setValue(true)
                .addOnFailureListener(e -> favoriteGamesLiveData.setValue(null));
    }

    public void eliminarDeFavoritos(Game game) {
        if (favoritesRef == null) return;

        favoritesRef.child(game.getTitulo()).removeValue()
                .addOnFailureListener(e -> favoriteGamesLiveData.setValue(null));
    }

}
