package ejercicios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestionTelefonosEj20 {
	
	private static final String NOMBRE_FICHERO = "Telefonos.dat";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		List<TelefonoEj20> lista = leerFichero();
		
		int opcion;
		
		do {
			System.out.println("\n=== GESTIÓN DE TELÉFONOS ===");
            System.out.println("1. Añadir teléfono");
            System.out.println("2. Mostrar lista");
            System.out.println("3. Modificar nombre por número");
            System.out.println("4. Borrar teléfono");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = Integer.parseInt(sc.nextLine());
            
            switch(opcion) {
	            case 1:
	            	
	            	break;
	            case 2:
	            	break;
	            case 3:
	            	break;
	            case 4:
	            	break;
	            case 0:
	            	break;
	            default:
	            	System.out.println("Opción no válida.");
	            	break;
            
            }
			
		}while(opcion!=0);
		
		sc.close();
		
		
	}
	
		//Leer la lista del fichero, si existe

		private static ArrayList<TelefonoEj20> leerFichero(){
			File fichero = new File(NOMBRE_FICHERO);
			if(!fichero.exists()) {
				return new ArrayList<>();
			}
			
			try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichero))) {
				return (ArrayList<TelefonoEj20>)  ois.readObject();
				
			}catch(IOException | ClassNotFoundException e) {
				System.out.println("No se pudo leer el fichero: "+e.getMessage());
				return new ArrayList<>();
			}
			
		}
		
		//Guardar lista en el fichero
		private static void  guardarFichero(List<TelefonoEj20> lista){
			try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(NOMBRE_FICHERO))){
				oos.writeObject(lista);
			}catch(IOException i) {
				System.out.println("Error al guardar el fichero: "+i.getMessage());
			}
		}
		
		//Añadir nuevo teléfono
		
		private static void anadirTelefono(Scanner sc, List<TelefonoEj20> lista) {
			System.out.println("Introduce el número de teléfono:");
			String numeroAnadir = sc.nextLine();
			
			//Comprobar duplicado
			
			for (TelefonoEj20 num : lista) {
				if(num.getNumero().equals(numeroAnadir)) {
					System.out.println("No se puede añadir el número porque ya existe.");
					return;
				}
			}
			
			System.out.println("Introduce el nombre de la persona: ");
			String nombreAnadir = sc.nextLine();
			lista.add(new TelefonoEj20(numeroAnadir,nombreAnadir));
			guardarFichero(lista);
			System.out.println("Teléfono añadido correctamente.");
			
		}
		
		//Mostrar lista
		
		private static void mostrarLista(List<TelefonoEj20> lista) {
			if(lista.isEmpty()) {
				System.out.println("No hay teléfonos guardados.");
				return;
			}
			
			System.out.println("\n=== LISTA DE TELÉFONOS ===");
			for(TelefonoEj20 tm:lista) {
				System.out.println(tm);
			}
		}
		
		//Modificar nombre por número
		
		private static void modificarTelefono(Scanner sc,List<TelefonoEj20>lista) {
			System.out.println("Introduce el número de teléfono: ");
			String numeroModificar = sc.nextLine();
			
			for(TelefonoEj20 tmo : lista) {
				if(tmo.getNumero().equals(numeroModificar)) {
					System.out.println("Introduce el nuevo nombre: ");
					String nombreNuevo = sc.nextLine();
					tmo.setNombre(nombreNuevo);
					guardarFichero(lista);
					System.out.println("Nombre modificado correctamente.");
					return;
				}
			}
			System.out.println("No se encontró ningún teléfono con ese número.");
		}
		
		private static void borrarTelefono(Scanner sc, List<TelefonoEj20>lista) {
			System.out.println("Introduce el número de teléfono a borrar:");
			String numeroBorrar = sc.nextLine();
			
			for(TelefonoEj20 tb:lista) {
				if(tb.getNumero().equals(numeroBorrar)) {
					lista.remove(tb);
					guardarFichero(lista);
					System.out.println("Número borrado correctamente");
					return;
					
				}
			}
			System.out.println("No se encontró ningún teléfono con ese número.");
		}

}
