package com.example.sgundot_di.data.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.sgundot_di.R;
import com.example.sgundot_di.data.models.Game;
import com.example.sgundot_di.data.viewmodels.FavoritesViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DetailFragment extends Fragment {

    private String gameTitle;
    private TextView titleTextView, descriptionTextView;
    private ImageView imageView;
    private Button favoriteButton;
    private DatabaseReference databaseReference;
    private DatabaseReference favoritesReference;
    private FirebaseAuth auth;
    private FavoritesViewModel favoritesViewModel;
    private boolean isFavorite = false;

    public DetailFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        titleTextView = view.findViewById(R.id.detailTitle);
        descriptionTextView = view.findViewById(R.id.detailDescription);
        imageView = view.findViewById(R.id.detailImage);
        favoriteButton = view.findViewById(R.id.favoriteButton);

        favoritesViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (getArguments() != null) {
            gameTitle = getArguments().getString("GAME_TITLE");
        }

        if (gameTitle != null) {
            loadGameDetails(gameTitle);
        }

        if (user != null) {
            favoritesReference = FirebaseDatabase.getInstance().getReference("favoritos").child(user.getUid());
            checkIfFavorite(gameTitle);

            favoriteButton.setOnClickListener(v -> {
                if (isFavorite) {
                    removeFromFavorites(gameTitle);
                } else {
                    addToFavorites(gameTitle);
                }
            });
        } else {
            favoriteButton.setVisibility(View.GONE); // Ocultar si el usuario no está autenticado
        }

        return view;
    }

    private void loadGameDetails(String gameTitle) {
        databaseReference = FirebaseDatabase.getInstance().getReference("juegos");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot gameSnapshot : snapshot.getChildren()) {
                    String titulo = gameSnapshot.child("titulo").getValue(String.class);

                    if (titulo != null && titulo.equals(gameTitle)) {
                        String descripcion = gameSnapshot.child("descripcion").getValue(String.class);
                        String imagen = gameSnapshot.child("imagen").getValue(String.class);

                        titleTextView.setText(titulo);
                        descriptionTextView.setText(descripcion);
                        Picasso.get().load(imagen).into(imageView);
                        return;
                    }
                }
                titleTextView.setText("Juego no encontrado");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                titleTextView.setText("Error al cargar los datos");
            }
        });
    }

    private void addToFavorites(String gameTitle) {
        favoritesReference.child(gameTitle).setValue(true).addOnSuccessListener(aVoid -> {
            Toast.makeText(getContext(), "Agregado a Favoritos", Toast.LENGTH_SHORT).show();
            isFavorite = true;
            favoriteButton.setText("Eliminar de Favoritos");
        }).addOnFailureListener(e -> Toast.makeText(getContext(), "Error al agregar", Toast.LENGTH_SHORT).show());
    }

    private void removeFromFavorites(String gameTitle) {
        favoritesReference.child(gameTitle).removeValue().addOnSuccessListener(aVoid -> {
            Toast.makeText(getContext(), "Eliminado de Favoritos", Toast.LENGTH_SHORT).show();
            isFavorite = false;
            favoriteButton.setText("Agregar a Favoritos");
        }).addOnFailureListener(e -> Toast.makeText(getContext(), "Error al eliminar", Toast.LENGTH_SHORT).show());
    }

    private void checkIfFavorite(String gameTitle) {
        favoritesReference.child(gameTitle).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    isFavorite = true;
                    favoriteButton.setText("Eliminar de Favoritos");
                } else {
                    isFavorite = false;
                    favoriteButton.setText("Agregar a Favoritos");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
}
