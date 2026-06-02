package org.myspring.pokedexbackend.dtos.base;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record FavoriteDTO(
        @NotBlank
        String pokemonName,
        @NotBlank
        @Size(min = 5, max = 30)
        String nickname) {
}
