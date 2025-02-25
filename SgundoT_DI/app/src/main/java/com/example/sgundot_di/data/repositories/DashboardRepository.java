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

    // Referencia a la base de datos de Firebase, en la colección "juegos"
    private final DatabaseReference juegosRef;

    // LiveData para almacenar y observar la lista de juegos obtenidos desde Firebase
    private final MutableLiveData<List<Game>> juegosLiveData;

    // LiveData para manejar errores en la carga de datos desde Firebase
    private final MutableLiveData<String> errorLiveData;

    public DashboardRepository() {
        // Inicializamos la referencia a la base de datos apuntando a la colección "juegos"
        juegosRef = FirebaseDatabase.getInstance().getReference("juegos");

        // Inicializamos los objetos LiveData para ser observados por los ViewModels
        juegosLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
    }

    // Método para obtener la lista de juegos encapsulada en un LiveData
    public LiveData<List<Game>> getJuegosLiveData() {
        return juegosLiveData;
    }

    // Método para obtener mensajes de error encapsulados en un LiveData
    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    // Método para cargar los juegos desde Firebase y actualizar el LiveData correspondiente
    public void cargarJuegos() {
        juegosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // Lista temporal para almacenar los juegos obtenidos de Firebase
                List<Game> listaDeJuegos = new ArrayList<>();

                int index = 0; // Contador manual para asignar un ID único a cada juego obtenido

                /*
                 * Recorremos cada nodo hijo dentro del snapshot de Firebase, donde cada hijo
                 * representa un juego almacenado en la base de datos.
                 *
                 * - `snapshot.getChildren()` devuelve todos los juegos almacenados.
                 * - `juegoSnapshot.child("titulo").getValue(String.class)` obtiene el título del juego.
                 * - `juegoSnapshot.child("descripcion").getValue(String.class)` obtiene la descripción.
                 * - `juegoSnapshot.child("imagen").getValue(String.class)` obtiene la URL de la imagen.
                 *
                 * Se valida que los valores no sean nulos antes de agregarlos a la lista.
                 */
                for (DataSnapshot juegoSnapshot : snapshot.getChildren()) {
                    // Extraemos los valores del juego actual en la iteración
                    String titulo = juegoSnapshot.child("titulo").getValue(String.class);
                    String descripcion = juegoSnapshot.child("descripcion").getValue(String.class);
                    String imagen = juegoSnapshot.child("imagen").getValue(String.class);

                    // Verificamos que los datos obtenidos no sean nulos antes de crear un objeto Game
                    if (titulo != null && descripcion != null && imagen != null) {
                        // Creamos un nuevo objeto Game con los datos extraídos de Firebase
                        Game game = new Game(String.valueOf(index), titulo, descripcion, imagen, false);

                        // Agregamos el juego a la lista temporal
                        listaDeJuegos.add(game);

                        // Incrementamos el contador manual para garantizar un ID único
                        index++;
                    }
                }

                // Actualizamos el LiveData con la lista de juegos obtenida
                juegosLiveData.setValue(listaDeJuegos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Si ocurre un error, actualizamos el LiveData de errores con el mensaje correspondiente
                errorLiveData.setValue("Error al cargar juegos: " + error.getMessage());
            }
        });
    }
}
