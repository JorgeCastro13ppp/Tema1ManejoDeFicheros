package ejercicios;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Ejercicio10 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Introduce la ruta del directorio: ");
            String ruta = sc.nextLine();

            File directorio = new File(ruta);

            if (!directorio.exists() || !directorio.isDirectory()) {
                System.out.println("La ruta no existe o no es un directorio válido.");
                return;
            }

            File[] archivos = directorio.listFiles();

            if (archivos == null || archivos.length == 0) {
                System.out.println("El directorio está vacío.");
                return;
            }

            // Ordenar por tamaño
            Arrays.sort(archivos, Comparator.comparingLong(File::length));

            // Mostrar información
            for (File archivo : archivos) {
                if (archivo.isFile()) {
                    System.out.println(archivo.getName() + " " + archivo.length() + " bytes");
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            sc.close();
            System.out.println("Programa finalizado.");
        }
	}

}
