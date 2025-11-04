package ejercicios;

import java.io.File;

/**
 * Ejercicio7 — Lista de ficheros introducidos por teclado.
 * Para cada fichero muestra:
 *  - Si existe
 *  - Si es fichero o carpeta
 *  - Ruta absoluta
 *  - Permisos (rwx)
 *  - Tamaño en bytes
 *
 * Mejora: evitamos el uso de while(true) y usamos una condición clara.
 */
import java.util.ArrayList;
import java.util.Scanner;

public class Ejercicio7 {
	
	/**
     * Devuelve una cadena con los permisos del fichero en formato "rwe"
     * (r = lectura, w = escritura, e = ejecución). Si no tiene permiso
     * aparece '-' en la posición correspondiente.
     */
	
	private static String obtenerPermisos(File archivoNuevo) {
		StringBuilder permisos = new StringBuilder();
		
		// operador ternario para construir la representación de permisos
		permisos.append(archivoNuevo.canRead() ? "r" : "-");   // lectura?
        permisos.append(archivoNuevo.canWrite() ? "w" : "-");  // escritura?
        permisos.append(archivoNuevo.canExecute() ? "x" : "-");// ejecución?
		
		return permisos.toString();
	}

	 public static void main(String[] args) {
	        Scanner sc = new Scanner(System.in);

	        try {
	            ArrayList<String> ficherosLista = new ArrayList<>();
	            String nombre = ""; // inicializamos vacío para poder entrar en el while

	            /*
	             * CAMBIO PRINCIPAL:
	             * En lugar de usar while(true) + break, usamos un bucle con condición
	             * explícita: mientras el nombre introducido NO sea "salir".
	             *
	             * Ventajas:
	             *  - Código más claro (la condición de terminación está en la cabecera).
	             *  - Evitamos bucles infinitos por accidente.
	             *  - Facilita leer y mantener el código.
	             */
	            while (!nombre.equalsIgnoreCase("salir")) {
	                System.out.println("Nombre del fichero (escribe 'salir' para terminar):");
	                nombre = sc.nextLine();

	                // Si el usuario no ha escrito "salir", añadimos a la lista
	                if (!nombre.equalsIgnoreCase("salir")) {
	                    ficherosLista.add(nombre);
	                    System.out.println("Fichero añadido.");
	                }
	                // Si escribió "salir", el bucle terminará al evaluar la condición
	            }

	            System.out.println("\n--- INFORMACIÓN DE FICHEROS ---");

	            // Recorremos la lista de rutas introducidas y mostramos la información solicitada
	            for (String ficheroNombre : ficherosLista) {
	                File archivoNuevo = new File(ficheroNombre);

	                // Encabezado para cada fichero (más claro en la salida)
	                System.out.println("\nFichero: " + ficheroNombre);
	                System.out.println("----------------------------");

	                // Primero comprobamos si existe (archivo o carpeta)
	                if (archivoNuevo.exists()) {

	                    // Indicamos si es fichero o carpeta usando isFile/isDirectory
	                    if (archivoNuevo.isFile()) {
	                        System.out.println("Tipo: Es un fichero.");
	                    } else if (archivoNuevo.isDirectory()) {
	                        System.out.println("Tipo: Es una carpeta.");
	                    } else {
	                        // Rara vez sucede, pero queda para completitud
	                        System.out.println("Tipo: Existe pero no es fichero ni carpeta reconocible.");
	                    }

	                    // Ruta absoluta del archivo/carpeta
	                    System.out.println("Ruta absoluta: " + archivoNuevo.getAbsolutePath());

	                    // Permisos con el método auxiliar (r/w/x o '-' si no tiene)
	                    System.out.println("Permisos (rwx): " + obtenerPermisos(archivoNuevo));

	                    // Tamaño en bytes (si es fichero). Para carpetas la API devuelve un valor que no es
	                    // el "tamaño total del contenido", por eso normalmente se muestra solo si es fichero.
	                    if (archivoNuevo.isFile()) {
	                        System.out.println("Tamaño: " + archivoNuevo.length() + " bytes");
	                    } else {
	                        // Opcional: indicar que no aplicable o que se puede calcular recursivamente
	                        System.out.println("Tamaño: (no aplicable para carpetas con File.length())");
	                    }

	                } else {
	                    // Si no existe mostramos mensaje claro
	                    System.out.println("El fichero o carpeta no existe.");
	                }

	                System.out.println("----------------------------");
	            }

	        } catch (Exception e) {
	            // Capturamos cualquier excepción inesperada y mostramos mensaje simple
	            System.out.println("Error: " + e.getMessage());
	        } finally {
	            // Cerramos el Scanner siempre para evitar warnings/resources leak
	            sc.close();
	        }
	    }

}
