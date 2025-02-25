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

    private final DatabaseReference favoritesRef; // Referencia a los juegos favoritos del usuario en Firebase
    private final DatabaseReference gamesRef; // Referencia a la colección general de juegos en Firebase
    private final MutableLiveData<List<Game>> favoriteGamesLiveData; // LiveData que almacena la lista de juegos favoritos

    public FavoritesRepository() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Si el usuario está autenticado, inicializamos las referencias en Firebase
        if (user != null) {
            favoritesRef = FirebaseDatabase.getInstance().getReference("favoritos").child(user.getUid());
            gamesRef = FirebaseDatabase.getInstance().getReference("juegos");
        } else {
            favoritesRef = null;
            gamesRef = null;
        }

        favoriteGamesLiveData = new MutableLiveData<>(); // Inicializamos LiveData
    }

    // Método para obtener la lista de juegos favoritos en forma de LiveData
    public LiveData<List<Game>> getFavoriteGamesLiveData() {
        return favoriteGamesLiveData;
    }

    // Método para cargar los juegos favoritos del usuario desde Firebase
    public void loadFavorites() {
        if (favoritesRef == null || gamesRef == null) return; // Verificamos que Firebase esté inicializado

        favoritesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Game> favoriteGames = new ArrayList<>();
                List<String> favoriteTitles = new ArrayList<>();

                /*
                 * Iteramos sobre cada nodo hijo en la referencia "favoritos" de Firebase,
                 * donde cada hijo representa un juego marcado como favorito por el usuario.
                 * - La clave del nodo es el título del juego.
                 * - El valor es un booleano que indica si está en favoritos.
                 */
                for (DataSnapshot favSnapshot : snapshot.getChildren()) {
                    String tituloJuego = favSnapshot.getKey(); // Obtenemos la clave del nodo (título del juego)
                    Boolean isFavorite = favSnapshot.getValue(Boolean.class); // Valor asociado al nodo

                    if (tituloJuego != null && isFavorite != null && isFavorite) {
                        favoriteTitles.add(tituloJuego); // Agregamos el título a la lista de favoritos
                    }
                }

                // 🔹 Ahora consultamos la colección de juegos en Firebase para obtener más detalles
                gamesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot gamesSnapshot) {
                        favoriteGames.clear(); // Limpiamos la lista antes de actualizarla

                        /*
                         * Iteramos sobre la lista de juegos en Firebase y verificamos
                         * cuáles están en la lista de títulos favoritos obtenida previamente.
                         */
                        for (DataSnapshot gameSnapshot : gamesSnapshot.getChildren()) {
                            String titulo = gameSnapshot.child("titulo").getValue(String.class);

                            if (titulo != null && favoriteTitles.contains(titulo)) {
                                String descripcion = gameSnapshot.child("descripcion").getValue(String.class);
                                String imagen = gameSnapshot.child("imagen").getValue(String.class);

                                // Si los datos no son nulos, creamos un objeto Game y lo agregamos a la lista
                                if (descripcion != null && imagen != null) {
                                    Game game = new Game(titulo, titulo, descripcion, imagen, true);
                                    favoriteGames.add(game);
                                }
                            }
                        }

                        favoriteGamesLiveData.setValue(favoriteGames); // 🔹 Actualizamos el LiveData con los juegos favoritos
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        favoriteGamesLiveData.setValue(null); // En caso de error, actualizamos con `null`
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                favoriteGamesLiveData.setValue(null); // 🔹 Si Firebase cancela la solicitud, actualizamos LiveData con `null`
            }
        });
    }

    // Método para agregar un juego a la lista de favoritos del usuario
    public void agregarAFavoritos(Game game) {
        if (favoritesRef == null) return;

        favoritesRef.child(game.getTitulo()).setValue(true)
                .addOnFailureListener(e -> favoriteGamesLiveData.setValue(null));
    }

    // Método para eliminar un juego de la lista de favoritos del usuario
    public void eliminarDeFavoritos(Game game) {
        if (favoritesRef == null) return;

        favoritesRef.child(game.getTitulo()).removeValue()
                .addOnFailureListener(e -> favoriteGamesLiveData.setValue(null));
    }
}
