package ejercicios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Ejercicio17 — Modificar una línea concreta de un fichero de texto.
 *
 * En esta versión evitamos el uso de ArrayList (memoria) y usamos un fichero temporal.
 * Flujo:
 *  1) Pedir ruta del fichero y comprobar existencia.
 *  2) Mostrar el contenido numerado para que el usuario vea las líneas.
 *  3) Pedir el número de línea a modificar y validar.
 *  4) Pedir el nuevo contenido.
 *  5) Crear un fichero temporal, copiar todas las líneas desde el original,
 *     sustituyendo la línea elegida por la nueva.
 *  6) Reemplazar el fichero original por el temporal (borrar + renombrar).
 */
public class Ejercicio17 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            // 1) Pedir el nombre/ruta del fichero a modificar
            System.out.println("Introduce el nombre del fichero:");
            String ficheroModificar = sc.nextLine();

            File fichero = new File(ficheroModificar);

            // Comprobación rápida: ¿existe el fichero?
            if (!fichero.exists()) {
                System.out.println("El fichero no existe.");
                return; // Salimos porque no hay nada que modificar
            }

            // 2) Mostramos el contenido del fichero numerado para que el usuario elija la línea
            //    Lo hacemos en una pasada separada: menos complejidad y no tenemos que almacenar nada.
            int totalLineas = 0;
            System.out.println("Contenido del fichero:");
            try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
                String linea;
                int contador = 1;
                while ((linea = br.readLine()) != null) {
                    // Mostramos línea numerada para que el usuario sepa cuál elegir
                    System.out.println(contador + ". " + linea);
                    contador++;
                }
                totalLineas = contador - 1; // contador se incrementó una vez más al salir del while
            } catch (IOException e) {
                // Si hay error al leer en esta fase, informamos y salimos
                System.out.println("Error al leer el fichero: " + e.getMessage());
                return;
            }

            // Si el fichero está vacío, no tiene sentido pedir línea a modificar
            if (totalLineas == 0) {
                System.out.println("El fichero está vacío. No hay líneas que modificar.");
                return;
            }

            // 3) Pedir el número de línea a modificar y validar rango
            System.out.println("Introduce número de línea a modificar (1 - " + totalLineas + "):");
            int lineaModificar;
            try {
                lineaModificar = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException nfe) {
                System.out.println("Número de línea inválido (no es un número).");
                return;
            }

            if (lineaModificar < 1 || lineaModificar > totalLineas) {
                System.out.println("Número de línea no válido.");
                return;
            }

            // 4) Pedir el nuevo contenido para esa línea
            System.out.println("Introduce el nuevo contenido para la línea:");
            String nuevaLinea = sc.nextLine();

            // 5) Copiar a un fichero temporal, reemplazando la línea indicada
            // Construimos un fichero temporal en la misma carpeta que el original
            File ficheroTemp = new File(fichero.getParent(), fichero.getName() + ".tmp");

            // try-with-resources para asegurar cierre de flujos
            try (BufferedReader br = new BufferedReader(new FileReader(fichero));
                 PrintWriter pw = new PrintWriter(new FileWriter(ficheroTemp, false))) {

                String linea;
                int contador = 1;
                while ((linea = br.readLine()) != null) {
                    if (contador == lineaModificar) {
                        // Es la línea a modificar: escribimos la nueva línea en el temporal
                        pw.println(nuevaLinea);
                    } else {
                        // No es la línea a modificar: copiamos la original tal cual
                        pw.println(linea);
                    }
                    contador++;
                }
                // Al salir del try-with-resources, br y pw se cierran automáticamente
            } catch (IOException e) {
                // Si ocurre un error al escribir el temporal, lo informamos y abortamos
                System.out.println("Error al escribir el fichero temporal: " + e.getMessage());
                // Intentamos borrar el temporal si existe (limpieza)
                if (ficheroTemp.exists()) {
                    ficheroTemp.delete();
                }
                return;
            }

            // 6) Reemplazar fichero original por temporal:
            // Primero intentamos borrar el original; si no se puede, informar error.
            if (!fichero.delete()) {
                System.out.println("No se pudo borrar el fichero original. Operación abortada.");
                // Intentamos borrar el temporal para no dejar basura
                ficheroTemp.delete();
                return;
            }

            // Intentamos renombrar el temporal al nombre original
            if (!ficheroTemp.renameTo(fichero)) {
                System.out.println("No se pudo renombrar el fichero temporal. Operación abortada.");
                // Si falla, intentamos restaurar alguna consistencia (opcional)
                return;
            }

            // Si hemos llegado aquí, todo fue correcto
            System.out.println("Fichero modificado correctamente.");
            // (Podríamos mostrar el fichero resultante si queremos)

        } finally {
            // Cerramos scanner siempre en el finally para liberar recurso
            sc.close();
        }
    }
}
