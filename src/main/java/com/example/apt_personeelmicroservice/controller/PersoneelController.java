package com.example.apt_personeelmicroservice.controller;

import com.example.apt_personeelmicroservice.model.Functie;
import com.example.apt_personeelmicroservice.model.Personeel;
import com.example.apt_personeelmicroservice.repository.PersoneelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
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
            arne.setPersoneelsnummer("K20220103AH");
            personeelRepository.save(arne);

            Personeel toon = new Personeel("Toon", "Staes", Functie.Keuken);
            toon.setPersoneelsnummer("K20220103TS");
            personeelRepository.save(toon);

            Personeel niels = new Personeel("Niels", "Verheyen", Functie.Zaal);
            niels.setPersoneelsnummer("Z20220103NV");
            personeelRepository.save(niels);

            Personeel bert = new Personeel("Bert", "Moons", Functie.Zaal);
            bert.setPersoneelsnummer("Z20220104BM");
            personeelRepository.save(bert);

            Personeel lisa = new Personeel("Lisa", "Polders", Functie.Zaal);
            lisa.setPersoneelsnummer("Z20220104LP");
            personeelRepository.save(lisa);
        }
    }
}
