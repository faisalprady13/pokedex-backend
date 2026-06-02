package org.myspring.pokedexbackend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.myspring.pokedexbackend.dtos.base.FavoriteDTO;
import org.myspring.pokedexbackend.models.Pokemon;
import org.myspring.pokedexbackend.services.PokedexService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/collection")
@RequiredArgsConstructor
public class CollectionController {

    private final PokedexService pokedexService;

    @GetMapping
    public ResponseEntity<List<Pokemon>> getAllFavorites() {
        return ResponseEntity.ok().body(pokedexService.getAllFavorites());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pokemon> getById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(pokedexService.getFavoriteById(id));
    }

    @PostMapping
    public ResponseEntity<Pokemon> createFavoritePokemon(@RequestBody @Valid FavoriteDTO favoriteDTO) {
        Pokemon createdPokemon = pokedexService.createFavoritePokemon(favoriteDTO);
        return ResponseEntity.created(URI.create("/api/collection/" + createdPokemon.id().toString())).body(createdPokemon);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavoritePokemon(@PathVariable UUID id) {
        pokedexService.deleteFavoriteById(id);
        return ResponseEntity.noContent().build();
    }
}
