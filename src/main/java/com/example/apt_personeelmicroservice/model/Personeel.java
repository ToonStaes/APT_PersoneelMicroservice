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
        this.personeelsnummer = setPersoneelsnummer();
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

    public String setPersoneelsnummer() {
        String personeelsnummerMaker;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String datestring = formatter.format(date);

        if (functie == Functie.Keuken){
            personeelsnummerMaker = "K";
        } else {
            personeelsnummerMaker = "Z";
        }

        personeelsnummerMaker += datestring + this.voornaam.toUpperCase().charAt(0) + this.achternaam.toUpperCase().charAt(0);
        return personeelsnummerMaker;
    }
}
