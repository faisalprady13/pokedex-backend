package org.myspring.pokedexbackend.dtos.api;

import lombok.Builder;

@Builder
public record PokemonApiDTO(
        int id,
        String name,
        int height,
        int weight,
        TypeApiDTO[] types,
        SpriteApiDTO sprites
) {
}

