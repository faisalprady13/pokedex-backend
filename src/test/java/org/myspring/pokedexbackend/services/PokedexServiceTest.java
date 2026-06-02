package org.myspring.pokedexbackend.services;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.myspring.pokedexbackend.dtos.api.PokemonApiDTO;
import org.myspring.pokedexbackend.dtos.base.FavoriteDTO;
import org.myspring.pokedexbackend.dtos.base.NicknameDTO;
import org.myspring.pokedexbackend.models.Pokemon;
import org.myspring.pokedexbackend.repositories.PokedexRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PokedexServiceTest {

    @Captor
    private ArgumentCaptor<Pokemon> pokemonCaptor;

    @Mock
    private PokedexRepository mockPokedexRepository;
    @Mock
    private PokemonApiClient mockPokemonApiClient;

    @Test
    void getPokemonByName_shouldReturnPokemon_whenGivenName() {
        PokemonApiDTO pokemonApiDTO = PokemonApiDTO.builder().name("ditto").build();
        Pokemon pokemon = Pokemon.builder().pokemonName("ditto").build();
        when(mockPokemonApiClient.getPokemonByName("ditto")).thenReturn(pokemonApiDTO);
        PokedexService pokedexService = new PokedexService(mockPokedexRepository, mockPokemonApiClient);

        Pokemon result = pokedexService.getPokemonByName("ditto");

        assertThat(result).usingRecursiveComparison().ignoringFields("id").isEqualTo(pokemon);
    }


    @Test
    void createFavoritePokemon_shouldReturnPokemon_whenGivenNameAndNickname() {
        FavoriteDTO favoriteDTO = FavoriteDTO.builder().pokemonName("ditto").nickname("jelly").build();
        PokemonApiDTO pokemonApiDTO = PokemonApiDTO.builder().name("ditto").build();
        Pokemon pokemon = new Pokemon(pokemonApiDTO, favoriteDTO.nickname());
        when(mockPokemonApiClient.getPokemonByName("ditto")).thenReturn(pokemonApiDTO);
        when(mockPokedexRepository.save(any(Pokemon.class))).thenReturn(pokemon);
        PokedexService pokedexService = new PokedexService(mockPokedexRepository, mockPokemonApiClient);

        Pokemon result = pokedexService.createFavoritePokemon(favoriteDTO);

        verify(mockPokedexRepository, times(1)).save(pokemonCaptor.capture());
        assertThat(pokemonCaptor.getValue()).usingRecursiveComparison().ignoringFields("id").isEqualTo(pokemon);
        assertThat(result).usingRecursiveComparison().ignoringFields("id").isEqualTo(pokemon);
    }

    @Test
    void getAllFavorites() {
        Pokemon pokemon = Pokemon.builder().pokemonName("ditto").build();
        List<Pokemon> pokemons = new ArrayList<>(List.of(pokemon));
        when(mockPokedexRepository.findAll()).thenReturn(pokemons);
        PokedexService pokedexService = new PokedexService(mockPokedexRepository, mockPokemonApiClient);

        List<Pokemon> result = pokedexService.getAllFavorites();

        assertEquals(result.size(), pokemons.size());
    }

    @Test
    void getFavoriteById_shouldReturnPokemon_whenGivenId() {
        Pokemon pokemon = Pokemon.builder().pokemonName("ditto").build();
        when(mockPokedexRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(pokemon));
        PokedexService pokedexService = new PokedexService(mockPokedexRepository, mockPokemonApiClient);

        Pokemon result = pokedexService.getFavoriteById(UUID.randomUUID());

        assertEquals(pokemon, result);
    }

    @Test
    void deleteFavoriteById_shouldCallRepoDelete_whenGivenId() {
        Pokemon pokemon = Pokemon.builder().pokemonName("ditto").build();
        when(mockPokedexRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(pokemon));
        PokedexService pokedexService = new PokedexService(mockPokedexRepository, mockPokemonApiClient);

        pokedexService.deleteFavoriteById(UUID.randomUUID());

        verify(mockPokedexRepository, times(1)).delete(pokemonCaptor.capture());
        assertEquals(pokemon, pokemonCaptor.getValue());
    }

    @Test
    void updateFavoriteById_shouldCallRepoUpdate_whenGivenId() {
        Pokemon pokemon = Pokemon.builder().pokemonName("ditto").nickname("jelly").build();
        NicknameDTO nicknameDTO = NicknameDTO.builder().nickname("purple jelly").build();
        Pokemon expected = new Pokemon(pokemon, nicknameDTO.nickname());
        when(mockPokedexRepository.findById(any(UUID.class))).thenReturn(Optional.of(pokemon));
        when(mockPokedexRepository.save(any(Pokemon.class))).thenReturn(expected);
        PokedexService pokedexService = new PokedexService(mockPokedexRepository, mockPokemonApiClient);

        Pokemon result = pokedexService.updateFavoriteById(UUID.randomUUID(), nicknameDTO);

        verify(mockPokedexRepository, times(1)).save(pokemonCaptor.capture());
        assertEquals(expected, pokemonCaptor.getValue());

        assertEquals(nicknameDTO.nickname(), result.nickname());
        assertEquals(expected, result);
    }
}