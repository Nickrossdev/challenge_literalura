package com.alura.literalura.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DataPersona(

        @JsonProperty("name")
        String nombre,

        @JsonProperty("birth_year")
        Integer nacimiento,

        @JsonProperty("death_year")
        Integer deceso
) {
        @Override
        public String toString() {
                return String.format(" NOMBRE: %s | AÑO DE NACIMIENTO: %s | AÑO DE FALLECIMIENTO: %s",
                        nombre,
                        nacimiento,
                        deceso);
        }
}
