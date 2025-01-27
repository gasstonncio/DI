package com.example.sgundot_di;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
<<<<<<< HEAD
import android.widget.Toast;
=======
import android.util.Log;
>>>>>>> 5403b93c5bba4ab7af512b47a19e70e6d85361ce

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity {

    private static final String TAG = "DashboardActivity";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private static final String TAG = "DashboardActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mAuth = FirebaseAuth.getInstance();
<<<<<<< HEAD
        mDatabase = FirebaseDatabase.getInstance().getReference();
=======
        mDatabase = FirebaseDatabase.getInstance().getReference("juegos");
>>>>>>> 5403b93c5bba4ab7af512b47a19e70e6d85361ce

        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        ImageView itemImageView = findViewById(R.id.itemImageView);
        Button logoutButton = findViewById(R.id.logoutButton);

<<<<<<< HEAD
        // Mostrar que la actividad se ha iniciado
        Log.d(TAG, "Iniciando carga de datos desde Firebase");

        mDatabase.child("juegos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "Datos recibidos de Firebase: " + dataSnapshot.exists());

                if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    Log.d(TAG, "Número de juegos encontrados: " + dataSnapshot.getChildrenCount());

                    // Obtener el primer juego
                    DataSnapshot firstGame = dataSnapshot.getChildren().iterator().next();
                    Log.d(TAG, "Datos del primer juego: " + firstGame.getValue());

                    String titulo = firstGame.child("título").getValue(String.class);
                    String descripcion = firstGame.child("descripción").getValue(String.class);
                    String imagen = firstGame.child("imagen").getValue(String.class);

                    Log.d(TAG, "Título: " + titulo);
                    Log.d(TAG, "Descripción: " + descripcion);
                    Log.d(TAG, "URL de imagen: " + imagen);

                    if (titulo != null) titleTextView.setText(titulo);
                    if (descripcion != null) descriptionTextView.setText(descripcion);

                    if (imagen != null && !imagen.isEmpty()) {
                        Picasso.get()
                                .load(imagen)
                                .fit()
                                .centerCrop()
                                .into(itemImageView, new com.squareup.picasso.Callback() {
                                    @Override
                                    public void onSuccess() {
                                        Log.d(TAG, "Imagen cargada exitosamente");
                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        Log.e(TAG, "Error al cargar la imagen", e);
                                        Toast.makeText(DashboardActivity.this,
                                                "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                } else {
                    Log.e(TAG, "No se encontraron datos en Firebase");
                    Toast.makeText(DashboardActivity.this,
                            "No se encontraron datos", Toast.LENGTH_SHORT).show();
=======
        // Cambiamos a ValueEventListener para mejor manejo de datos
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "Data received: " + dataSnapshot.getValue());

                // Intentamos obtener el primer elemento
                for (DataSnapshot gameSnapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, "Game data: " + gameSnapshot.getValue());

                    String title = gameSnapshot.child("título").getValue(String.class);
                    String description = gameSnapshot.child("descripción").getValue(String.class);
                    String imageUrl = gameSnapshot.child("imagen").getValue(String.class);

                    Log.d(TAG, "Title: " + title);
                    Log.d(TAG, "Description: " + description);
                    Log.d(TAG, "Image URL: " + imageUrl);

                    if (title != null) titleTextView.setText(title);
                    if (description != null) descriptionTextView.setText(description);
                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        Picasso.get()
                                .load(imageUrl)
                                .fit()
                                .centerCrop()
                                .into(itemImageView);
                    }

                    // Solo necesitamos el primer elemento
                    break;
>>>>>>> 5403b93c5bba4ab7af512b47a19e70e6d85361ce
                }
            }

            @Override
<<<<<<< HEAD
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Error al leer datos de Firebase", databaseError.toException());
                Toast.makeText(DashboardActivity.this,
                        "Error al cargar los datos: " + databaseError.getMessage(),
                        Toast.LENGTH_SHORT).show();
=======
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
>>>>>>> 5403b93c5bba4ab7af512b47a19e70e6d85361ce
            }
        });

        logoutButton.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(DashboardActivity.this, MainActivity.class));
            finish();
        });
    }
}