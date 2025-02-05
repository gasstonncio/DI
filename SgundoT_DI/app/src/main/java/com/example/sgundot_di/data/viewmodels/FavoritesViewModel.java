package com.example.sgundot_di.data.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.sgundot_di.data.models.Game;
import com.example.sgundot_di.data.repositories.FavoritesRepository;
import java.util.List;

public class FavoritesViewModel extends ViewModel {
    private final FavoritesRepository repository;

    public FavoritesViewModel() {
        repository = new FavoritesRepository();
    }

    public LiveData<List<Game>> getFavoritesLiveData() {
        return repository.getFavoritesLiveData();
    }

    public LiveData<String> getErrorLiveData() {
        return repository.getErrorLiveData();
    }

    public void toggleFavorite(Game game) {
        repository.toggleFavorite(game);
    }
}
