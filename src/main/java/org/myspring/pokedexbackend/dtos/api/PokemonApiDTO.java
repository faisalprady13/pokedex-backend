package org.myspring.pokedexbackend.dtos.api;

import lombok.Builder;

import java.util.List;

@Builder
public record PokemonApiDTO(
        int id,
        String name,
        int height,
        int weight,
        List<TypeApiDTO> types,
        SpriteApiDTO sprites
) {
}

