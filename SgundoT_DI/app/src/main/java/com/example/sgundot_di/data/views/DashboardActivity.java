package com.example.sgundot_di.data.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sgundot_di.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("juegos");

        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        ImageView itemImageView = findViewById(R.id.itemImageView);
        Button logoutButton = findViewById(R.id.logoutButton);

        // Mostrar que la actividad se ha iniciado
        Log.d(TAG, "Iniciando carga de datos desde Firebase");

        mDatabase.addValueEventListener(new ValueEventListener() {
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
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Error al leer datos de Firebase", databaseError.toException());
                Toast.makeText(DashboardActivity.this,
                        "Error al cargar los datos: " + databaseError.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        logoutButton.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
            finish();
        });
    }
}