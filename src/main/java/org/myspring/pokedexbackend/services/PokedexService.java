package org.myspring.pokedexbackend.services;

import lombok.RequiredArgsConstructor;
import org.myspring.pokedexbackend.dtos.api.PokemonApiDTO;
import org.myspring.pokedexbackend.dtos.base.FavoriteDTO;
import org.myspring.pokedexbackend.dtos.base.NicknameDTO;
import org.myspring.pokedexbackend.exceptions.CollectionEntryNotFoundException;
import org.myspring.pokedexbackend.exceptions.PokemonNotFoundException;
import org.myspring.pokedexbackend.models.Pokemon;
import org.myspring.pokedexbackend.repositories.PokedexRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PokedexService {
    private final PokedexRepository pokedexRepository;
    private final PokemonApiClient pokemonApiClient;
    //    private final RestClient pokemonRestClient; // alternative

    public Pokemon getPokemonByName(String name) {
        PokemonApiDTO pokemonApiDTO = pokemonApiClient.getPokemonByName(name);
        // alternative withpokemonRestClient
//        PokemonApiDTO pokemonApiDTO = Objects.requireNonNull(pokemonRestClient.get().uri("/pokemon/" + name).retrieve()
//                .onStatus(status -> status.value() == 404,
//                        (request, response) -> {
//                            throw new PokemonNotFoundException(name);
//                        }
//                )
//                .body(PokemonApiDTO.class));
        return new Pokemon(pokemonApiDTO);
    }

    public List<Pokemon> getAllFavorites() {
        return pokedexRepository.findAll();
    }

    public Pokemon getFavoriteById(UUID id) {
        return pokedexRepository.findById(id).orElseThrow(() -> new CollectionEntryNotFoundException(id));
    }

    public void deleteFavoriteById(UUID id) {
        Pokemon found = getFavoriteById(id);
        pokedexRepository.delete(found);
    }

    public Pokemon updateFavoriteById(UUID id, NicknameDTO nicknameDTO) {
        Pokemon foundPokemon = getFavoriteById(id);
        Pokemon updatedPokemon = new Pokemon(foundPokemon, nicknameDTO.nickname());
        return pokedexRepository.save(updatedPokemon);
    }

    public Pokemon createFavoritePokemon(FavoriteDTO favoriteDTO) {
        PokemonApiDTO pokemonApiDTO = pokemonApiClient.getPokemonByName(favoriteDTO.pokemonName());
        Pokemon pokemon = new Pokemon(pokemonApiDTO, favoriteDTO.nickname());
        return pokedexRepository.save(pokemon);
    }
}
