package com.example.sgundot_di.data.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.sgundot_di.data.models.User;
import com.example.sgundot_di.data.repositories.UserRepository;

public class LoginViewModel extends ViewModel {
    private final UserRepository userRepository; // Repositorio que maneja los datos del usuario

    public LoginViewModel() {
        userRepository = new UserRepository(); // Inicialización del repositorio
    }

    // Método para iniciar sesión con email y contraseña
    public void loginUser(String email, String password) {
        userRepository.loginUser(email, password);
    }

    // Método para obtener los datos del usuario en forma de LiveData
    public LiveData<User> getUserLiveData() {
        return userRepository.getUserLiveData();
    }

    // Método para obtener mensajes de error en la autenticación
    public LiveData<String> getErrorLiveData() {
        return userRepository.getErrorLiveData();
    }

    // Método para verificar si el usuario ya está autenticado
    public boolean isUserLoggedIn() {
        return userRepository.isUserLoggedIn();
    }
}
