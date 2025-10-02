package ejercicios;

import java.io.File;
import java.util.Scanner;

public class Ejercicio9 {
	public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Introduce la ruta del fichero a borrar: ");
            String ruta = sc.nextLine();

            File fichero = new File(ruta);

            if (!fichero.exists() || !fichero.isFile()) {
                System.out.println("El fichero no existe o no es un fichero válido.");
                return; // salimos del programa
            }

            System.out.print("¿Seguro que deseas borrar el fichero " + fichero.getName() + "? (s/n): ");
            String respuesta = sc.nextLine();

            if (respuesta.equalsIgnoreCase("s")) {
                boolean borrado = fichero.delete();
                if (borrado) {
                    System.out.println("Fichero borrado correctamente.");
                } else {
                    System.out.println("No se pudo borrar el fichero.");
                }
            } else {
                System.out.println("Operación cancelada. El fichero no se borró.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            sc.close();
            System.out.println("Programa finalizado.");
        }
    }
}
