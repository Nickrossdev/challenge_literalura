package com.alura.literalura.app;

import java.util.Scanner;

public class MenuPrincipal {
    private MenuAgregarLibro menuAgregarLibro = new MenuAgregarLibro();
    private boolean isRunning = true;

    public void mostrarMenu() {
        while (isRunning) {
            System.out.println("""
                    * INSERTE UNA OPCIÓN *
                    ------------------------------------------------------------------------------------------------
                    1. AGREGAR NUEVO LIBRO
                    2. LISTAR LIBROS REGISTRADOS
                    3. LISTAR AUTORES REGISTRADOS
                    4. LISTAR AUTORES VIVOS EN DETERMINADO AÑO
                    5. LISTAR LIBROS POR IDIOMA
                    
                    0. SALIR
                    ------------------------------------------------------------------------------------------------""");
            Integer opcion = leerOpcion();
            if (opcion == null) return;
            switch (opcion) {
                case 1 -> menuAgregarLibro.mostrarMenuGuardarLibro();
                case 2 -> System.out.println("xd");
                case 0 -> salir();
                default -> System.out.println("Opción no válida.");
            }
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
        System.out.println("¡Gracias por usar la aplicación!");
        isRunning = false;
    }
}
