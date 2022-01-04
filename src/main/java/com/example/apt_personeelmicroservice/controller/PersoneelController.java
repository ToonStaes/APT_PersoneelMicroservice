package com.example.apt_personeelmicroservice.controller;

import com.example.apt_personeelmicroservice.model.Functie;
import com.example.apt_personeelmicroservice.model.Personeel;
import com.example.apt_personeelmicroservice.repository.PersoneelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class PersoneelController {

    @Autowired
    private PersoneelRepository personeelRepository;

    @GetMapping("/personeel/{personeelsnummer}")
    public Personeel getPersoneelByPersoneelsnummer(@PathVariable String personeelsnummer){
        return personeelRepository.findPersoneelByPersoneelsnummer(personeelsnummer);
    }

    @GetMapping("/personeel/functie/{gekozenFunctie}")
    public List<Personeel> getPersoneelByFunctie(@PathVariable String gekozenFunctie){
        gekozenFunctie = gekozenFunctie.substring(0, 1).toUpperCase() + gekozenFunctie.substring(1);
        Functie functie = Functie.valueOf(gekozenFunctie);
        return personeelRepository.findPersoneelByFunctie(functie);
    }

    @GetMapping("/personeel")
    public List<Personeel> getPersoneel(){
        return personeelRepository.findAll();
    }

    @PostConstruct
    public void fillDB(){
        if(personeelRepository.count()==0){
            Personeel arne = new Personeel("Arne", "Hus", Functie.Keuken);
            arne.setPersoneelsnummer(genereerPersoneelsnummer(arne));
            personeelRepository.save(arne);

            Personeel toon = new Personeel("Toon", "Staes", Functie.Keuken);
            toon.setPersoneelsnummer(genereerPersoneelsnummer(toon));
            personeelRepository.save(toon);

            Personeel niels = new Personeel("Niels", "Verheyen", Functie.Zaal);
            niels.setPersoneelsnummer(genereerPersoneelsnummer(niels));
            personeelRepository.save(niels);
        }
    }

    private String genereerPersoneelsnummer(Personeel personeel) {
        String personeelsnummerMaker;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String datestring = formatter.format(date);

        if (personeel.getFunctie() == Functie.Keuken){
            personeelsnummerMaker = "K";
        } else {
            personeelsnummerMaker = "Z";
        }

        personeelsnummerMaker += datestring + personeel.getVoornaam().toUpperCase().charAt(0) + personeel.getAchternaam().toUpperCase().charAt(0);
        return personeelsnummerMaker;
    }
}
