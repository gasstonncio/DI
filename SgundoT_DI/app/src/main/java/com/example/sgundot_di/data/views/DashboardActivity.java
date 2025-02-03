package com.example.sgundot_di.data.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sgundot_di.R;
import com.example.sgundot_di.data.adapter.GameAdapter;
import com.example.sgundot_di.data.models.Game;
import com.example.sgundot_di.data.viewmodels.DashboardViewModel;

public class DashboardActivity extends AppCompatActivity implements GameAdapter.OnGameClickListener {

    private DashboardViewModel viewModel;
    private GameAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Inicializar ViewModel
        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        // Configurar RecyclerView
        RecyclerView recyclerView = findViewById(R.id.gamesRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 columnas
        adapter = new GameAdapter(this);
        recyclerView.setAdapter(adapter);

        // Configurar botón de logout
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            viewModel.logoutUser();
            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
            finish();
        });

        // Observar cambios en los datos
        viewModel.getGamesLiveData().observe(this, games -> {
            adapter.setGames(games);
        });

        // Observar errores
        viewModel.getErrorLiveData().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, "Error: " + error, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onGameClick(Game game) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_TITULO, game.getTítulo());
        intent.putExtra(DetailActivity.EXTRA_DESCRIPCION, game.getDescripción());
        intent.putExtra(DetailActivity.EXTRA_IMAGEN, game.getImagen());
        startActivity(intent);
    }
}