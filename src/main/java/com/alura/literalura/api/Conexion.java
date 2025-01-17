package com.alura.literalura.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Conexion {
    HttpClient client = HttpClient.newHttpClient();

    public String conectar(String url){
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return  response.body();
        } catch (IOException | InterruptedException e) {
            System.out.println("Error en la conexión a la Api");
            throw new RuntimeException(e);
        }
    }
}
