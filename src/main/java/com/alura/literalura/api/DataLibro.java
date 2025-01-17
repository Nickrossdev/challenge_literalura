package com.alura.literalura.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataLibro(
        @JsonProperty("id")
        Integer gutembergID,

        @JsonProperty("title")
        String titulo,

        @JsonProperty("subjects")
        List<String> temas,

        @JsonProperty("authors")
        List<DataPersona> autores,

        @JsonProperty("translators")
        List<DataPersona> traductores,

        @JsonProperty("bookshelves")
        List<String> colecciones,

        @JsonProperty("languages")
        List<String> lenguajes,

        @JsonProperty("copyright")
        Boolean copyright,

        @JsonProperty("formats")
        DataEnlaces enlaces,

        @JsonProperty("download_count")
        Integer descargas
) {
        @Override
        public String toString() {
                return String.format("""
                        ID: %s
                        TITULO: %s
                        TEMAS: %s
                        AUTOR: %s
                        TRADUCTORES: %s
                        COLECCIONES: %s
                        IDIOMAS: %s
                        COPYRIGHT: %s
                        ENLACES: %s
                        DESCARGAS: %s
                        ------------------------------------------------------------------------------------------------""",
                        gutembergID,
                        titulo,
                        temas,
                        autores,
                        traductores,
                        colecciones,
                        lenguajes,
                        copyright,
                        enlaces,
                        descargas);

        }
}
