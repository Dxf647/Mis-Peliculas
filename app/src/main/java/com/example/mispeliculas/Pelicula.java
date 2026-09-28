package com.example.mispeliculas;

public class Pelicula {
    private int id;
    private String titulo;
    private String genero;
    private int anio;
    private String descripcion;
    private int calificacion;
    private String imagen;
    private String fechaAgregado;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getFechaAgregado() {
        return fechaAgregado;
    }

    public void setFechaAgregado(String fechaAgregado) {
        this.fechaAgregado = fechaAgregado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //Recuperar
    public Pelicula(int id, String titulo, String genero, int anio, String descripcion, int calificacion, String imagen, String fechaAgregado) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.anio = anio;
        this.descripcion = descripcion;
        this.calificacion = calificacion;
        this.imagen = imagen;
        this.fechaAgregado = fechaAgregado;
    }

    //Crear
    public Pelicula(String titulo, String genero, int anio, String descripcion, int calificacion, String imagen) {
        this.titulo = titulo;
        this.genero = genero;
        this.anio = anio;
        this.descripcion = descripcion;
        this.calificacion = calificacion;
        this.imagen = imagen;
    }
}
