package com.example.sgundot_di.data.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.sgundot_di.data.models.Game;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class DashboardRepository {

    private final DatabaseReference juegosRef;
    private final MutableLiveData<List<Game>> juegosLiveData;
    private final MutableLiveData<String> errorLiveData;

    public DashboardRepository() {
        juegosRef = FirebaseDatabase.getInstance().getReference("juegos");
        juegosLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Game>> getJuegosLiveData() {
        return juegosLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public void cargarJuegos() {
        juegosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Game> listaDeJuegos = new ArrayList<>();
                int index = 0; // Contador manual para asignar ID

                for (DataSnapshot juegoSnapshot : snapshot.getChildren()) {
                    String titulo = juegoSnapshot.child("titulo").getValue(String.class);
                    String descripcion = juegoSnapshot.child("descripcion").getValue(String.class);
                    String imagen = juegoSnapshot.child("imagen").getValue(String.class);

                    if (titulo != null && descripcion != null && imagen != null) {
                        Game game = new Game(String.valueOf(index), titulo, descripcion, imagen, false);
                        listaDeJuegos.add(game);
                        index++; // Incrementamos el contador manualmente
                    }
                }

                juegosLiveData.setValue(listaDeJuegos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorLiveData.setValue("Error al cargar juegos: " + error.getMessage());
            }
        });
    }
}
