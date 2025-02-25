package com.example.sgundot_di.data.models;

public class User {
    // Atributos del modelo de usuario
    private String uid;
    private String email;
    private String username;

    // Constructor vacío requerido para Firebase
    public User() {
    }

    // Constructor para inicializar un usuario con sus datos
    public User(String uid, String email, String username) {
        this.uid = uid;
        this.email = email;
        this.username = username;
    }

    // Métodos getter y setter para acceder y modificar los atributos
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
