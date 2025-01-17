package com.alura.literalura.app;

import com.alura.literalura.api.Conexion;
import com.alura.literalura.api.ConversorGenerico;
import com.alura.literalura.api.DataBusqueda;
import com.alura.literalura.api.DataLibro;

import java.util.List;
import java.util.Scanner;

public class MenuAgregarLibro {
    private final Conexion conexion = new Conexion();
    private final ConversorGenerico conversor = new ConversorGenerico();

    private int pagina = 1;
    private boolean estaEjecutandose = true;
    private DataBusqueda ultimaBusqueda;
    private String nombreLibro;

    public void mostrarMenuGuardarLibro() {
        buscarNuevoLibro();  // Iniciar directamente con la búsqueda

        while (estaEjecutandose) {
            mostrarOpcionesMenu();
            Integer opcion = leerOpcion();
            if (opcion == null) continue;

            switch (opcion) {
                case 1 -> irPaginaSiguiente();
                case 2 -> irPaginaAnterior();
                case 3 -> irPaginaEspecifica();
                case 4 -> guardarLibro();
                case 5 -> buscarNuevoLibro();
                case 0 -> salir();
                default -> imprimirMensaje("Opción no válida.");
            }
        }
    }

    private void mostrarOpcionesMenu() {
        System.out.println("""
                * INSERTE UNA OPCIÓN *
                
                1. SIGUIENTE PÁGINA
                2. PÁGINA ANTERIOR
                3. PÁGINA ESPECÍFICA
                4. GUARDAR UN LIBRO
                5. REALIZAR OTRA BÚSQUEDA
                
                0. SALIR
                ------------------------------------------------------------------------------------------------""");
    }

    private void irPaginaSiguiente() {
        if (ultimaBusqueda.urlsiguiente() != null) {
            pagina++;
            obtenerDatos(ultimaBusqueda.urlsiguiente());
        } else {
            imprimirMensaje("NO HAY MÁS PÁGINAS SIGUIENTES");
        }
    }

    private void irPaginaAnterior() {
        if (ultimaBusqueda.urlAnterior() != null) {
            pagina--;
            obtenerDatos(ultimaBusqueda.urlAnterior());
        } else {
            imprimirMensaje("NO HAY MÁS PÁGINAS ANTERIORES");
        }
    }

    private void irPaginaEspecifica() {
        imprimirMensaje("INGRESE EL NÚMERO DE PÁGINA");
        Integer paginaEspecifica = leerOpcion();
        if (paginaEspecifica == null) return;

        if (paginaEspecifica <= 0 || paginaEspecifica > ultimaBusqueda.resultados()) {
            imprimirMensaje("INGRESE UNA PÁGINA EXISTENTE | 1 - " + ultimaBusqueda.resultados());
        } else {
            pagina = paginaEspecifica;
            String url = construirUrl(nombreLibro);
            obtenerDatos(url);
        }
    }

    private void guardarLibro() {
        imprimirMensaje("INGRESE EL NÚMERO DEL LIBRO A GUARDAR");
        Integer seleccion = leerOpcion();
        if (seleccion == null) return;

        List<DataLibro> libros = ultimaBusqueda.libros();
        if (seleccion < 1 || seleccion > libros.size()) {
            imprimirMensaje("SELECCIÓN NO VÁLIDA.");
        } else {
            DataLibro libroSeleccionado = libros.get(seleccion - 1);
            // Aquí implementas la lógica para guardar el libro
            System.out.println("Libro guardado: " + libroSeleccionado.titulo());
        }
    }

    public void buscarNuevoLibro() {
        pagina = 1;
        imprimirMensaje("INGRESE EL NOMBRE DEL LIBRO A BUSCAR");
        nombreLibro = leerEntrada();
        String url = construirUrl(nombreLibro);
        obtenerDatos(url);
    }

    private void obtenerDatos(String url) {
        String json = conexion.conectar(url);
        ultimaBusqueda = conversor.convertir(json, DataBusqueda.class);

        mostrarLibrosEncontrados(ultimaBusqueda);
    }

    private void mostrarLibrosEncontrados(DataBusqueda dataBusqueda) {
        List<DataLibro> libros = dataBusqueda.libros();
        System.out.printf("""
                        ------------------------------------------------------------------------------------------------
                        Libros encontrados: %d | Mostrando página: %d
                        ------------------------------------------------------------------------------------------------%n""",
                dataBusqueda.resultados(), pagina);

        for (int i = 0; i < libros.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, libros.get(i).titulo());
        }

    }

    private Integer leerOpcion() {
        try {
            System.out.print("Seleccione una opción: ");
            return new Scanner(System.in).nextInt();
        } catch (Exception e) {
            System.out.println("ERROR: Solo números.");
            return null;
        }
    }

    private void salir() {
        imprimirMensaje("¡Gracias por usar la aplicación!");
        estaEjecutandose = false;
    }

    private void imprimirMensaje(String texto) {
        System.out.printf("""
                    ------------------------------------------------------------------------------------------------
                    * %s *
                    ------------------------------------------------------------------------------------------------%n""", texto);
    }

    private String leerEntrada() {
        return new Scanner(System.in).nextLine();
    }

    private String construirUrl(String nombreLibro) {
        String nombreLibroFormateado = nombreLibro.replace(" ", "%20");
        String HOST = "https://gutendex.com/books/?";
        String PATH_BUSQUEDA = "search=";
        String PATH_PAGINA = "&page=";
        return HOST + PATH_BUSQUEDA + nombreLibroFormateado + PATH_PAGINA + pagina;
    }
}
