package com.example.apt_personeelmicroservice.model;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Personeel {

    public Personeel() {

    }

    public Personeel(String voornaam, String achternaam, Functie functie) {
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.functie = functie;
        setPersoneelsnummer();
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

    private void setPersoneelsnummer() {
        String personeelsnummer;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String datestring = formatter.format(date).toString();

        if (functie == Functie.Keuken){
            personeelsnummer = "K";
        } else {
            personeelsnummer = "Z";
        }

        personeelsnummer += datestring + this.voornaam.toUpperCase().charAt(0) + this.achternaam.toUpperCase().charAt(0);
        this.personeelsnummer = personeelsnummer;
    }
}
