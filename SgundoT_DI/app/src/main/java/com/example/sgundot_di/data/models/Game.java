package com.example.sgundot_di.data.models;

public class Game {
    private String título;
    private String descripción;
    private String imagen;

    // Constructor vacío requerido para Firebase
    public Game() {
    }

    public Game(String título, String descripción, String imagen) {
        this.título = título;
        this.descripción = descripción;
        this.imagen = imagen;
    }

    // Getters y setters
    public String getTítulo() {
        return título;
    }

    public void setTítulo(String título) {
        this.título = título;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}