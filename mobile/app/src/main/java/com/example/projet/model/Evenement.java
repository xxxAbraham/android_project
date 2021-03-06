package com.example.projet.model;

public class Evenement {

    String id;
    String nom;
    String date;
    String adresse;
    String description;
    String creator;

    public Evenement(String id, String nom, String date, String adresse, String description, String creator) {
        this.nom = nom;
        this.id = id;
        this.date = date;
        this.adresse = adresse;
        this.creator = creator;
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreator() {
        return creator;
    }
}
