package com.alura.literalura.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataEnlaces(
        @JsonProperty("text/html")
        String textHtml,

        @JsonProperty("application/epub+zip")
        String epubZip,

        @JsonProperty("application/x-mobipocket-ebook")
        String mobipocketEbook,

        @JsonProperty("text/plain; charset=us-ascii")
        String charsetUsAscii,

        @JsonProperty("text/plain; charset=utf-8")
        String charsetUtf8,

        @JsonProperty("application/rdf+xml")
        String rdfXml,

        @JsonProperty("image/jpeg")
        String imagenJpeg,

        @JsonProperty("application/octet-stream")
        String octetStream
) {
}
