package org.myspring.pokedexbackend.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record ErrorMessage(String message, String timestamp) {

    public ErrorMessage(String message) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this(message, LocalDateTime.now().format(formatter));
    }
}
