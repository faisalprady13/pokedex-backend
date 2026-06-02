package org.myspring.pokedexbackend.exceptions;

import java.util.UUID;

public class CollectionEntryNotFoundException extends RuntimeException {
    public CollectionEntryNotFoundException() {
    }

    public CollectionEntryNotFoundException(UUID id) {
        super("Favorite pokemon with id " + id + " not found");
    }
}
