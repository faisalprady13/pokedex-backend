package org.myspring.pokedexbackend.services;

import org.myspring.pokedexbackend.dtos.api.PokemonApiDTO;
import org.myspring.pokedexbackend.exceptions.PokemonNotFoundException;
import org.myspring.pokedexbackend.models.Pokemon;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class PokemonApiClient {
    private final RestClient restClient;

    public PokemonApiClient(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("https://pokeapi.co/api/v2/").build();
    }

    public PokemonApiDTO getPokemonByName(String name) {
        return restClient.get().uri("/pokemon/" + name).retrieve()
                .onStatus(status -> status.value() == 404,
                        (request, response) -> {
                            throw new PokemonNotFoundException(name);
                        }
                )
                .body(PokemonApiDTO.class);
    }
}
