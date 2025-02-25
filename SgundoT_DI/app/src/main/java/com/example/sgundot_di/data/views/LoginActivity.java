package com.example.sgundot_di.data.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.sgundot_di.R;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth; // Manejo de autenticación con Firebase
    private SwitchMaterial swDarkMode; // Switch para activar/desactivar modo oscuro
    private SharedPreferences preferences; // Preferencias para almacenar el estado del modo oscuro
    private ImageView darkModeIcon; // Icono que indica el estado del modo oscuro

    // Método para actualizar el icono del modo oscuro
    private void updateDarkModeIcon(boolean isNightMode) {
        darkModeIcon.setImageResource(isNightMode ? R.drawable.ic_sun : R.drawable.ic_moon);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Cargar configuración de modo oscuro antes de inflar el layout
        preferences = getSharedPreferences("settings", MODE_PRIVATE);
        boolean isNightMode = preferences.getBoolean("night_mode", false);
        AppCompatDelegate.setDefaultNightMode(isNightMode ?
                AppCompatDelegate.MODE_NIGHT_YES :
                AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializamos las vistas
        swDarkMode = findViewById(R.id.swDarkMode);
        darkModeIcon = findViewById(R.id.darkModeIcon);
        mAuth = FirebaseAuth.getInstance();

        // Establecer estado inicial del switch y actualizar icono
        swDarkMode.setChecked(isNightMode);
        updateDarkModeIcon(isNightMode);

        // Manejar cambios de modo oscuro
        swDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("night_mode", isChecked);
            editor.apply();

            // Cambiar el tema de la aplicación
            AppCompatDelegate.setDefaultNightMode(isChecked ?
                    AppCompatDelegate.MODE_NIGHT_YES :
                    AppCompatDelegate.MODE_NIGHT_NO);

            // Actualizar el icono
            updateDarkModeIcon(isChecked);
        });

        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);
        TextView registerTextView = findViewById(R.id.registerTextView);

        // Evento del botón de inicio de sesión
        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // Autenticación con Firebase
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                Toast.makeText(LoginActivity.this, "Login exitoso", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Error en autenticación: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });

        // Evento para ir a la pantalla de registro
        registerTextView.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }
}
