package com.example.apt_personeelmicroservice.model;

import javax.persistence.*;

@Entity
public class Personeel {

    public Personeel() {

    }

    public Personeel(String voornaam, String achternaam, Functie functie) {
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.functie = functie;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String voornaam;
    private String achternaam;

    @Enumerated(EnumType.STRING)
    private Functie functie;

    @Column(unique = true)
    private String personeelsnummer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Functie getFunctie() {
        return functie;
    }

    public void setFunctie(Functie functie) {
        this.functie = functie;
    }

    public String getPersoneelsnummer() {
        return personeelsnummer;
    }

    @PostPersist
    public void setPersoneelsnummer() {
        if (functie == Functie.Keuken){
            this.personeelsnummer = "K" + this.id;
        } else {
            this.personeelsnummer = "Z" + this.id;
        }

    }
}
