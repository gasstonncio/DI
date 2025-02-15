package com.example.sgundot_di.data.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.sgundot_di.data.models.Game;
import com.example.sgundot_di.data.repositories.FavoritesRepository;
import java.util.List;

public class FavoritesViewModel extends ViewModel {
    private final FavoritesRepository repository;
    private final LiveData<List<Game>> favoriteGamesLiveData;

    public FavoritesViewModel() {
        repository = new FavoritesRepository();
        favoriteGamesLiveData = repository.getFavoriteGamesLiveData();
        repository.loadFavorites(); // 🔹 Carga la lista de favoritos al iniciar
    }

    public LiveData<List<Game>> getFavoriteGamesLiveData() {
        return favoriteGamesLiveData;
    }

    public void agregarAFavoritos(Game game) {
        repository.agregarAFavoritos(game);
    }

    public void eliminarDeFavoritos(Game game) { // 🔹 Nuevo método para eliminar favoritos
        repository.eliminarDeFavoritos(game);
    }
}
