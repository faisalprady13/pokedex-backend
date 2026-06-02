package org.myspring.pokedexbackend.dtos.base;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record NicknameDTO(
        @NotBlank
        @Size(min = 5, max = 30)
        String nickname) {
}
