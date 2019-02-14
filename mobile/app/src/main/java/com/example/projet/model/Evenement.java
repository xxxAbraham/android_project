package com.example.projet.model;

public class Evenement {

    String nom;
    String date;
    String adresse;
    int codePostal;
    String ville;
    String description;

    public Evenement(String nom, String date, String adresse, int codePostal, String ville, String description) {
        this.nom = nom;
        this.date = date;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
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

    public int getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
