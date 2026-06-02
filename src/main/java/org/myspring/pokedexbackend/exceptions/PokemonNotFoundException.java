package org.myspring.pokedexbackend.exceptions;

public class PokemonNotFoundException extends RuntimeException {
    public PokemonNotFoundException() {
    }

    public PokemonNotFoundException(String pokemonName) {
        super("Pokemon with name " + pokemonName + " not found");
    }
}
