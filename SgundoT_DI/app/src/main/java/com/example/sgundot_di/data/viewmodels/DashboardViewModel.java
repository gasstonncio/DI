package com.example.sgundot_di.data.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.sgundot_di.data.models.Game;
import com.example.sgundot_di.data.repositories.DashboardRepository;
import java.util.List;

public class DashboardViewModel extends ViewModel {

    private final DashboardRepository repository;
    private final LiveData<List<Game>> juegosLiveData;
    private final LiveData<String> errorLiveData;

    public DashboardViewModel() {
        repository = new DashboardRepository();
        juegosLiveData = repository.getJuegosLiveData();
        errorLiveData = repository.getErrorLiveData();
        repository.cargarJuegos();
    }

    public LiveData<List<Game>> getJuegosLiveData() {
        return juegosLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }
}
