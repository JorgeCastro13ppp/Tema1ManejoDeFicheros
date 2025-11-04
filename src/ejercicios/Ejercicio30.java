package ejercicios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Ejercicio30 {
	private static final String FICHERO = "alumnos2.bin";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner (System.in);
		//En el ejercicio 29 teníamos una colección de alumnos representada con Set, estto garantizaba que no hubiera duplicados
		//pero si queriamos buscar actualizar o borrar un alumno teníamos que recorrer toda la colección uno por uno con un bucle
		//esto es correcto pero poco eficiente
		
		//El Map está pensado justamente para asociar una clave única(expediente) con un valor (alumno)
		//Cada entrada sería algo como
		//12345 → AlumnoEj29{expediente=12345, nombre="Ana", nota=8.5}
		//67890 → AlumnoEj29{expediente=67890, nombre="Luis", nota=-1}
		//Esto permite hacer operaciones mucho más directas y más rápidas. Evitamos escribir código repetitivo de búsqueda manual
		//En este ejercicio el expediente actúa como identificador único así que lo más lógico limpio y eficiente es usar Map
		Map<Integer,AlumnoEj29> alumnos = leerFichero();
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
	
	private static Map<Integer,AlumnoEj29> leerFichero(){
		//Antes creabamos un conjunto vacío si el fichero existía lo leíamos y hacíamos un casting a Set<AlumnoEj29>
		//Si no existía devolvíamos el conjunto vacío
		//Set<AlumnoEj29> alumnosLeer= new LinkedHashSet<>(); 
		
		//Ahora creamos un objeto File con el nombre del fichero
		File ficheroLeer = new File(FICHERO);//Sirve para comprobar si existe antes de intentar abrirlo
		if(!ficheroLeer.exists()) {//Si el fichero no existe el programa devuelve un LinkedHashMap vacío
			//return alumnosLeer;
			return new LinkedHashMap<>();//Antes devolvíamos un LinkedHasSet vacío, LinkedHashMap mantiene el orden de insercíon igual que el otro lo hacía
		}
		
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheroLeer))){
			//antes asumíamos que el fichero siempre contenía un Set<AlumnoEj29> así que hacíamos directamente el casting
			
			//alumnosLeer = (Set <AlumnoEj29>)  ois.readObject();
			
			//Ahora el fichero guarda un Map<Integer,AlumnoEj29> pero como por seguridad no puedes saber 100% qué hay guardado lees un Object genérico y luego comprobamos
			Object obj = ois.readObject();
			if(obj instanceof Map) { //Esto garantiza que el objeto leído sea realmente un Map antes de hacer el casting
				return (Map<Integer,AlumnoEj29>) obj; //Si lo es, devolvemos
				//Este enfoque es más seguro y flexible que el anteerior
			}
		}catch(IOException | ClassNotFoundException e) {
			System.out.println("Error al leer el fichero: "+e.getMessage());
			e.printStackTrace();
		}
		
		//return alumnosLeer;
		return new LinkedHashMap<>();//Si algo falla devolvemos un LinkedHashMap vacío para evitar errores NullPointerException
	}
	
	private static void guardarFichero(Map<Integer,AlumnoEj29>alumnos) {
		//Este método es muy similar al de la anterior clase
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FICHERO))) {
			oos.writeObject(alumnos); //Aquí se escribe el mapa completo (Map<Integer,AlumnoEj29>) en ficher binario
			//Todas las propiedades del objeto deben ser serializables (en este caso int,String, double ya lo son)
			//Este método guarda toda la estructura de datos (claves y valores) en el fichero, al leerlo más tarde con readObject(), recuperamos exactamente el mismo mapa con todos los alumnos
		}catch(IOException e) {
			System.out.println("Error al guardar el fichero: "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	private static void anadirAlumno(Scanner sc, Map<Integer,AlumnoEj29>alumnos) { //Modificamos los parámetros de entrada
		//Se solicita al usuario que introduzca el expoediente
		System.out.println("Introduce el número del expediente a añadir:");
		int expedienteAnadir= sc.nextInt();
		sc.nextLine();
		
		/*for(AlumnoEj29 a: alumnos) {
			if(a.getExpediente()==expedienteAnadir) {
				System.out.println("Ya existe un alumno con ese expediente.");
				return;
			}
		} */
		
		//Comprobamos duplicados se comprueba si el mapa ya contiene ese expediente como clave
		if(alumnos.containsKey(expedienteAnadir)) {
			System.out.println("Ya existe un alumno con ese expediente."); //Si ya existe, muestra un mensaje y sale del método con return
			return;//En el ejercicio 29 esta comprobación se hacía recorriendo el Set con un for, porque no había claves únicas
		}//Con Map es mucho más eficiente
		
		//Se pide eln nombre del nuevo alumno y se guarda en una variable
		System.out.println("Introduce el nombre del alumno a añadir:");
		String nombreAlumnoAnadir = sc.nextLine();
		
		//alumnos.add(new AlumnoEj29(expedienteAnadir,nombreAlumnoAnadir,-1));
		//Antes se usaba porque era un Set, que no necesita clave; en Map usamos .put() para asociar la clave con el valor
 		
		//Añadimos alumno al mapa
		//Se crea un objeto AlumnoEj29 con el epediente nombre y nota inicial, que indica "sin nota asignada"
		//Luego se guarda enb el mapa con .put(clave,valor), la clave es el expediente y el valor el objeto AlumnoEj29
		alumnos.put(expedienteAnadir, new AlumnoEj29(expedienteAnadir,nombreAlumnoAnadir,-1));
		System.out.println("Alumno añadido correctamente");//Aquí informamos al usuario que el alumno se ha añadido con éxito
		
	}
	
	private static void mostrarAlumnos(Map<Integer,AlumnoEj29>alumnos) {
		//Verificamos si el mapa no tiene datos
		if(alumnos.isEmpty()) {
			System.out.println("No hay alumnos registrados."); //Si no tiene datos devolvemos un mensaje que dice que no tiene 
		}else {//Si el mapa no está vacío se imprime un encabezado decorativo
			System.out.println("-----LISTA DE ALUMNOS------");
			
			/*for(AlumnoEj29 a:alumnos) {
				System.out.println(a);
			}*/
			
			//ahora se usa .values() que devuelve una colección con todos los valores del mapa SIN LAS CLAVES
			for(AlumnoEj29 a:alumnos.values()) {
				System.out.println(a);//Cada elemento 'a' es un objeto AlumnoEj29
			}
		}
	}
	
	private static void ponerNota(Scanner sc,Map<Integer,AlumnoEj29>alumnos) {
		//Este método es un ejemplo muy claro de como usar un Map simplifica la búsqueda y actualización de datos
		System.out.println("Introduce el expediente del alumno:");
		int expedienteNota = sc.nextInt();
		sc.nextLine();
		
		/*for(AlumnoEj29 a:alumnos) {
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
		System.out.println("No se encontró ningún alumno con ese expediente.");*/
		
		//Antes se necesitaba recorrer todo el set con un bucle for para buscar el alumno por expediente
		//Ahora con Map se puede acceder directamente al alumno usando su "key" (expedienteNota) en O(1). Búsqueda inmediata
		
		AlumnoEj29 alumnoNota = alumnos.get(expedienteNota);
		if(alumnoNota==null) {//Si la clave no existe el método get devuelve null
			System.out.println("No existe ningún alumno con ese expediente."); //Se muestra mensaje de error
			return;//Se interrumpe la ejecución del método con el return 
		}
		System.out.println("Introduce la nota: ");
		String notaStr = sc.nextLine().replace(',', '.');//Se reemplazan las comas por puntos como siempre
		double notaParseada= Double.parseDouble(notaStr);//Convertimos el texto introducido a tipo double
		//Si el usuario escribe letras se lanzará una NumberFormatException, podríamos capturarlo con try-catch
		
		//Si todo es correcto se llama al método setNota() del objeto AlumnoEj29 para actualizar la nota
		alumnoNota.setNota(notaParseada);
		System.out.println("Nota actualizada correctamente.");
	}
	
	private static void mostrarEstadisticas(Map<Integer,AlumnoEj29>alumnos) {
		//Si el mapa está vacío devuelve true
		if(alumnos.isEmpty()) {
			System.out.println("No hay alumnos registrados.");//Mpstramos mensaje
			return;//Se interrumpe la ejecución
		}
		
		//Declaramos variables para acumular datos
		
		int suspensos =0; //Nota <5
		int aprobados=0;//Nota >5
		int sumaNotas=0;//Suma total de todas las notas para calcular la media, debería ser double para mejorar precisión
		int alumnosConNota=0;//Nº de alumnos que tienen una nota válida, distinta de -1
		
		
		/*for (AlumnoEj29 a: alumnos) {
			if(a.getNota()!= -1) {
				alumnosConNota++;
				sumaNotas += a.getNota();
				if(a.getNota()<5) {
					suspensos++;
				}else {
					aprobados++;
				}
			}
		}*/
		
		//De nuevo, el .values() devuelve una colección con todos los valores del mapa, los objetos AlumnoEj29
		//Recorremos todos los alumnos sin preocuparnos por las claves
		for (AlumnoEj29 a: alumnos.values()) {
			//Primero se excluyen a los alumnos sin nota asignada para los cálculos estadísticos
			if(a.getNota()!= -1) {
				alumnosConNota++;//Se incrementa el contador de alumnos con nota
				sumaNotas += a.getNota();//Se añade su nota a la suma total para calcular la media 
				if(a.getNota()<5) {
					suspensos++;
				}else {
					aprobados++;
				}
			}
		}
		//Si hay alumnos con Nota >0, calcula la media, 
		//Sino hay ninguno evita la división entre 0 y establece 0.0
		double media = (alumnosConNota>0)?sumaNotas/alumnosConNota:0.0;
		
		System.out.println("ESTADÍSTICAS:");
        System.out.println("Suspensos: " + suspensos);
        System.out.println("Aprobados: " + aprobados);
        System.out.printf("Nota media: %.2f%n", media);
	}
	
	private static void borrarAlumno(Scanner sc, Map<Integer,AlumnoEj29>alumnos) {
		System.out.println("Introduce el expediente del alumno a borrar:");
		int expedienteBorrar = sc.nextInt();
		sc.nextLine();
		
		
		/*Iterator<AlumnoEj29> it = alumnos.iterator();
		
		while(it.hasNext()) {
			AlumnoEj29 ab = it.next();
			if(ab.getExpediente()==expedienteBorrar) {
				it.remove();
				System.out.println("Alumo borrado correctamente.");
				return;
			}
		}
		
		System.out.println("No se encontró ningún alumno con ese expediente."); */
		
		// El método .remove() intenta eliminar la entrada asociada a la clave expedienteBorrar del Map
		
		if(alumnos.remove(expedienteBorrar)!=null) {
			//Si el expediente existía el método devuelve el objeto AlumnoEj29 eliminado
			System.out.println("Alumno borrado correctamente.");
		}else {
			System.out.println("No se encontró ningún alumno con ese expediente.");
		}
		
	}

}
