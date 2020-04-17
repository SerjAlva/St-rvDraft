package com.example.starvdraft_v1.models;

public class Ingredient {

    private String iName;
    private String iQuantity;
    private String iUnits;
    private String iComment;

    public Ingredient(String iName, String iQuantity, String iUnits, String iComment) {
        this.iName = iName;
        this.iQuantity = iQuantity;
        this.iUnits = iUnits;
        this.iComment = iComment;
    }

    public String getiName() {
        return iName;
    }

    public void setiName(String iName) {
        this.iName = iName;
    }

    public String getiQuantity() {
        return iQuantity;
    }

    public void setiQuantity(String iQuantity) {
        this.iQuantity = iQuantity;
    }

    public String getiUnits() {
        return iUnits;
    }

    public void setiUnits(String iUnits) {
        this.iUnits = iUnits;
    }

    public String getiComment() {
        return iComment;
    }

    public void setiComment(String iComment) {
        this.iComment = iComment;
    }
}
