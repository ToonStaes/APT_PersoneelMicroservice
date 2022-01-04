package com.example.apt_personeelmicroservice;

import com.example.apt_personeelmicroservice.model.Functie;
import com.example.apt_personeelmicroservice.model.Personeel;
import com.example.apt_personeelmicroservice.repository.PersoneelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PersoneelControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersoneelRepository personeelRepository;

    Personeel personeel1 = new Personeel("Jan", "Polders", Functie.Zaal);
    Personeel personeel2 = new Personeel("Jef", "Mols", Functie.Zaal);
    Personeel personeel3 = new Personeel("Marlies", "Sjegers", Functie.Keuken);
    Personeel personeel4 = new Personeel("Jos", "Beckers", Functie.Keuken);

    private String genereerDatestringVandaag(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        return formatter.format(date);
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

    @BeforeEach
    public void BeforeAllTests(){
        personeelRepository.deleteAll();
        personeel1.setPersoneelsnummer(genereerPersoneelsnummer(personeel1));
        personeelRepository.save(personeel1);
        personeel2.setPersoneelsnummer(genereerPersoneelsnummer(personeel2));
        personeelRepository.save(personeel2);
        personeel3.setPersoneelsnummer(genereerPersoneelsnummer(personeel3));
        personeelRepository.save(personeel3);
        personeel4.setPersoneelsnummer(genereerPersoneelsnummer(personeel4));
        personeelRepository.save(personeel4);
    }

    @AfterEach
    public void AfterAllTests(){
        personeelRepository.deleteAll();
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void givenPersoneel_whenGetPersoneelByPersoneelsnummer_thenReturnJsonPersoneel() throws Exception {
        String personeelsnummer = "Z" + genereerDatestringVandaag() + "JP";
        mockMvc.perform(get("/personeel/{personeelsnummer}", personeelsnummer))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.voornaam", is("Jan")))
                .andExpect(jsonPath("$.achternaam", is("Polders")))
                .andExpect(jsonPath("$.functie", is(Functie.Zaal.toString())));
    }

    @Test
    void givenPersoneel_whenGetPersoneelByFunctie_thenReturnJsonPersoneel() throws Exception {

        mockMvc.perform(get("/personeel/functie/{gekozenFunctie}", "Keuken"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].voornaam", is("Marlies")))
                .andExpect(jsonPath("$[0].achternaam", is("Sjegers")))
                .andExpect(jsonPath("$[0].personeelsnummer", is("K" + genereerDatestringVandaag() + "MS")))
                .andExpect(jsonPath("$[1].voornaam", is("Jos")))
                .andExpect(jsonPath("$[1].achternaam", is("Beckers")))
                .andExpect(jsonPath("$[1].personeelsnummer", is("K" + genereerDatestringVandaag() + "JB")));
    }

    @Test
    void givenPersoneel_whenGetPersoneel_thenReturnJsonPersoneel() throws Exception {

        mockMvc.perform(get("/personeel"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].voornaam", is("Jan")))
                .andExpect(jsonPath("$[0].achternaam", is("Polders")))
                .andExpect(jsonPath("$[0].personeelsnummer", is("Z" + genereerDatestringVandaag() + "JP")))
                .andExpect(jsonPath("$[1].voornaam", is("Jef")))
                .andExpect(jsonPath("$[1].achternaam", is("Mols")))
                .andExpect(jsonPath("$[1].personeelsnummer", is("Z" + genereerDatestringVandaag() + "JM")))
                .andExpect(jsonPath("$[2].voornaam", is("Marlies")))
                .andExpect(jsonPath("$[2].achternaam", is("Sjegers")))
                .andExpect(jsonPath("$[2].personeelsnummer", is("K" + genereerDatestringVandaag() + "MS")))
                .andExpect(jsonPath("$[3].voornaam", is("Jos")))
                .andExpect(jsonPath("$[3].achternaam", is("Beckers")))
                .andExpect(jsonPath("$[3].personeelsnummer", is("K" + genereerDatestringVandaag() + "JB")));
    }
}
