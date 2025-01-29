package com.example.sgundot_di.data.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.sgundot_di.data.models.User;
import com.example.sgundot_di.data.repositories.UserRepository;

public class LoginViewModel extends ViewModel {
    private final UserRepository userRepository;

    public LoginViewModel() {
        userRepository = new UserRepository();
    }

    public void loginUser(String email, String password) {
        userRepository.loginUser(email, password);
    }

    public LiveData<User> getUserLiveData() {
        return userRepository.getUserLiveData();
    }

    public LiveData<String> getErrorLiveData() {
        return userRepository.getErrorLiveData();
    }

    public boolean isUserLoggedIn() {
        return userRepository.isUserLoggedIn();
    }
}