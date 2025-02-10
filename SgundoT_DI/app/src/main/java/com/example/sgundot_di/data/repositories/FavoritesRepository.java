package com.example.sgundot_di.data.repositories;

import androidx.lifecycle.MutableLiveData;
import com.example.sgundot_di.data.models.Game;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class FavoritesRepository {
    private final DatabaseReference favoritesRef;
    private final FirebaseAuth auth;
    private final MutableLiveData<List<Game>> favoritesLiveData;
    private final MutableLiveData<String> errorLiveData;

    public FavoritesRepository() {
        auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        favoritesRef = database.getReference("users");

        favoritesLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();

        if (auth.getCurrentUser() != null) {
            loadFavorites();
        }
    }

    // Cargar juegos favoritos
    private void loadFavorites() {
        String userId = auth.getCurrentUser().getUid();
        favoritesRef.child(userId).child("favoritos")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Game> favoritesList = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Game game = snapshot.getValue(Game.class);
                            if (game != null) {
                                game.setFavorite(true);  // Marcar el juego como favorito
                                favoritesList.add(game);
                            }
                        }

                        favoritesLiveData.setValue(favoritesList);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        errorLiveData.setValue(databaseError.getMessage());
                    }
                });
    }

    // Guardar o eliminar un juego de favoritos
    public void toggleFavorite(Game game) {
        if (auth.getCurrentUser() == null) {
            errorLiveData.setValue("Usuario no autenticado");
            return;
        }

        String userId = auth.getCurrentUser().getUid();
        DatabaseReference userFavoritesRef = favoritesRef.child(userId).child("favoritos").child(game.getId());

        if (game.isFavorite()) {
            // Añadir juego a favoritos
            userFavoritesRef.setValue(game)
                    .addOnFailureListener(e -> errorLiveData.setValue(e.getMessage()));
        } else {
            // Eliminar juego de favoritos
            userFavoritesRef.removeValue()
                    .addOnFailureListener(e -> errorLiveData.setValue(e.getMessage()));
        }
    }

    // Métodos para obtener los datos
    public MutableLiveData<List<Game>> getFavoritesLiveData() {
        return favoritesLiveData;
    }

    public MutableLiveData<String> getErrorLiveData() {
        return errorLiveData;
    }
}
