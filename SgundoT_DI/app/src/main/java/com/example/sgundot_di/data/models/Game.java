package com.example.sgundot_di.data.models;

public class Game {
    // Atributos del modelo de datos de un juego
    private String id;
    private String titulo;
    private String descripcion;
    private String imagen;
    private boolean isFavorite;

    // Constructor vacío requerido para Firebase
    public Game() { }

    // Constructor con parámetros para inicializar un juego
    public Game(String id, String titulo, String descripcion, String imagen, boolean isFavorite) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.isFavorite = isFavorite;
    }

    // Métodos getter y setter para obtener y modificar los atributos
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) { // 🔹 Método para modificar el estado de favorito
        this.isFavorite = favorite;
    }
}
