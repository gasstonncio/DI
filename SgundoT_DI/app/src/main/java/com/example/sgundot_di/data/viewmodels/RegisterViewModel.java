package com.example.sgundot_di.data.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.sgundot_di.data.models.User;
import com.example.sgundot_di.data.repositories.UserRepository;

public class RegisterViewModel extends ViewModel {
    private final UserRepository userRepository; // Repositorio que maneja el registro de usuarios

    public RegisterViewModel() {
        userRepository = new UserRepository(); // Inicialización del repositorio
    }

    // Método para registrar un nuevo usuario con email, contraseña y nombre de usuario
    public void registerUser(String email, String password, String username) {
        userRepository.registerUser(email, password, username);
    }

    // Método para obtener los datos del usuario en forma de LiveData
    public LiveData<User> getUserLiveData() {
        return userRepository.getUserLiveData();
    }

    // Método para obtener mensajes de error en el registro
    public LiveData<String> getErrorLiveData() {
        return userRepository.getErrorLiveData();
    }

    // Método para validar los datos ingresados antes del registro
    public boolean validateRegistrationData(String email, String password, String username) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        if (password == null || password.length() < 6) {
            return false;
        }
        return username != null && !username.trim().isEmpty();
    }
}
