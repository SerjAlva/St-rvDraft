package com.example.starvdraft_v1.models;
/*

    La clase elemento lista representa cada ingrediente que estamos comprando, ejemplo:

            "1.5 Kg de Zanahria"

 */
public class MarketListItem {
    protected double cantidad;
    protected String unidad;
    protected String nombreIngrediente;
    protected boolean status;
    //Por si se necesita v:
    //protected String comentarios;


    public MarketListItem(double masa, String unidad, String nombreIngrediente) {
        this.cantidad = masa;
        this.unidad = unidad;
        this.nombreIngrediente = nombreIngrediente;
        status = false;
    }

    public boolean isReady(){
        return status;
    }

    public void setStatus(boolean status){
        this.status = status;
    }
    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getNombreIngrediente() {
        return nombreIngrediente;
    }

    public void setNombreIngrediente(String nombreIngrediente) {
        this.nombreIngrediente = nombreIngrediente;
    }

    @Override
    public String toString(){
        return cantidad + " " + unidad + " de " + nombreIngrediente;
    }
}
