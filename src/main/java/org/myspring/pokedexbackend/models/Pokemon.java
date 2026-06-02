package org.myspring.pokedexbackend.models;

import lombok.Builder;
import org.myspring.pokedexbackend.dtos.api.PokemonApiDTO;
import org.myspring.pokedexbackend.dtos.api.TypeApiDTO;
import org.myspring.pokedexbackend.dtos.api.TypeDetailApiDTO;

import java.util.Arrays;
import java.util.UUID;

@Builder
public record Pokemon(UUID id,
                      int pokemonId,
                      String nickname,
                      String pokemonName,
                      String pictureUrl,
                      int height,
                      int weight,
                      String[] types
) {

    public Pokemon(PokemonApiDTO pokemonApiDTO) {
        String[] types = Arrays.stream(pokemonApiDTO.types())
                .map(TypeApiDTO::type)
                .map(TypeDetailApiDTO::name)
                .toArray(String[]::new);

        this(UUID.randomUUID(),
                pokemonApiDTO.id(),
                null,
                pokemonApiDTO.name(),
                pokemonApiDTO.sprites().other().officialArtworkApiDTO().front_default(),
                pokemonApiDTO.height(),
                pokemonApiDTO.weight(),
                types
        );
    }

}
