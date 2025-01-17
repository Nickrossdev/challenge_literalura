package com.alura.literalura.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataBusqueda(
        @JsonProperty("count")
        int resultados,
        @JsonProperty("next")
        String urlsiguiente,
        @JsonProperty("previous")
        String urlAnterior,
        @JsonProperty("results")
        List<DataLibro> libros
) {
}
