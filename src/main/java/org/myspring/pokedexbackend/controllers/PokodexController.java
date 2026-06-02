package org.myspring.pokedexbackend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.myspring.pokedexbackend.dtos.base.FavoriteDTO;
import org.myspring.pokedexbackend.models.Pokemon;
import org.myspring.pokedexbackend.services.PokedexService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PokodexController {

    private final PokedexService pokedexService;

    @GetMapping("/pokemon/{name}")
    public Pokemon getPokemonByName(@PathVariable String name) {
        return pokedexService.getPokemonByName(name);
    }

    @PostMapping("/collection")
    public Pokemon createFavoritePokemon(@RequestBody @Valid FavoriteDTO favoriteDTO) {
        return pokedexService.createFavoritePokemon(favoriteDTO);
    }
}
