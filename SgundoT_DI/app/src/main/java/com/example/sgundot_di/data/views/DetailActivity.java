package com.example.sgundot_di.data.views;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.sgundot_di.R;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_TITULO = "extra_titulo";
    public static final String EXTRA_DESCRIPCION = "extra_descripcion";
    public static final String EXTRA_IMAGEN = "extra_imagen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Obtener los datos del Intent
        String titulo = getIntent().getStringExtra(EXTRA_TITULO);
        String descripcion = getIntent().getStringExtra(EXTRA_DESCRIPCION);
        String imagen = getIntent().getStringExtra(EXTRA_IMAGEN);

        // Configurar las vistas
        ImageView imageView = findViewById(R.id.detailImage);
        TextView titleTextView = findViewById(R.id.detailTitle);
        TextView descriptionTextView = findViewById(R.id.detailDescription);

        titleTextView.setText(titulo);
        descriptionTextView.setText(descripcion);
        Glide.with(this)
                .load(imagen)
                .into(imageView);
    }
}