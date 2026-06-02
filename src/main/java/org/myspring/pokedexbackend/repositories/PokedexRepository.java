package org.myspring.pokedexbackend.repositories;

import org.myspring.pokedexbackend.models.Pokemon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PokedexRepository extends MongoRepository<Pokemon, UUID> {
}
