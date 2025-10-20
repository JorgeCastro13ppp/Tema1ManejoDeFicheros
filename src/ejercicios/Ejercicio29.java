package ejercicios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class Ejercicio29 {
	
	private static final String FICHERO = "alumnos.bin";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner (System.in);
		Set<AlumnoEj29> alumnos = leerFichero();
		int opcion;
		
		do {
			System.out.println("\n--- GESTIÓN DE ALUMNOS ---");
            System.out.println("1. Añadir alumno");
            System.out.println("2. Mostrar alumnos");
            System.out.println("3. Poner nota");
            System.out.println("4. Mostrar estadística");
            System.out.println("5. Borrar alumno");
            System.out.println("6. Salir");
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
            	break;
            case 6:
            	System.out.println("Saliendo...");
            	break;
            default:
            	System.out.println("Opción no válida.");
            	break;
            }
            
            guardarFichero(alumnos);
			
		}while(opcion!=6);
		
		sc.close();

	}
	
	private static Set<AlumnoEj29> leerFichero(){
		File ficheroLeer = new File(FICHERO);
		Set<AlumnoEj29> alumnosLeer= new LinkedHashSet<>(); 
		
		if(!ficheroLeer.exists()) {
			return alumnosLeer;
		}
		
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheroLeer))){
			alumnosLeer = (Set <AlumnoEj29>)  ois.readObject();
		}catch(IOException | ClassNotFoundException e) {
			System.out.println("Error al leer el fichero: "+e.getMessage());
			e.printStackTrace();
		}
		
		return alumnosLeer;
	}
	
	private static void guardarFichero(Set<AlumnoEj29>alumnos) {
		
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FICHERO))) {
			oos.writeObject(alumnos);
		}catch(IOException e) {
			System.out.println("Error al guardar el fichero: "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	private static void anadirAlumno(Scanner sc, Set<AlumnoEj29>alumnos) {
		System.out.println("Introduce el número del expediente a añadir:");
		int expedienteAnadir= sc.nextInt();
		sc.nextLine();
		
		for(AlumnoEj29 a: alumnos) {
			if(a.getExpediente()==expedienteAnadir) {
				System.out.println("Ya existe un alumno con ese expediente.");
				return;
			}
		}
		
		System.out.println("Introduce el nombre del alumno a añadir:");
		String nombreAlumnoAnadir = sc.nextLine();
		
		System.out.println("Introduce nota a añadir:");
		
	}

	
}
