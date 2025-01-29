package com.example.sgundot_di.data.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.sgundot_di.data.models.User;
import com.example.sgundot_di.data.repositories.UserRepository;

public class RegisterViewModel extends ViewModel {
    private final UserRepository userRepository;

    public RegisterViewModel() {
        userRepository = new UserRepository();
    }

    public void registerUser(String email, String password, String username) {
        userRepository.registerUser(email, password, username);
    }

    public LiveData<User> getUserLiveData() {
        return userRepository.getUserLiveData();
    }

    public LiveData<String> getErrorLiveData() {
        return userRepository.getErrorLiveData();
    }

    // Método para validar los datos del registro
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