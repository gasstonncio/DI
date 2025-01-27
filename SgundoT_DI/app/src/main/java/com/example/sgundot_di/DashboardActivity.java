package com.example.sgundot_di;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private static final String TAG = "DashboardActivity";

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
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        logoutButton.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(DashboardActivity.this, MainActivity.class));
            finish();
        });
    }
}