package ejercicios;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestionEmpleadosEj27 {
	
	private static final String FICHERO = "empleado.bin";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		List<EmpleadoEj26> empleados = leerFichero();
		
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
            
            switch(opcion) {
            	case 1:
            		break;
            	case 2:
                	break;
            	case 3:
                	break;
            	case 4:
                	break;
            	case 5:
            		System.out.println("Saliendo...");
                	break;
            	default:
            		System.out.println("Opción no válida");
            		break;
            }
			
		}while(opcion!=5);
		
		sc.close();
		
		
	}
	
	private static void anadirEmpleado(Scanner sc, List<EmpleadoEj26> empleados) {
		System.out.println("Introduce el DNI: ");
		String dni = sc.nextLine();
		
		//Ev
	}
	
	private static List<EmpleadoEj26> leerFichero() {
		File ficheroLeer = new File(FICHERO);
		
		if(!ficheroLeer.exists()) {
			return new ArrayList<>();
		}
		
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheroLeer))){
			return (List<EmpleadoEj26>) ois.readObject();
			
		}catch(IOException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return new ArrayList<>();
		}
		
	}

}
