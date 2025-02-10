package com.example.sgundot_di.data.views;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.example.sgundot_di.R;
import com.example.sgundot_di.data.models.Game;
import com.example.sgundot_di.data.viewmodels.FavoritesViewModel;
import android.util.Log;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_TITULO = "extra_titulo";
    public static final String EXTRA_DESCRIPCION = "extra_descripcion";
    public static final String EXTRA_IMAGEN = "extra_imagen";
    public static final String EXTRA_ID = "extra_id"; // Nuevo extra

    private FavoritesViewModel favoritesViewModel;
    private FloatingActionButton favoriteFab;
    private Game currentGame;



    // Dentro de la clase DetailActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Inicializar ViewModel
        favoritesViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);

        // Obtener los datos del Intent
        String id = getIntent().getStringExtra(EXTRA_ID);
        String titulo = getIntent().getStringExtra(EXTRA_TITULO);
        String descripcion = getIntent().getStringExtra(EXTRA_DESCRIPCION);
        String imagen = getIntent().getStringExtra(EXTRA_IMAGEN);

        // Verificación de los datos recibidos
        Log.d("DetailActivity", "ID: " + id);
        Log.d("DetailActivity", "Titulo: " + titulo);
        Log.d("DetailActivity", "Descripcion: " + descripcion);
        Log.d("DetailActivity", "Imagen: " + imagen);

        // Comprobar si alguno de los datos es null
        if (id == null || titulo == null || descripcion == null || imagen == null) {
            Log.e("DetailActivity", "Datos incompletos. No se pueden mostrar correctamente.");
            Toast.makeText(this, "Datos incompletos del juego", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear objeto Game con los datos
        currentGame = new Game(id, titulo, descripcion, imagen);

        // Configurar las vistas
        ImageView imageView = findViewById(R.id.detailImage);
        TextView titleTextView = findViewById(R.id.detailTitle);
        TextView descriptionTextView = findViewById(R.id.detailDescription);
        favoriteFab = findViewById(R.id.favoriteFab);

        // Asignar los valores a las vistas
        titleTextView.setText(titulo);
        descriptionTextView.setText(descripcion);

        // Asegurarse de que la imagen se cargue correctamente
        Picasso.get()
                .load(imagen)
                .fit()
                .centerCrop()
                .into(imageView);

        // Configurar el FloatingActionButton
        favoriteFab.setOnClickListener(v -> {
            currentGame.setFavorite(!currentGame.isFavorite());
            updateFabIcon();
            favoritesViewModel.toggleFavorite(currentGame);
        });

        // Verificar si el juego está en favoritos
        favoritesViewModel.getFavoritesLiveData().observe(this, games -> {
            for (Game game : games) {
                if (game.getId().equals(currentGame.getId())) {
                    currentGame.setFavorite(true);
                    updateFabIcon();
                    break;
                }
            }
        });
    }



    private void updateFabIcon() {
        favoriteFab.setImageResource(
                currentGame.isFavorite() ?
                        R.drawable.ic_favorite_filled :
                        R.drawable.ic_favorite_border
        );
    }
}