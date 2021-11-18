package com.example.apt_personeelmicroservice;

import com.example.apt_personeelmicroservice.model.Functie;
import com.example.apt_personeelmicroservice.model.Personeel;
import com.example.apt_personeelmicroservice.repository.PersoneelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PersoneelControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersoneelRepository personeelRepository;

    private ObjectMapper mapper = new ObjectMapper();

    private String genereerDatestringVandaag(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        return formatter.format(date).toString();
    }

    @Test
    public void givenPersoneel_whenGetPersoneelByPersoneelsnummer_thenReturnJsonPersoneel() throws Exception {
        Personeel personeelslid = new Personeel("Jan", "Polders", Functie.Zaal);
        personeelslid.setVoornaam("Monique");
        personeelslid.setAchternaam("Lombaerts");
        personeelslid.setFunctie(Functie.Keuken);

        String personeelsnummer = "K" + genereerDatestringVandaag() + "ML";

        given(personeelRepository.findPersoneelByPersoneelsnummer(personeelsnummer)).willReturn(personeelslid);

        mockMvc.perform(get("/personeel/{personeelsnummer}", personeelsnummer))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.voornaam", is("Monique")))
                .andExpect(jsonPath("$.achternaam", is("Lombaerts")))
                .andExpect(jsonPath("$.functie", is(Functie.Keuken.toString())));
    }

    @Test
    public void givenPersoneel_whenGetPersoneelByFunctie_thenReturnJsonPersoneel() throws Exception {
        Personeel personeelslid1 = new Personeel("Marlies", "Sjegers", Functie.Zaal);
        Personeel personeelslid2 = new Personeel("Jos", "Beckers", Functie.Zaal);

        List<Personeel> personeelList = new ArrayList<>();
        personeelList.add(personeelslid1);
        personeelList.add(personeelslid2);

        given(personeelRepository.findPersoneelByFunctie(Functie.Keuken)).willReturn(personeelList);

        mockMvc.perform(get("/personeel/functie/{gekozenFunctie}", "Keuken"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].voornaam", is("Marlies")))
                .andExpect(jsonPath("$[0].achternaam", is("Sjegers")))
                .andExpect(jsonPath("$[0].personeelsnummer", is("Z" + genereerDatestringVandaag() + "MS")))
                .andExpect(jsonPath("$[1].voornaam", is("Jos")))
                .andExpect(jsonPath("$[1].achternaam", is("Beckers")))
                .andExpect(jsonPath("$[1].personeelsnummer", is("Z" + genereerDatestringVandaag() + "JB")));
    }

    @Test
    public void givenPersoneel_whenGetPersoneel_thenReturnJsonPersoneel() throws Exception {
        Personeel personeel1 = new Personeel("Jan", "Polders", Functie.Zaal);
        Personeel personeel2 = new Personeel("Jef", "Mols", Functie.Zaal);
        Personeel personeel3 = new Personeel("Marlies", "Sjegers", Functie.Keuken);
        Personeel personeel4 = new Personeel("Jos", "Beckers", Functie.Keuken);

        List<Personeel> personeelList = new ArrayList<>();
        personeelList.add(personeel1);
        personeelList.add(personeel2);
        personeelList.add(personeel3);
        personeelList.add(personeel4);

        given(personeelRepository.findAll()).willReturn(personeelList);

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