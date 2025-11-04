package ejercicios;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Ejercicio 24 — Lectura de un fichero binario con DataInputStream.
 * 
 * Corregido para evitar el uso de available() y controlar el final del fichero
 * mediante EOFException (fin de archivo).
 */
public class Ejercicio24 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Introduce la ruta del fichero: ");
        String rutaFichero = sc.nextLine();

        // DataInputStream permite leer tipos primitivos (int, double, char, etc.)
        // en el mismo formato binario en que fueron escritos con DataOutputStream.
        try (DataInputStream dis = new DataInputStream(new FileInputStream(rutaFichero))) {
            System.out.println("Registros del fichero:\n");

            // Leemos hasta que se produzca una excepción EOFException,
            // que indica que hemos llegado al final del fichero.
            while (true) {
                try {
                    int expediente = dis.readInt();
                    double nota = dis.readDouble();

                    StringBuilder nombre = new StringBuilder();
                    char c;

                    // Leemos carácter a carácter hasta encontrar '\n'
                    while ((c = dis.readChar()) != '\n') {
                        nombre.append(c);
                    }

                    // Mostrar el registro leído
                    System.out.println("Expediente: " + expediente);
                    System.out.println("Nota: " + nota);
                    System.out.println("Nombre: " + nombre.toString());
                    System.out.println("-----------------------------");

                } catch (EOFException e) {
                    // Se lanza automáticamente cuando llegamos al final del archivo
                    break; // salimos del bucle de lectura
                }
            }

        } catch (IOException e) {
            // Cubre tanto errores de lectura como de apertura de fichero
            System.out.println("Error al leer el fichero: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
