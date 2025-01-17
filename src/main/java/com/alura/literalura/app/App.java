package com.alura.literalura.app;

import com.alura.literalura.api.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Scanner;

public class App {
    private final String HOST = "https://gutendex.com/books/?";
    private final String PATH_BUSQUEDA = "search=";
    private final String PATH_PAGINA = "&page=";
    private int paginaActual = 1;

    boolean isRun = true;
    boolean isFirstSearch = true;

    private Conexion conexion = new Conexion();
    private ConversorGenerico conversor = new ConversorGenerico();
    private DataBusqueda ultimaBusqueda;

    public void run(){
        imprimirSaludo();
        iniciar();
    }

    private void iniciar(){
        while (isRun){
            procesarMenuInicial(leerOpcion());
        }
    }

    private void imprimirSaludo(){
        imprimirMensaje("BIENVENIDO A LITERALURA");
    }

    private void imprimirMenuInicial(){
        System.out.println("""
                * INSERTE UNA OPCIÓN *
                
                1. AGREGAR NUEVO LIBRO
                2. LISTAR LIBROS REGISTRADOS
                3. LISTAR AUTORES REGISTRADOS
                4. LISTAR AUTORES VIVOS EN DETERMINADO AÑO
                5. LISTAR LIBROS POR IDIOMA
                
                0. SALIR
                ------------------------------------------------------------------------------------------------""");
    }

    private int leerOpcion(){
        try {
           return Integer.parseInt(leerEntrada());
        } catch (NumberFormatException e) {
           imprimirMensaje("ERROR: SOLO PUEDE INSERTAR NÚMEROS");
           return -1;
        }
    }

    private void procesarMenuInicial(int opcion){
        imprimirMenuInicial();

        if (opcion > 5){imprimirMensaje("NUMERO MAYOR A LAS OPCIONES"); return;}
        switch (opcion) {
            case -1 -> imprimirMensaje("");
            case 0 -> salir();
            case 1 -> agregarNuevoLibro();
            case 2 -> listarLibrosRegistrados();
            case 3 -> listarAutoresRegistrados();
            case 4 -> listarAutoresVivosPorAño();
            case 5 -> listarLibrosPorIdioma();
            default -> imprimirMensaje("INTENTE DE NUEVO (OPCIONES VALIDAS 0-5)");
        }
    }

    // ACCIONES ASOCIADAS A CADA OPCIÓN
    private void salir() {
        imprimirMensaje("GRACIAS POR USAR LITERALURA");
        isRun = false;
    }

    private void agregarNuevoLibro() {
        buscarLibroEnApi();
    }

    private void listarLibrosRegistrados() {
        imprimirMensaje("Funcionalidad para listar libros registrados (por implementar)");
    }

    private void listarAutoresRegistrados() {
        imprimirMensaje("Funcionalidad para listar autores registrados (por implementar)");
    }

    private void listarAutoresVivosPorAño() {
        imprimirMensaje("Funcionalidad para listar autores vivos en un año (por implementar)");
    }

    private void listarLibrosPorIdioma() {
        imprimirMensaje("Funcionalidad para listar libros por idioma (por implementar)");
    }

    private String construirUrl(String nombreLibro){
        String nombreLibroFormateado = nombreLibro.replace(" ", "%20");
        return HOST + PATH_BUSQUEDA + nombreLibroFormateado + PATH_PAGINA + paginaActual;
    }

    private void buscarLibroEnApi() {
        String url = obtenerUrlBusqueda();

        if (url == null) return;  // Si no hay URL, se detiene la búsqueda

        String json = conexion.conectar(url);
        DataBusqueda resultadoBusqueda = conversor.convertir(json, DataBusqueda.class);
        ultimaBusqueda = resultadoBusqueda;

        mostrarLibrosEncontrados(resultadoBusqueda);
        procesarMenuBusqueda(leerOpcion());
    }

    private String obtenerUrlBusqueda() {
        if (isFirstSearch) {
            imprimirMensaje("INGRESE EL NOMBRE DEL LIBRO A BUSCAR");
            String libroABuscar = leerEntrada();
            return construirUrl(libroABuscar);
        } else {
            try {
                return ultimaBusqueda.urlsiguiente();
            } catch (NullPointerException e) {
                imprimirMensaje("No hay más páginas");
                return null;
            }
        }
    }

    private void imprimirMenuBusqueda(){
        System.out.println("""
                * INSERTE UNA OPCIÓN *
                
                1. SIGUIENTE PAGINA
                2. GUARDAR UN LIBRO
                3. REALIZAR OTRA BUSQUEDA
                
                0. SALIR
                ------------------------------------------------------------------------------------------------""");
    }

    private void procesarMenuBusqueda(int opcion){
        imprimirMenuBusqueda();

        switch (opcion) {
            case -1 -> imprimirMensaje("");
            case 0 -> salir();
            case 1 -> irSiguientePagina();
            case 2 -> guardarLibro();
            case 3 -> iniciar();
            default -> imprimirMensaje("INTENTE DE NUEVO (OPCIONES VALIDAS 0-3)");
        }
    }

    private void guardarLibro() {
        imprimirMensaje("INGRESE EL ID DEL LIBRO A GUARDAR");
        int id = leerOpcion();

        if (id == -1) {
            imprimirMensaje("ID INVÁLIDO. OPERACIÓN CANCELADA.");
            return;
        }

        // Buscar el libro con el ID ingresado
        DataLibro libroEscogido = ultimaBusqueda.libros().stream()
                .filter(l -> l.gutembergID() == id)
                .findFirst()
                .orElse(null);
        imprimirMensaje("EL LIBRO ESCOGIDO ES:");

        if (libroEscogido != null) {
            // Aquí se realiza la acción de guardar el libro
            imprimirMensaje("LIBRO GUARDADO: " + libroEscogido.titulo());
            // guardarEnColeccion(libroEscogido);  // Método para guardar el libro
        } else {
            imprimirMensaje("NO SE ENCONTRÓ UN LIBRO CON ESE ID.");
        }
    }


    private void irSiguientePagina(){
        if (ultimaBusqueda.urlsiguiente() == null) {
            imprimirMensaje("NO HAY MAS PÁGINAS");

            procesarMenuBusqueda(leerOpcion());
        } else {
            isFirstSearch = false;
            paginaActual++;
            buscarLibroEnApi();
        }
    }
    private void mostrarLibrosEncontrados(DataBusqueda dataBusqueda){
        List<DataLibro> libros = dataBusqueda.libros();
        int librosTotales = dataBusqueda.resultados();
        int librosObtenidos = libros.size();
        int paginasTotales = librosTotales / librosObtenidos;
        System.out.printf("""
                        ------------------------------------------------------------------------------------------------
                        Libros coincidentes encontrados: %d | mostrando: %d
                        ------------------------------------------------------------------------------------------------%n""",
                librosTotales,
                librosObtenidos);
        libros.forEach(System.out::println);
        System.out.printf("""
                        Página: %d de %d
                        ------------------------------------------------------------------------------------------------%n""",
                paginaActual,
                paginasTotales);
    }

    private String leerEntrada(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void imprimirMensaje(String texto){
        System.out.printf("""
                    ------------------------------------------------------------------------------------------------
                    * %s *
                    ------------------------------------------------------------------------------------------------%n""", texto);
    }
}
