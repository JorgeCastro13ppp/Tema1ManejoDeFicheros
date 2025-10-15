package ejercicios;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class GestionEmpleadosEj27 {
	
	private static final String FICHERO = "empleado.bin"; // Las constantes final indican que no cambiarán durante la ejecución del programa

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		//Cargamos los empleados desde el fichero al iniciar el programa
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
            		anadirEmpleado(sc,empleados);
            		break;
            	case 2:
            		buscarEmpleado(sc,empleados);
                	break;
            	case 3:
            		listarEmpleados(empleados);
                	break;
            	case 4:
            		borrarEmpleado(sc,empleados);
                	break;
            	case 5:
            		System.out.println("Saliendo...");
                	break;
            	default:
            		System.out.println("Opción no válida");
            		break;
            }
			
            guardarFichero(empleados); //Usamos el método guardarFichero aquí para actualizar inmediatamente después de cada operación
            
		}while(opcion!=5);
		
		sc.close();
		
	}
	
	private static void anadirEmpleado(Scanner sc, List<EmpleadoEj26> empleados) {
		System.out.println("Introduce el DNI: ");
		String dniNuevo = sc.nextLine();
		
		//Evitar duplicados
		for(EmpleadoEj26 e : empleados) {
			if(e.getDni().equalsIgnoreCase(dniNuevo)) {
				System.out.println("Ya existe un empleado con ese dni.");
				return;
			}
		}
		
		System.out.println("Introduce el nombre: ");
		String nombreNuevo = sc.nextLine();
		
		System.out.println("Introduce el sueldo:");
		String sueldoNuevo = sc.nextLine().replace(',','.'); //Sustituye coma por punto
		double sueldoNuevoParseado = Double.parseDouble(sueldoNuevo); //Con ello evitamos errores al usar 330,90 o 330.90
		
		empleados.add(new EmpleadoEj26(dniNuevo,nombreNuevo,sueldoNuevoParseado));
		System.out.println("Empleado añadido correctamente.");
					
		
	}
	
	private static void buscarEmpleado(Scanner sc, List<EmpleadoEj26>empleados) {
		System.out.println("Introduce el DNI del empleado a buscar:");
		String dniBuscar = sc.nextLine();
		
		for(EmpleadoEj26 e: empleados) {
			if(e.getDni().equalsIgnoreCase(dniBuscar)) {
				System.out.println("Empleado encontrado: ");
				System.out.println(e);
				return;
			}
				
		}
		
		System.out.println("No existe ningún empleado con ese DNI.");
	}
	
	private static void listarEmpleados(List<EmpleadoEj26> empleados) {
		
		if(empleados.isEmpty()) {
			System.out.println("No existe ningún empleado.");
		}else {
			System.out.println("----LISTA DE EMPLEADOS------");
			for(EmpleadoEj26 e : empleados) {
				System.out.println(e);
			}
		}
		
		
	}
	
	private static void borrarEmpleado(Scanner sc,List<EmpleadoEj26>empleados) {
		System.out.println("Introduce el DNI del empleado a borrar:");
		String dniBorrar = sc.nextLine();
		
		//Aqúi debemos usar un iterador para borrar empleados de la lista sino daría error
		
		Iterator <EmpleadoEj26> it = empleados.iterator();
		
		//Java lanza una excepción al intentar borrar un objeto de una lista mientras la recorres 
		
		while(it.hasNext()) {
			
			EmpleadoEj26 eb = it.next();
			
			if(eb.getDni().equalsIgnoreCase(dniBorrar)) {
				
				it.remove(); //Evitamos el error con el .remove()
				
				System.out.println("Empleado borrado correctamente.");
				return;
			}
		}
		System.out.println("No se encontró ningún empleado con ese DNI.");
	}
	
	private static void guardarFichero(List<EmpleadoEj26>empleados) {
		// OOS Abre o crea el fichero sino existe el fichero cuyo nombre está en la constante
		//FOS permite leer objetos escritos en binario 
		// cada objeto Java en una secuencia de bytes que puede guardarse y luego recuperarse con ObjectInputStream
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FICHERO))){
			
			//Este método serializa la lista completa de empleados
			//Cada elemento de la lista debe ser serializable por eso la clase EmpleadoEj26 implementa Serializable
			oos.writeObject(empleados);
			
		}catch(IOException e) {
			System.out.println("Error al guardar el fichero:" +e.getMessage());
			e.printStackTrace();
		}
	}
	
	private static List<EmpleadoEj26> leerFichero() {
		//Crea un objeto File que representa el archivo físico "empleado.bin"
		//Esto solo hace una referencia a este, no abre el fichero todavía
		File ficheroLeer = new File(FICHERO);
		
		//Creamos una lista vacía que devolveremos al final, ya sea con los empleados leidos o vacía si algo falla
		List <EmpleadoEj26> listaComprobar = new ArrayList<>(); //Esto evita devolver null que podría dar errores en el main
		
		//Si el archivo no existe devuelve inmediatamente la lista vacía
		if(!ficheroLeer.exists()) {  //Evitamos FileNotFoundException al intentar abrir un archivo inexistente
			return listaComprobar;
		}
		
		//Aquí ocurre lo contrario que en el método guardarFichero()
		//FileInput abre el archivo y lee bytes del disco
		//ObjectInputStream reconstruye objetos Java a partir de esos bytes (deserializa)
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheroLeer))){ //El flujo de se cierra directamente como ya sabemos
			
			//Aquí debemos usar while porque readObject no tiene un método que nos permita saber si hay más objetos
			//Entonces la única forma de saberlo es que lanze la excepción End Of File cuando llega al final
			while(true) { //Bucle infinito
				//La clase Object es la clase base de todas las clases, por eso la usamos
				//El fichero puede contener un objeto, una lista u otro tipo de objeto
				//Entonces usamos esto para examinarlos después
				Object obj = ois.readObject(); //lee un objeto completo, que haya sido escrito con writeObject()
				
				//Aquí manejamos dos posibles estructuras de archivos
				
				//instanceof es un operador de comprobación de tipo
				//devuelve true si el objeto es una instancia de esa clase o una subclase
				if(obj instanceof EmpleadoEj26) { //¿Es este objeto un empleado?
					
					//Si el archivo contiene un objeto de tipo empleado lo añadimos directamente
					listaComprobar.add((EmpleadoEj26) obj); //Utilizamos casting para poder tratarlo como un EmpleadoEj26
					
				}else if(obj instanceof List) {//¿Es este objeto una lista?
					
					//Si el archivo contiene una lista completa de empleados usamos addAll() para añadirlos todos
					listaComprobar.addAll((List <EmpleadoEj26>)obj);
				}
			}
			
		}catch(EOFException a){ //Se lanza cuando ya no hay más objetos que leer
			//Cuando llega al final del archivo lanza un End Of File
			//Por eso usamos un bucle infinito y luego atrapamos una excepción para cerrarloç
			//No la tratamos como error porque solo significa fin de lectura del archivo
		}catch(IOException | ClassNotFoundException e) {
			System.out.println("Error al leer el fichero.");
			System.out.println(e.getMessage());
			e.printStackTrace();
			
		}
		
		return listaComprobar;  //Devolvemos la lista final y seguimos con el funcionamiento normal del programa
		
	}

}
