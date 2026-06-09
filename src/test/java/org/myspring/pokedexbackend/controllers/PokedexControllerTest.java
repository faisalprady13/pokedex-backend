package org.myspring.pokedexbackend.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.myspring.pokedexbackend.dtos.api.*;
import org.myspring.pokedexbackend.models.Pokemon;
import org.myspring.pokedexbackend.repositories.PokedexRepository;
import org.myspring.pokedexbackend.services.PokedexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.restclient.test.autoconfigure.AutoConfigureMockRestServiceServer;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;


import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureMockRestServiceServer
class PokedexControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockRestServiceServer mockServer;

    @Test
    void getPokemonByName_shouldReturnPokemon_whenGivenName() throws Exception {
        String pokemonName = "pikachu";
        PokemonApiDTO pokemonApiDTO =
                PokemonApiDTO.builder()
                        .name("pikachu")
                        .id(99)
                        .height(123)
                        .weight(321)
                        .sprites(new SpriteApiDTO(new OtherSpriteApiDTO(new OfficialArtworkApiDTO("pikachu_image_url"))))
                        .types(List.of(
                                new TypeApiDTO(new TypeDetailApiDTO("thunder")),
                                new TypeApiDTO(new TypeDetailApiDTO("cute"))
                        ))
                        .build();

        Pokemon pokemon = new Pokemon(pokemonApiDTO);

        mockServer.expect(requestTo("https://pokeapi.co/api/v2/pokemon/" + pokemonName))
                .andRespond(withSuccess(objectMapper.writeValueAsString(pokemonApiDTO), MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/api/pokemon/" + pokemonName))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.pokemonName").value(pokemon.pokemonName()))
                .andExpect(jsonPath("$.height").value(pokemon.height()))
                .andExpect(jsonPath("$.weight").value(pokemon.weight()))
                .andExpect(jsonPath("$.nickname").value(pokemon.nickname()))
                .andExpect(jsonPath("$.pokemonId").value(pokemon.pokemonId()))
                .andExpect(jsonPath("$.types", containsInAnyOrder("thunder", "cute"))) // compare list
//                .andExpect(jsonPath("$.types", contains("thunder", "cute"))) // fail example
//                .andExpect(jsonPath("$.types", contains("cute", "thunder"))) this work but order must be correct
//                .andExpect(jsonPath("$.types").value(pokemon.types())) this wont work for array
                .andExpect(jsonPath("$.types[0]").value("thunder")) // check 1 by 1 also works
                .andExpect(jsonPath("$.types[1]").value("cute"))
                .andExpect(jsonPath("$.pictureUrl").value(pokemon.pictureUrl()));
//                .andExpect(content().json(objectMapper.writeValueAsString(pokemon)));
//  compare the whole object, but not possible because id need to be ignored, hence compare each property
    }


    // I think this is unnecessary, since the throw will be tested by unit test?
//    @Test
//    void getPokemonByName_shouldThrowError_whenNameNotFound() {
//        String pokemonName = "wolf";
//
//        mockServer.expect(requestTo("https://pokeapi.co/api/v2/pokemon/" + pokemonName))
//                .andRespond(withBadGateway());
//
//    }
}