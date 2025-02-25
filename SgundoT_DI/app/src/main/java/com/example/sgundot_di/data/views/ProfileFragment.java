package com.example.sgundot_di.data.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import com.example.sgundot_di.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    private EditText newPasswordEditText;
    private Switch darkModeSwitch;
    private FirebaseAuth auth;

    public ProfileFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflamos la vista del fragmento de perfil
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Inicializamos las vistas
        newPasswordEditText = view.findViewById(R.id.newPasswordEditText);
        darkModeSwitch = view.findViewById(R.id.darkModeSwitch);
        Button changePasswordButton = view.findViewById(R.id.changePasswordButton);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        // Configuración inicial del switch de modo oscuro basado en preferencias
        SharedPreferences prefs = requireActivity().getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
        boolean isDarkMode = prefs.getBoolean("darkMode", false);
        darkModeSwitch.setChecked(isDarkMode);

        // Listener para cambiar la contraseña del usuario
        changePasswordButton.setOnClickListener(v -> {
            String newPassword = newPasswordEditText.getText().toString().trim();

            if (newPassword.isEmpty() || newPassword.length() < 6) {
                Toast.makeText(getContext(), "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                return;
            }

            if (user != null) {
                user.updatePassword(newPassword)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(getContext(), "Contraseña actualizada con éxito", Toast.LENGTH_SHORT).show();
                            newPasswordEditText.setText("");
                        })
                        .addOnFailureListener(e -> Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });

        // Listener para alternar el modo oscuro
        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            toggleDarkMode(isChecked);
        });

        return view;
    }

    // Método para activar/desactivar el modo oscuro
    private void toggleDarkMode(boolean enableDarkMode) {
        SharedPreferences prefs = requireActivity().getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("darkMode", enableDarkMode).apply();

        if (enableDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // Recreamos la actividad para aplicar los cambios
        requireActivity().recreate();
    }
}
