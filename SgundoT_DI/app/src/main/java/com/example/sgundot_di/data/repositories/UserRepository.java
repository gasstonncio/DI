package com.example.sgundot_di.data.repositories;

import androidx.lifecycle.MutableLiveData;
import com.example.sgundot_di.data.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserRepository {
    private final FirebaseAuth firebaseAuth; // Instancia de Firebase Authentication
    private final DatabaseReference usersRef; // Referencia a la base de datos Firebase para usuarios
    private final MutableLiveData<User> userLiveData; // LiveData para manejar datos del usuario
    private final MutableLiveData<String> errorLiveData; // LiveData para manejar errores

    public UserRepository() {
        firebaseAuth = FirebaseAuth.getInstance(); // Inicializamos Firebase Authentication
        usersRef = FirebaseDatabase.getInstance().getReference("users"); // Referencia a la colección "users"
        userLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
    }

    // Método para registrar un usuario en Firebase Authentication y guardarlo en la base de datos
    public void registerUser(String email, String password, String username) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser firebaseUser = authResult.getUser();
                    if (firebaseUser != null) {
                        User newUser = new User(firebaseUser.getUid(), email, username);
                        usersRef.child(firebaseUser.getUid()).setValue(newUser)
                                .addOnSuccessListener(aVoid -> userLiveData.setValue(newUser))
                                .addOnFailureListener(e -> errorLiveData.setValue(e.getMessage()));
                    }
                })
                .addOnFailureListener(e -> errorLiveData.setValue(e.getMessage()));
    }

    // Método para iniciar sesión en Firebase Authentication
    public void loginUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser firebaseUser = authResult.getUser();
                    if (firebaseUser != null) {
                        usersRef.child(firebaseUser.getUid()).get()
                                .addOnSuccessListener(snapshot -> {
                                    User user = snapshot.getValue(User.class);
                                    userLiveData.setValue(user);
                                })
                                .addOnFailureListener(e -> errorLiveData.setValue(e.getMessage()));
                    }
                })
                .addOnFailureListener(e -> errorLiveData.setValue(e.getMessage()));
    }

    // Método para cerrar sesión
    public void logoutUser() {
        firebaseAuth.signOut();
        userLiveData.setValue(null);
    }

    // Método para obtener datos del usuario autenticado
    public MutableLiveData<User> getUserLiveData() {
        return userLiveData;
    }

    // Método para obtener errores de autenticación
    public MutableLiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    // Método para verificar si un usuario está autenticado
    public boolean isUserLoggedIn() {
        return firebaseAuth.getCurrentUser() != null;
    }
}
