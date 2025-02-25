package com.example.sgundot_di.data.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.sgundot_di.data.models.Game;
import com.example.sgundot_di.data.repositories.DashboardRepository;
import java.util.List;

public class DashboardViewModel extends ViewModel {

    private final DashboardRepository repository; // Repositorio que maneja los datos del dashboard
    private final LiveData<List<Game>> juegosLiveData; // LiveData que contiene la lista de juegos disponibles
    private final LiveData<String> errorLiveData; // LiveData para manejar los errores

    public DashboardViewModel() {
        repository = new DashboardRepository(); // Inicialización del repositorio
        juegosLiveData = repository.getJuegosLiveData(); // Obtenemos la lista de juegos
        errorLiveData = repository.getErrorLiveData(); // Obtenemos los mensajes de error
        repository.cargarJuegos(); // 🔹 Cargar los juegos desde Firebase al iniciar
    }

    // Método para obtener la lista de juegos en forma de LiveData
    public LiveData<List<Game>> getJuegosLiveData() {
        return juegosLiveData;
    }

    // Método para obtener mensajes de error en la carga de datos
    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }
}