package org.myspring.pokedexbackend.dtos.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OtherSpriteApiDTO(
        @JsonProperty("official-artwork")
        OfficialArtworkApiDTO officialArtworkApiDTO) {
}
