package org.myspring.pokedexbackend.dtos.base;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record FavoriteDTO(
        @NotBlank
        String pokemonName,
        @NotBlank
        @Min(4)
        @Max(30)
        String nickname) {
}
