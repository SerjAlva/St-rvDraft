package com.example.starvdraft_v1.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recipe {

    @SerializedName("Receta")
    @Expose
    private String receta;
    @SerializedName("Porcion")
    @Expose
    private String porcion;
    @SerializedName("TiempoP")
    @Expose
    private String tiempoP;
    @SerializedName("TiempoC")
    @Expose
    private String tiempoC;
    @SerializedName("Preparacion")
    @Expose
    private String preparacion;
    @SerializedName("IngredientesD")
    @Expose
    private String ingredientesD;
    @SerializedName("Autor")
    @Expose
    private String autor;
    @SerializedName("Calificacion")
    @Expose
    private String calificacion;
    @SerializedName("Categoria")
    @Expose
    private String categoria;
    @SerializedName("Nombre")
    @Expose
    private String nombre;
    @SerializedName("Apellido")
    @Expose
    private String apellido;

    public String getReceta() {
        return receta;
    }

    public void setReceta(String receta) {
        this.receta = receta;
    }

    public String getPorcion() {
        return porcion;
    }

    public void setPorcion(String porcion) {
        this.porcion = porcion;
    }

    public String getTiempoP() {
        return tiempoP;
    }

    public void setTiempoP(String tiempoP) {
        this.tiempoP = tiempoP;
    }

    public String getTiempoC() {
        return tiempoC;
    }

    public void setTiempoC(String tiempoC) {
        this.tiempoC = tiempoC;
    }

    public String getPreparacion() {
        return preparacion;
    }

    public void setPreparacion(String preparacion) {
        this.preparacion = preparacion;
    }

    public String getIngredientesD() {
        return ingredientesD;
    }

    public void setIngredientesD(String ingredientesD) {
        this.ingredientesD = ingredientesD;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

}