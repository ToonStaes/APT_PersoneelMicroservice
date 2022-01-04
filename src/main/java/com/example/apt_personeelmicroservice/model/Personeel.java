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

    public String getVoornaam() {
        return voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public Functie getFunctie() {
        return functie;
    }

    public String getPersoneelsnummer() {
        return personeelsnummer;
    }

    public void setPersoneelsnummer(String personeelsnummer) {
        this.personeelsnummer = personeelsnummer;
    }
}
