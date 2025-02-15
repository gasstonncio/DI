package com.example.sgundot_di.data.models;

public class Game {
    private String id;
    private String titulo;
    private String descripcion;
    private String imagen;
    private boolean isFavorite;

    public Game() { }

    public Game(String id, String titulo, String descripcion, String imagen, boolean isFavorite) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.isFavorite = isFavorite;
    }

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

    public void setFavorite(boolean favorite) { // 🔹 Se agregó este setter para corregir el error
        this.isFavorite = favorite;
    }
}
