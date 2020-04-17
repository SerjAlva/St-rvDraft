package com.example.starvdraft_v1.models;


public class DishItem {

    public String titulo, categoria, ingredientes, porciones;
    public double precio, precioMostrador;
    public int imagen;

    public DishItem(String titulo, String categoria, String ingredientes, Double precio, Double precioMostrador, Integer imagen, String porciones) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.ingredientes = ingredientes;
        this.precio = precio;
        this.precioMostrador = precioMostrador;
        this.imagen = imagen;
        this.porciones = porciones;

    }

    public DishItem() {

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getPrecioMostrador() {
        return precioMostrador;
    }

    public void setPrecioMostrador(double precioMostrador) {
        this.precioMostrador = precioMostrador;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getPorciones() {
        return porciones;
    }

    public void setPorciones(String porciones) {
        this.porciones = porciones;
    }




}
