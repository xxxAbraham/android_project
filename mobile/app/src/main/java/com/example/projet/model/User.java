package com.example.projet.model;

public class User {
    private String nom;
    private String id;

    public User(String nom, String id){
    this.nom = nom;
    this.id = id;
    }

    public String getNom(){
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
