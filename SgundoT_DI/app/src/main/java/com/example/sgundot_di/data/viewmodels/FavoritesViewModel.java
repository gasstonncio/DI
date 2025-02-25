package com.example.sgundot_di.data.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.sgundot_di.data.models.Game;
import com.example.sgundot_di.data.repositories.FavoritesRepository;
import java.util.List;

public class FavoritesViewModel extends ViewModel {
    private final FavoritesRepository repository; // Repositorio que maneja los datos de favoritos
    private final LiveData<List<Game>> favoriteGamesLiveData; // LiveData que contiene la lista de juegos favoritos

    public FavoritesViewModel() {
        repository = new FavoritesRepository(); // Inicialización del repositorio
        favoriteGamesLiveData = repository.getFavoriteGamesLiveData(); // Obtenemos los juegos favoritos desde el repositorio
        repository.loadFavorites(); // 🔹 Carga la lista de favoritos al iniciar el ViewModel
    }

    // Método para obtener la lista de juegos favoritos en forma de LiveData
    public LiveData<List<Game>> getFavoriteGamesLiveData() {
        return favoriteGamesLiveData;
    }

    // Método para agregar un juego a la lista de favoritos
    public void agregarAFavoritos(Game game) {
        repository.agregarAFavoritos(game);
    }

    // Método para eliminar un juego de la lista de favoritos
    public void eliminarDeFavoritos(Game game) {
        repository.eliminarDeFavoritos(game);
    }
}
