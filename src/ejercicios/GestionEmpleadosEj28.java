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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class GestionEmpleadosEj28 {
	
	//Las modificaciones hechas a partir del ejercicio 27 significarán que los empleados se almacenan en un LinkedHashSet
	//No puede haber duplicados aunque intenemos añadirlos
	//Se carga y guarda correctamente desde empleado2.bin
	//Mantiene el orden de inserción
	
	private static final String FICHERO = "empleado2.bin"; // Las constantes final indican que no cambiarán durante la ejecución del programa
	//En este caso cambiaremos el nombre a empleado2.bin porque es el ejercicio28 que así lo pide, usaremos otra forma de almacenamiento, etc.

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		
		//Cargamos los empleados desde el fichero al iniciar el programa
		//Usaremos un linkedHashSet, porque este no permite duplicados, podríamos usar HashSet o treeSet pero esta es la más lógica
		//Este mantiene el orden en el que se insertan los empleados lo cuál es útil al listarlos
		Set<EmpleadoEj26> empleados = leerFichero();	//Cambiamos ArrayList (listas) por linkedHashSet	
		
		//También debemos modificar el método leerFichero
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
	
	private static void anadirEmpleado(Scanner sc, Set<EmpleadoEj26> empleados) {
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
	
	private static void buscarEmpleado(Scanner sc, Set<EmpleadoEj26>empleados) {
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
	
	private static void listarEmpleados(Set<EmpleadoEj26> empleados) {
		
		if(empleados.isEmpty()) {
			System.out.println("No existe ningún empleado.");
		}else {
			System.out.println("----LISTA DE EMPLEADOS------");
			for(EmpleadoEj26 e : empleados) {
				System.out.println(e);
			}
		}
		
		
	}
	
	private static void borrarEmpleado(Scanner sc,Set<EmpleadoEj26>empleados) {
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
	
	//Modificamos también la declaración del método guardarFichero()

	private static void guardarFichero(Set<EmpleadoEj26>empleados) {
	
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FICHERO))){
			
			oos.writeObject(empleados);
			
		}catch(IOException e) {
			System.out.println("Error al guardar el fichero:" +e.getMessage());
			e.printStackTrace();
		}
	}
	
	//Modificamos la declaración del método puesto que debe devolver un Set no un List
	
	private static Set<EmpleadoEj26> leerFichero() {
		
		File ficheroLeer = new File(FICHERO);
		
		Set <EmpleadoEj26> listaComprobar = new LinkedHashSet<>(); 
		
		if(!ficheroLeer.exists()) {  
			return listaComprobar; //Devuelve vacío si no hay fichero
		}
		
		
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheroLeer))){ 
			//Por otra parte en este ejercicio podemos omitir el uso de while(true){...}
			//Puesto que en el método guardarFichero lo está escribiendo todo de una vez (Set)
			listaComprobar = (Set <EmpleadoEj26>) ois.readObject();
			
		}catch(EOFException a){ 
			
		}catch(IOException | ClassNotFoundException e) {
			System.out.println("Error al leer el fichero.");
			System.out.println(e.getMessage());
			e.printStackTrace();
			
		}
		
		return listaComprobar;  
		
	}
	
	

}
