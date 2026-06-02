package org.myspring.pokedexbackend.controllers;

import lombok.RequiredArgsConstructor;
import org.myspring.pokedexbackend.models.Pokemon;
import org.myspring.pokedexbackend.services.PokedexService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pokemon")
@RequiredArgsConstructor
public class PokodexController {

    private final PokedexService pokedexService;

    @GetMapping("/{name}")
    public Pokemon getPokemonByName(@PathVariable String name) {
        return pokedexService.getPokemonByName(name);
    }
}
