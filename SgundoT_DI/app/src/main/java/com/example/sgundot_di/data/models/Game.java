package com.example.sgundot_di.data.models;

public class Game {
    private String id;          // Campo id para almacenar el identificador único del juego
    private String titulo;      // Cambiado de "título" a "titulo"
    private String descripcion; // Cambiado de "descripción" a "descripcion"
    private String imagen;
    private boolean isFavorite; // Añadido

    // Constructor vacío requerido para Firebase
    public Game() {
    }

    public Game(String id, String titulo, String descripcion, String imagen) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.isFavorite = false;
    }

    // Getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;  // Este setter permite asignar el id generado por Firebase
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
