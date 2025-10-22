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
	
	//Lo declaramos como final porque no cambiará durante la ejecución del programa
	//Si más adelante necesitaramos cambiar el nombre del fichero solo lo haces en una línea
	private static final String FICHERO = "alumnos.bin"; //También evita errores, nadie puede reasignarle otro valor accidentalmente

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner (System.in);
		//Utilizamos set porque es una colección que no permite elementos duplicados
		//A diferencia de una List donde puedes tener el mismo objeto repetido varias veces
		//Un set garantiza unicidad
		
		Set<AlumnoEj29> alumnos = leerFichero();
		//En este programa tiene sentido porque gestionamos alumnos y cada uno tiene un expediente único
		//Por tanto no tiene sentido tener dos alumnos con el mismo expediente
		//Usar set ayuda a reforzar esto automáticamente
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
		//Creamos un objeto file que representa el fichero alumnos.bin
		File ficheroLeer = new File(FICHERO);
		//Si el archivo no existe aún File no lo crea solo lo señala
		
		//Se crea una colección vacía para almacenar los alumnos que se leerán del fichero
		//Si el fichero no existe o no se puede leer devolverá esta colección, vacía (funcional)
		Set<AlumnoEj29> alumnosLeer= new LinkedHashSet<>(); 
		//Comprueba que el fichero existe
		//Si no existe simplemente devolverá la colección vacía
		if(!ficheroLeer.exists()) {
			return alumnosLeer; //Esto evita errores la primera vez que ejecutamos el programa
		}
		
		//Aquí empieza la lectura del fichero binario usando serialización de objetos
		//FileInputStream abre un flujo de lectura hacia el objeto binario
		//Si existe abre un ois que permite leer objetos Java completos, no solo bytes
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheroLeer))){
			//Se lee el objeto completo que está guardado en el fichero
			//readObject() devuelve un Object por eso necesita un casting
			alumnosLeer = (Set <AlumnoEj29>)  ois.readObject();
			//Es importante recordar que esto funciona porque AlumnoEj29 implementa Serializable
		}catch(IOException | ClassNotFoundException e) {
			//IOException captura errores al acceder al archivo
			//ClassNotFoundException ocurre si el programa intenta leer un objeto de una clase que no existe en el código actual
			System.out.println("Error al leer el fichero: "+e.getMessage());
			e.printStackTrace();
		}
		//Devuelve el conjunto cargado o vacío en caso de que no exista u ocurra algún problema
		return alumnosLeer;
	}
	
	private static void guardarFichero(Set<AlumnoEj29>alumnos) {
		
		//Aquí abrimos dos flujos encadenados
		//FileOutputStream crea o sobreescribe el archivo binario indicado, es el flujo de bytes que conecta con el archivo físico
		//ObjectOutputStream envuelve al anterior y permite escribir objetos Java completos
		//Es lo opuesto al ObjectOutputStream que se usa para leer
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FICHERO))) { //FOS es el canal al archivo y OOS convierte el objeto Java en una secuencia de bytes para guardarlo
			oos.writeObject(alumnos); //Este es el paso clave, escribe el objeto alumnos(Set<AlumnoEj29> completo)
			//Java internamente serializa cada alumno del conjunto
			//Convierte el objeto entero en una secuencia binaria
			//Y la guarda en el fichero alumnos.bin
		}catch(IOException e) { //Captura cualquier posible error
			System.out.println("Error al guardar el fichero: "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	private static void anadirAlumno(Scanner sc, Set<AlumnoEj29>alumnos) {
		//Pedimos el número de Expediente que es único para cada alumno
		System.out.println("Introduce el número del expediente a añadir:");
		int expedienteAnadir= sc.nextInt(); //Lee el número
		sc.nextLine();//Limpia el salto de línea que queda en el buffer para evitar errores
		
		//Comprobamos si ya existe un alumno con ese expediente
		for(AlumnoEj29 a: alumnos) { //Se recorre el set de alumnos actual
			//Si encuentra uno con el que el usuario intenta añadir se muestra un aviso y se usa return 
			//Así salimos del método inmediatamente sin añadir nada
			if(a.getExpediente()==expedienteAnadir) {
				//Aunque Set ya evita duplicados aquí se comprueba de forma explícita para mostrar un mensaje claro al usuario
				System.out.println("Ya existe un alumno con ese expediente.");
				return;
			}
		}
		
		//Si el expediente no existe se pide el nombre del nuevo alumno al usuario
		//Y se guarda en una variable temporal
		System.out.println("Introduce el nombre del alumno a añadir:");
		String nombreAlumnoAnadir = sc.nextLine();
		
		//Se crea un nuevo alumno con el expediente y nombre introducido,
		//La nota inicial será -1, que indica que todavía no tiene nota asignada
		//Usamos -1 porque está fuera del rango válido, puesto que las notas van del 0-10
		//Podríamos usar otro pero -1 es convencional y claro
		alumnos.add(new AlumnoEj29(expedienteAnadir,nombreAlumnoAnadir,-1));
		//Se añade el objeto al set y si todo va bien se muestra un mensaje de confirmación
		System.out.println("Alumno añadido correctamente");
		
	}
	
	private static void mostrarAlumnos(Set<AlumnoEj29>alumnos) {
		//Primero comprobamos si la colección está vacía
		//Evitamos iterar sobre una colección vacía y mostramos un mensaje claro
		if(alumnos.isEmpty()) {
			System.out.println("No hay alumnos registrados.");
		}else {
			System.out.println("-----LISTA DE ALUMNOS------");
			//Iteramos por todos los elementos del Set en orden de inserción porque es LinkedHashSet
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
		//Primero comprobamos si la colección está vacía
		//Si está vacía no tiene sentido mostrar ninguna estadística
		if(alumnos.isEmpty()) {
			System.out.println("No hay alumnos registrados.");
			return;
		}
		
		//Inicialización de variables
		int suspensos =0; //Cuenta las notas <5
		int aprobados=0;//Las notas >5
		int sumaNotas=0;//La suma acumulada de todas las notas válidas para cacular la media
		int alumnosConNota=0;//Cuantos alumnos tienen nota asignada (No -1)
		
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
