package ejercicios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
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
            	anadirAlumno(sc,alumnos);
            	break;
            case 2:
            	mostrarAlumnos(alumnos);
            	break;
            case 3:
            	ponerNota(sc,alumnos);
            	break;
            case 4:
            	mostrarEstadisticas(alumnos);
            	break;
            case 5:
            	borrarAlumno(sc,alumnos);
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
		
		alumnos.add(new AlumnoEj29(expedienteAnadir,nombreAlumnoAnadir,-1));
		System.out.println("Alumno añadido correctamente");
		
	}
	
	private static void mostrarAlumnos(Set<AlumnoEj29>alumnos) {
		if(alumnos.isEmpty()) {
			System.out.println("No hay alumnos registrados.");
		}else {
			System.out.println("-----LISTA DE ALUMNOS------");
			for(AlumnoEj29 a:alumnos) {
				System.out.println(a);
			}
		}
	}
	
	private static void ponerNota(Scanner sc,Set<AlumnoEj29>alumnos) {
		System.out.println("Introduce el expediente del alumno:");
		int expedienteNota = sc.nextInt();
		sc.nextLine();
		
		for(AlumnoEj29 a:alumnos) {
			if(a.getExpediente() == expedienteNota) {
				System.out.println("Introduce la nota:");
				String notaStr = sc.nextLine().replace(",", ".");
				
				try {
					double notaAnadir = Double.parseDouble(notaStr);
					if(notaAnadir<0||notaAnadir>10) {
						System.out.println("La nota debe estar entre 0 y 10.");
						return;
					}
					a.setNota(notaAnadir);
					System.out.println("Nota actualizada correctamente.");
				}catch(NumberFormatException e) {
					System.out.println("Formato de nota inválido. Usa 7,7 o 7.7 (ej)");
				}
				return;
			}
		}
		System.out.println("No se encontró ningún alumno con ese expediente.");
	}
	
	private static void mostrarEstadisticas(Set<AlumnoEj29>alumnos) {
		if(alumnos.isEmpty()) {
			System.out.println("No hay alumnos registrados.");
			return;
		}
		
		int suspensos =0;
		int aprobados=0;
		int sumaNotas=0;
		int alumnosConNota=0;
		
		for (AlumnoEj29 a: alumnos) {
			if(a.getNota()!= -1) {
				alumnosConNota++;
				sumaNotas += a.getNota();
				if(a.getNota()<5) {
					suspensos++;
				}else {
					aprobados++;
				}
			}
		}
		
		double media = (alumnosConNota>0)?sumaNotas/alumnosConNota:0.0;
		
		System.out.println("\n📊 ESTADÍSTICAS:");
        System.out.println("Suspensos: " + suspensos);
        System.out.println("Aprobados: " + aprobados);
        System.out.printf("Nota media: %.2f%n", media);
	}
	
	private static void borrarAlumno(Scanner sc, Set<AlumnoEj29>alumnos) {
		System.out.println("Introduce el expediente del alumno a borrar:");
		int expedienteBorrar = sc.nextInt();
		sc.nextLine();
		
		Iterator<AlumnoEj29> it = alumnos.iterator();
		
		while(it.hasNext()) {
			AlumnoEj29 ab = it.next();
			if(ab.getExpediente()==expedienteBorrar) {
				it.remove();
				System.out.println("Alumo borrado correctamente.");
				return;
			}
		}
		
		System.out.println("No se encontró ningún alumno con ese expediente.");
		
	}

	
}
