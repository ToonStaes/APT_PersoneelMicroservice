package com.example.apt_personeelmicroservice.repository;

import com.example.apt_personeelmicroservice.model.Functie;
import com.example.apt_personeelmicroservice.model.Personeel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersoneelRepository extends JpaRepository<Personeel, Integer> {
    Personeel findPersoneelByPersoneelsnummer(String personeelsnummer);
    List<Personeel> findPersoneelByFunctie(Functie functie);
    List<Personeel> findAll();
}
