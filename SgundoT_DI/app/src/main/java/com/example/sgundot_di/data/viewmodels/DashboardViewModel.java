package com.example.sgundot_di.data.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.sgundot_di.data.models.Game;
import com.example.sgundot_di.data.repositories.DashboardRepository;
import com.example.sgundot_di.data.repositories.UserRepository;
import java.util.List;

public class DashboardViewModel extends ViewModel {
    private final DashboardRepository dashboardRepository;
    private final UserRepository userRepository;

    public DashboardViewModel() {
        dashboardRepository = new DashboardRepository();
        userRepository = new UserRepository();
    }

    public LiveData<List<Game>> getGamesLiveData() {
        return dashboardRepository.getGamesLiveData();
    }

    public LiveData<String> getErrorLiveData() {
        return dashboardRepository.getErrorLiveData();
    }

    public void logoutUser() {
        userRepository.logoutUser();
    }

    public boolean isUserLoggedIn() {
        return userRepository.isUserLoggedIn();
    }
}