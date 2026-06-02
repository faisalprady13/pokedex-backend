package org.myspring.pokedexbackend.models;

import lombok.Builder;
import org.myspring.pokedexbackend.dtos.api.PokemonApiDTO;
import org.myspring.pokedexbackend.dtos.api.TypeApiDTO;
import org.myspring.pokedexbackend.dtos.api.TypeDetailApiDTO;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Builder
public record Pokemon(
        @Id
        UUID id,
        int pokemonId,
        String nickname,
        String pokemonName,
        String pictureUrl,
        int height,
        int weight,
        List<String> types
) {

    public Pokemon(PokemonApiDTO pokemonApiDTO) {
        List<String> types = pokemonApiDTO.types() == null ? null : Arrays.stream(pokemonApiDTO.types())
                .map(TypeApiDTO::type)
                .map(TypeDetailApiDTO::name)
                .toList();
        String pictureUrl = pokemonApiDTO.sprites() == null ? null : pokemonApiDTO.sprites().other().officialArtworkApiDTO().front_default();

        this(UUID.randomUUID(),
                pokemonApiDTO.id(),
                null,
                pokemonApiDTO.name(),
                pictureUrl,
                pokemonApiDTO.height(),
                pokemonApiDTO.weight(),
                types
        );
    }

    public Pokemon(PokemonApiDTO pokemonApiDTO, String nickname) {
        List<String> types = pokemonApiDTO.types() == null ? null : Arrays.stream(pokemonApiDTO.types())
                .map(TypeApiDTO::type)
                .map(TypeDetailApiDTO::name)
                .toList();
        String pictureUrl = pokemonApiDTO.sprites() == null ? null : pokemonApiDTO.sprites().other().officialArtworkApiDTO().front_default();

        this(UUID.randomUUID(),
                pokemonApiDTO.id(),
                nickname,
                pokemonApiDTO.name(),
                pictureUrl,
                pokemonApiDTO.height(),
                pokemonApiDTO.weight(),
                types
        );
    }
}
