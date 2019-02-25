package com.example.projet.model;

public class Evenement {

    String nom;
    String date;
    String adresse;
    String description;

    public Evenement(String nom, String date, String adresse, String description) {
        this.nom = nom;
        this.date = date;
        this.adresse = adresse;
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


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
