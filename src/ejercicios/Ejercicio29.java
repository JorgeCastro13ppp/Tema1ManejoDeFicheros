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
		//Pedimos el expediente por teclado para apuntar hacia un alumno en concreto
		System.out.println("Introduce el expediente del alumno:");
		int expedienteNota = sc.nextInt();
		sc.nextLine();
		
		//Una vez hecho esto buscamos el alumno en el conjunto
		for(AlumnoEj29 a:alumnos) {
			//Recorremos el Set alumnos elemento por elemento buscando el expediente igual al solicitado
			if(a.getExpediente() == expedienteNota) { //Cuando lo encontramos entramos en el bloque donde pedimos la nota
				System.out.println("Introduce la nota:");//Pedimos la nota
				String notaStr = sc.nextLine().replace(",", ".");//Reemplazamos la coma y el punto para evitar errores y aceptar formatos
				
				try {
					//Aquí parseamos la nota de String a double
					double notaAnadir = Double.parseDouble(notaStr);
					//Comprobamos que la nota está en el rango válido
					if(notaAnadir<0||notaAnadir>10) {
						System.out.println("La nota debe estar entre 0 y 10.");
						return; // Si no lo está informamos aal usuario y salimos con return sin modificar nada
					}
					a.setNota(notaAnadir); //Si todo está correcto llamamos al setNota, esto modifica el objeto AlumnoEj29 directamente dentro del Set
					//Es importante que equals() y hashCode() estén definidos en base al expediente (en la clase)
					//Modificar la nota no cambia la entidad del objeto en el Set por lo que no afecta a la integridad de este
					System.out.println("Nota actualizada correctamente."); 
				}catch(NumberFormatException e) {
					System.out.println("Formato de nota inválido. Usa 7,7 o 7.7 (ej)");
				}
				return;
			}
		}
		System.out.println("No se encontró ningún alumno con ese expediente.");//Si recorremos todo el Set y no hacemos return dentro del if llegamos aquí
		//Y mostramos que no existe tal alumno con ese expediente
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
		
		//Usar estas variables separadas permite que la estadística ignore los alumnos sin nota
		
		//Ahora usamor un bucle for-each para iterar todos los alumnos en el Set	
		for (AlumnoEj29 a: alumnos) {
			//Solo se consideran alumnos con nota asignada
			if(a.getNota()!= -1) {
				alumnosConNota++; //Se incrementa el contador de alumnos con nota
				sumaNotas += a.getNota(); //Acumulamos la nota para calcular la media después
				if(a.getNota()<5) { //Por último clasificamos en  suspensos y aprobados según sean mayores o menores que 5
					suspensos++;
				}else {
					aprobados++;
				}
			}
		}
		
		//Ahora calculamos la  media usando el operador ternario "? :" para evitar división por cero si no hay alumnos con nota
		//La media se calcula como la suma de notas dividido por el número de alumnos con nota
		double media = (alumnosConNota>0)?sumaNotas/alumnosConNota:0.0;
					//Si hay algún alumno |Se realiza la media    |Sino la media será 0.0
					//con nota	
		//Aquí sumaNotas es int, si quisieramos precisión decimal deberíamos conveertirlo a double
		//double media = (alumnosConNota>0)?(double)sumaNotas/alumnosConNota:0.0;
		
		
		//Por último imprimimos el resultado
		System.out.println("\n ESTADÍSTICAS:");
        System.out.println("Suspensos: " + suspensos);
        System.out.println("Aprobados: " + aprobados);
        System.out.printf("Nota media: %.2f%n", media); // "%.2f%n imprime la media con 2 decimales, más legible
        
        //Cabe destacar que iteramos sobre un Set sin necesidad de índice, aprovechando la estructura LinkedHashSet para mantener el orden de inserción
	}
	
	private static void borrarAlumno(Scanner sc, Set<AlumnoEj29>alumnos) {
		//Pedimos al usuario el expediente del alumno a eliminar
		System.out.println("Introduce el expediente del alumno a borrar:");
		int expedienteBorrar = sc.nextInt();
		sc.nextLine();
		//Creamos el iterador sobre el conjunto alumnos
		//Esto es necesario porque no puedes eliminar directamente un elemento de un Set mientras lo recorres con un bucle for-each
		//Eso lanzaría una excepción ConcurrentModificationException
		//El iterador sí permite eliminar de forma segura el elemento actual mediante it.remove()
		Iterator<AlumnoEj29> it = alumnos.iterator();
		//Recorremos el conjunto con while
		while(it.hasNext()) {//Recorremos todos los alumnos del conjunto mientras haya elementos
			AlumnoEj29 ab = it.next(); //Devuelve el siguiente objeto AlumnoEj29
			//En cada iteraciçon se compara el expediente del alumno actual con el número introducido por el usuario
			//Si coincide entremos en el bloque if
			if(ab.getExpediente()==expedienteBorrar) {
				//it.remove() elimina el último elemento devuelto por it.next() del Set de forma segura
				it.remove();//Esto elimina definitivamente el alumno de la colección
				//Si no usaramos el iterador y tratasemos de hacer alumnos.remove(ab) dentro del bucle, daría error
				System.out.println("Alumo borrado correctamente.");//Si todo sale bien informamos al usuario
				return;//salimos del método inmediatamente para no seguir buscando ni imprimir mensajes adicionales
			}
		}
		//Si no se encontró el expediente se muestra este mensaje
		//Esto quiere decir que el bucle termina sin haber hecho return, no se encontró ningún alumno con el expediente indicado
		System.out.println("No se encontró ningún alumno con ese expediente.");
		
		//Cabe destacar que podemos usar un remove.if que es más moderno corto y legible
		
	}

	
}
