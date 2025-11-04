package ejercicios;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class GestionEmpleadosEj27 {
	
	 private static final String FICHERO = "empleado.bin";

	    public static void main(String[] args) {
	        Scanner sc = new Scanner(System.in);
	        int opcion;

	        do {
	            System.out.println("\n--- GESTIÓN DE EMPLEADOS ---");
	            System.out.println("1. Dar de alta empleado");
	            System.out.println("2. Buscar empleado por DNI");
	            System.out.println("3. Listar empleados");
	            System.out.println("4. Borrar empleado");
	            System.out.println("5. Salir");
	            System.out.print("Opción: ");
	            opcion = sc.nextInt();
	            sc.nextLine();

	            switch (opcion) {
	                case 1 -> anadirEmpleado(sc);
	                case 2 -> buscarEmpleado(sc);
	                case 3 -> listarEmpleados();
	                case 4 -> borrarEmpleado(sc);
	                case 5 -> System.out.println("Saliendo...");
	                default -> System.out.println("Opción no válida.");
	            }
	        } while (opcion != 5);

	        sc.close();
	    }

	    private static void anadirEmpleado(Scanner sc) {
	        System.out.println("Introduce el DNI:");
	        String dni = sc.nextLine();
	        System.out.println("Introduce el nombre:");
	        String nombre = sc.nextLine();
	        System.out.println("Introduce el sueldo:");
	        double sueldo = Double.parseDouble(sc.nextLine().replace(',', '.'));

	        EmpleadoEj26 nuevo = new EmpleadoEj26(dni, nombre, sueldo);

	        try (ObjectOutputStream oos = new MiObjectOutputStream(
	                new FileOutputStream(FICHERO, true))) {
	            oos.writeObject(nuevo);
	            System.out.println("Empleado añadido correctamente.");
	        } catch (IOException e) {
	            System.out.println("Error al guardar el empleado: " + e.getMessage());
	        }
	    }

	    // Clase interna auxiliar para evitar duplicar cabecera del stream al añadir
	    static class MiObjectOutputStream extends ObjectOutputStream {
	        MiObjectOutputStream(OutputStream out) throws IOException { super(out); }
	        @Override protected void writeStreamHeader() throws IOException { reset(); }
	    }

	    private static void listarEmpleados() {
	        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FICHERO))) {
	            while (true) {
	                try {
	                    EmpleadoEj26 e = (EmpleadoEj26) ois.readObject();
	                    System.out.println(e);
	                } catch (EOFException fin) {
	                    break;
	                }
	            }
	        } catch (IOException | ClassNotFoundException e) {
	            System.out.println("No se pudieron listar los empleados: " + e.getMessage());
	        }
	    }

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
	                        break;
	                    }
	                } catch (EOFException fin) {
	                    break;
	                }
	            }
	        } catch (IOException | ClassNotFoundException e) {
	            System.out.println("Error al buscar empleado: " + e.getMessage());
	        }
	        if (!encontrado) System.out.println("No existe un empleado con ese DNI.");
	    }

	    private static void borrarEmpleado(Scanner sc) {
	        System.out.println("Introduce el DNI del empleado a borrar:");
	        String dniBorrar = sc.nextLine();

	        File original = new File(FICHERO);
	        File temporal = new File("empleado_tmp.bin");

	        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(original));
	             ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(temporal))) {

	            boolean encontrado = false;

	            while (true) {
	                try {
	                    EmpleadoEj26 e = (EmpleadoEj26) ois.readObject();
	                    if (e.getDni().equalsIgnoreCase(dniBorrar)) {
	                        encontrado = true;
	                    } else {
	                        oos.writeObject(e);
	                    }
	                } catch (EOFException fin) {
	                    break;
	                }
	            }

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
