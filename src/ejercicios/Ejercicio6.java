package ejercicios;

import java.io.File;
import java.util.Scanner;

public class Ejercicio6 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Introduce el nombre del fichero a renombrar:");
            String rutaFicheroAntiguo = sc.nextLine();
            File ficheroAntiguo = new File(rutaFicheroAntiguo);

            // Primero comprobamos que existe
            if (ficheroAntiguo.exists() && ficheroAntiguo.isFile()) {
                System.out.println("Introduce un nuevo nombre para el fichero:");
                String archivoNuevo = sc.nextLine();

                File ficheroNuevo = new File(ficheroAntiguo.getParent(), archivoNuevo);

                if (ficheroNuevo.exists()) {
                    System.out.println("Ya existe un fichero con ese nombre.");
                } else {
                    boolean archivoRenombrado = ficheroAntiguo.renameTo(ficheroNuevo);
                    if (archivoRenombrado) {
                        System.out.println("Fichero renombrado con éxito.");
                        System.out.println("Nueva ruta: " + ficheroNuevo.getAbsolutePath());
                    } else {
                        System.out.println("No se pudo renombrar el fichero (puede estar en uso o no tienes permisos).");
                    }
                }
            } else {
                System.out.println("El fichero original no existe o no es un fichero válido.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
