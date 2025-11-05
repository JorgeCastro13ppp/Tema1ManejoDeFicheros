package ejercicios;

import java.io.*;       // Incluye todas las clases de entrada/salida necesarias
import java.util.Scanner;

public class GestionEmpleadosEj27 {

    // --- CONSTANTE ---
    // Definimos el nombre del fichero donde se almacenarán los objetos EmpleadoEj26.
    // Es constante (final) porque no va a cambiar durante la ejecución del programa.
    private static final String FICHERO = "empleado.bin";

    // --- MÉTODO PRINCIPAL ---
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        // Menú principal
        do {
            System.out.println("\n--- GESTIÓN DE EMPLEADOS ---");
            System.out.println("1. Dar de alta empleado");
            System.out.println("2. Buscar empleado por DNI");
            System.out.println("3. Listar empleados");
            System.out.println("4. Borrar empleado");
            System.out.println("5. Salir");
            System.out.print("Opción: ");

            opcion = sc.nextInt();
            sc.nextLine(); // Limpia el salto de línea tras leer el número

            // Nueva sintaxis switch mejorada (Java 14+)
            switch (opcion) {
                case 1 -> anadirEmpleado(sc);
                case 2 -> buscarEmpleado(sc);
                case 3 -> listarEmpleados();
                case 4 -> borrarEmpleado(sc);
                case 5 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 5);

        sc.close(); // Cierre del scanner al salir del programa
    }

    // ============================================================
    //  MÉTODO: AÑADIR EMPLEADO
    // ============================================================

    private static void anadirEmpleado(Scanner sc) {
        // Solicitar los datos del nuevo empleado
        System.out.println("Introduce el DNI:");
        String dni = sc.nextLine();

        System.out.println("Introduce el nombre:");
        String nombre = sc.nextLine();

        System.out.println("Introduce el sueldo:");
        // Reemplazamos comas por puntos para evitar errores de conversión
        double sueldo = Double.parseDouble(sc.nextLine().replace(',', '.'));

        // Creamos el nuevo objeto empleado
        EmpleadoEj26 nuevo = new EmpleadoEj26(dni, nombre, sueldo);

        // --- Escritura en el fichero ---
        try (ObjectOutputStream oos = new MiObjectOutputStream(
                new FileOutputStream(FICHERO, true))) {
            // El segundo parámetro (true) indica "append": añadir al final del archivo

            // writeObject serializa el objeto y lo escribe en binario
            oos.writeObject(nuevo);
            System.out.println("Empleado añadido correctamente.");

        } catch (IOException e) {
            System.out.println("Error al guardar el empleado: " + e.getMessage());
        }
    }

    // ============================================================
    //  CLASE INTERNA: MiObjectOutputStream
    // ============================================================
    // Esta clase sirve para evitar un problema clásico de ObjectOutputStream:
    // Cada vez que abrimos el flujo, escribe una "cabecera" binaria al inicio.
    // Si abrimos el archivo varias veces en modo append, habría múltiples cabeceras
    // y el ObjectInputStream no podría leer el fichero correctamente.
    //
    // Solución → Heredamos ObjectOutputStream y sobreescribimos writeStreamHeader()
    // para evitar escribir la cabecera de nuevo.
    static class MiObjectOutputStream extends ObjectOutputStream {
        MiObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
            // En vez de escribir una nueva cabecera, simplemente reseteamos el flujo
            reset();
        }
    }

    // ============================================================
    //  MÉTODO: LISTAR EMPLEADOS
    // ============================================================

    private static void listarEmpleados() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FICHERO))) {

            // Leemos los objetos hasta llegar al final del archivo
            while (true) {
                try {
                    EmpleadoEj26 e = (EmpleadoEj26) ois.readObject();
                    System.out.println(e);
                } catch (EOFException fin) {
                    // EOFException (End Of File) indica que llegamos al final del archivo
                    break;
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No se pudieron listar los empleados: " + e.getMessage());
        }
    }

    // ============================================================
    //  MÉTODO: BUSCAR EMPLEADO POR DNI
    // ============================================================

    private static void buscarEmpleado(Scanner sc) {
        System.out.println("Introduce el DNI a buscar:");
        String dni = sc.nextLine();
        boolean encontrado = false;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FICHERO))) {
            while (true) {
                try {
                    EmpleadoEj26 e = (EmpleadoEj26) ois.readObject();
                    if (e.getDni().equalsIgnoreCase(dni)) {
                        System.out.println("Empleado encontrado: " + e);
                        encontrado = true;
                        break; // Terminamos si encontramos coincidencia
                    }
                } catch (EOFException fin) {
                    // Fin del fichero sin encontrar el empleado
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al buscar empleado: " + e.getMessage());
        }

        if (!encontrado)
            System.out.println("No existe un empleado con ese DNI.");
    }

    // ============================================================
    //  MÉTODO: BORRAR EMPLEADO
    // ============================================================

    private static void borrarEmpleado(Scanner sc) {
        System.out.println("Introduce el DNI del empleado a borrar:");
        String dniBorrar = sc.nextLine();

        File original = new File(FICHERO);
        File temporal = new File("empleado_tmp.bin"); // Fichero temporal

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(original));
             ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(temporal))) {

            boolean encontrado = false;

            while (true) {
                try {
                    // Leemos cada empleado del archivo original
                    EmpleadoEj26 e = (EmpleadoEj26) ois.readObject();

                    // Si coincide el DNI, no lo copiamos → se borra
                    if (e.getDni().equalsIgnoreCase(dniBorrar)) {
                        encontrado = true;
                    } else {
                        // Copiamos al nuevo archivo todos los demás
                        oos.writeObject(e);
                    }

                } catch (EOFException fin) {
                    break; // Fin de archivo
                }
            }

            // Reemplazamos el archivo original por el temporal
            if (encontrado) {
                System.out.println("Empleado borrado correctamente.");
                original.delete();
                temporal.renameTo(original);
            } else {
                System.out.println("No se encontró ningún empleado con ese DNI.");
                temporal.delete();
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al borrar empleado: " + e.getMessage());
        }
    }
}
