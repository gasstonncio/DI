package com.example.sgundot_di.data.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sgundot_di.R;
import com.example.sgundot_di.databinding.ActivitySplashBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding; // ViewBinding para acceder a la UI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 🔹 Aplicamos una animación de fade-in al logo y texto de bienvenida
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(2000);
        binding.logoImage.startAnimation(fadeIn);
        binding.welcomeText.startAnimation(fadeIn);

        // 🔹 Esperamos 3 segundos antes de redirigir al usuario a la siguiente pantalla
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class)); // Usuario autenticado
            } else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class)); // Usuario no autenticado
            }
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out); // 🔹 Transición suave
            finish(); // Cerramos esta actividad para que no se pueda volver atrás
        }, 3000);
    }
}
