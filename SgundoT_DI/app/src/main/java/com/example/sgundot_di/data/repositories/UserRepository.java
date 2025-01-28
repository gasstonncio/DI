package com.example.sgundot_di.data.repositories;

import androidx.lifecycle.MutableLiveData;
import com.example.sgundot_di.data.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserRepository {
    private final FirebaseAuth firebaseAuth;
    private final DatabaseReference usersRef;
    private final MutableLiveData<User> userLiveData;
    private final MutableLiveData<String> errorLiveData;

    public UserRepository() {
        firebaseAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference("users");
        userLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
    }

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

    public void logoutUser() {
        firebaseAuth.signOut();
        userLiveData.setValue(null);
    }

    public MutableLiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public boolean isUserLoggedIn() {
        return firebaseAuth.getCurrentUser() != null;
    }
}