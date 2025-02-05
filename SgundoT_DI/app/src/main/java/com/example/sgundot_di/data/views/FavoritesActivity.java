package com.example.sgundot_di.data.views;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;
import com.example.sgundot_di.R;
import com.example.sgundot_di.data.adapter.GameAdapter;
import com.example.sgundot_di.data.models.Game;
import com.example.sgundot_di.data.viewmodels.FavoritesViewModel;

public class FavoritesActivity extends AppCompatActivity implements GameAdapter.OnGameClickListener {
    private FavoritesViewModel viewModel;
    private GameAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        // Inicializar ViewModel
        viewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);

        // Configurar RecyclerView
        RecyclerView recyclerView = findViewById(R.id.favoritesRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new GameAdapter(this);
        recyclerView.setAdapter(adapter);

        // Observar cambios en los favoritos
        viewModel.getFavoritesLiveData().observe(this, games -> {
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
        // Igual que en DashboardActivity
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_TITULO, game.getTitulo());
        intent.putExtra(DetailActivity.EXTRA_DESCRIPCION, game.getDescripcion());
        intent.putExtra(DetailActivity.EXTRA_IMAGEN, game.getImagen());
        startActivity(intent);
    }
}
