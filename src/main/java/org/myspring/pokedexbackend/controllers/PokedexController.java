package org.myspring.pokedexbackend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.myspring.pokedexbackend.dtos.base.FavoriteDTO;
import org.myspring.pokedexbackend.models.Pokemon;
import org.myspring.pokedexbackend.services.PokedexService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pokemon")
@RequiredArgsConstructor
public class PokedexController {

    private final PokedexService pokedexService;

    @GetMapping("/{name}")
    public ResponseEntity<Pokemon> getPokemonByName(@PathVariable String name) {
        return ResponseEntity.accepted().body(pokedexService.getPokemonByName(name));
    }
}
