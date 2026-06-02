package org.myspring.pokedexbackend.services;

import lombok.RequiredArgsConstructor;
import org.myspring.pokedexbackend.dtos.api.PokemonApiDTO;
import org.myspring.pokedexbackend.models.Pokemon;
import org.myspring.pokedexbackend.repositories.PokedexRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PokedexService {
    private final PokedexRepository pokedexRepository;
    private final PokemonApiClient pokemonApiClient;

    public Pokemon getPokemonByName(String name) {
        PokemonApiDTO pokemonApiDTO = pokemonApiClient.getPokemonByName(name);
        return new Pokemon(pokemonApiDTO);
    }
}
