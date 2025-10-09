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
	
	//Definimos el nombre del archivo donde vamos a guardar los datos en formato binario y con objetos serializados
	//Static y Final porque es una constante compartida por toda la clase
	private static final String NOMBRE_FICHERO = "Telefonos.dat";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		//Carga los teléfonos desde el fichero (si existe)
		//Sino el método devolverá una lista vacía	
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
	            	anadirTelefono(sc,lista);
	            	break;
	            case 2:
	            	mostrarLista(lista);
	            	break;
	            case 3:
	            	modificarTelefono(sc,lista);
	            	break;
	            case 4:
	            	borrarTelefono(sc,lista);
	            	break;
	            case 0:
	            	System.out.println("Saliendo del programa...");
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
			//Abre el fichero
			//Sino hay nada devolverá una lista vacía
			File fichero = new File(NOMBRE_FICHERO);
			if(!fichero.exists()) {
				return new ArrayList<>();
			}
			//ObjectInputStream interpreta BYTES como objetos Java previamente guardados con ObjectOutPutStream
			//FileInputStream lee bytes
			try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichero))) {
				return (ArrayList<TelefonoEj20>)  ois.readObject();
				//Así obtenemos directamente la lista de objetos y no simples bytes
				
			}catch(IOException | ClassNotFoundException e) {
				System.out.println("No se pudo leer el fichero: "+e.getMessage());
				return new ArrayList<>();
			}
			
		}
		
		//Guardar lista en el fichero
		private static void  guardarFichero(List<TelefonoEj20> lista){
			//ObjectOutputStream convierte objetos a bytes
			//Serializa los objetos (objetos->bytes)
			//Así guardamos directamente estructuras complejas como List<TelefonoEj20> lista
			
			//FileOutputStream escribe bytes en el archivo 
			//Abre el fichero físicamente en modo escritura
			//Envía bytes hacia el archivo (en disco)
			//No entiende estructuras ni objetos solo ESCRIBE BYTES
			try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(NOMBRE_FICHERO))){
				oos.writeObject(lista);
			}catch(IOException i) {
				System.out.println("Error al guardar el fichero: "+i.getMessage());
			}
		}
		
		//Añadir nuevo teléfono
		
		private static void anadirTelefono(Scanner sc, List<TelefonoEj20> lista) {
			//Scanner sc permite al usuario escribir el número y nombre
			System.out.println("Introduce el número de teléfono:");
			String numeroAnadir = sc.nextLine();
			
			//Comprobar duplicado, queremos que no haya repetidos así que revisamos toda la lista
			// con el for recorremos cada objeto guardado
			
			for (TelefonoEj20 num : lista) {
				if(num.getNumero().equals(numeroAnadir)) {
					System.out.println("No se puede añadir el número porque ya existe.");
					return;
					//Si ya existe mostramos un mensaje y salimos con el return para no seguir en el método
				}
			}
			
			System.out.println("Introduce el nombre de la persona: ");
			String nombreAnadir = sc.nextLine();
			//Creamos un nuevo objeto con los datos introducidos por el usuario
			//El objeto lo añadimos a la lista en memoria pero aún no está en el fichero
			lista.add(new TelefonoEj20(numeroAnadir,nombreAnadir));
			
			//Guardamos la lista actualizada
			//Llamamos al mismo método que nusa ObjetOutputStream para reescribir todo el fichero
			//Hacemos esto porque trabajamos un un fichero binario de objetos no con texto.
			guardarFichero(lista);
			System.out.println("Teléfono añadido correctamente.");
			
		}
		
		//Mostrar lista
		
		private static void mostrarLista(List<TelefonoEj20> lista) {
			
			//Comprobamos si está vacío
			if(lista.isEmpty()) {
				System.out.println("No hay teléfonos guardados.");
				return;
			}
			
			System.out.println("\n=== LISTA DE TELÉFONOS ===");
			for(TelefonoEj20 tm:lista) {
				//IMPORTANTE usamos esta línea porque en la clase TelefonoEj20 tenemos el método toString
				System.out.println(tm);
			}
		}
		
		//Modificar nombre por número
		
		private static void modificarTelefono(Scanner sc,List<TelefonoEj20>lista) {
			//Usamos el número para buscar el objeto dentro de la lista
			System.out.println("Introduce el número de teléfono: ");
			String numeroModificar = sc.nextLine();
			
			for(TelefonoEj20 tmo : lista) {
				//La condición compara cada número de teléfono del objeto actual con el introducido por el usuario
				
				if(tmo.getNumero().equals(numeroModificar)) {
					//Si lo encontramos entramos en la condición
					System.out.println("Introduce el nuevo nombre: ");
					String nombreNuevo = sc.nextLine();
					//Una vez encontrado se pide el nuevo nombre y se guarda con el setter setNombre
					//Esto modifica el objeto dentro de la lista, NO CREA UNO NUEVO
					//Como lista es un objeto en memoria ,el cambio se aplica directamente dentro del arrayList
					tmo.setNombre(nombreNuevo);
					// Ahora usamos el método guardarFichero que abre un ObjectOutputStream y escribe la lista completa.
					//Sino hicieramos esto el cambio existiría en memoria y se perdería al salir del programa
					guardarFichero(lista);
					System.out.println("Nombre modificado correctamente.");
					return;
				}
			}
			System.out.println("No se encontró ningún teléfono con ese número.");
		}
		
		private static void borrarTelefono(Scanner sc, List<TelefonoEj20>lista) {
			//Con este último completamos toda la lógica CRUD
			
			System.out.println("Introduce el número de teléfono a borrar:");
			String numeroBorrar = sc.nextLine();
			
			for(TelefonoEj20 tb:lista) {
				//Comprobamos que el objeto contiene el número que introdujo el usuario
				if(tb.getNumero().equals(numeroBorrar)) {
					//Si lo contiene entramos en el if
					//Usamos lista.remove que elimina el objeto directamente de la lista en memoria
					//No necesitamos índices porque remove(tb) busca el objeto internamente
					lista.remove(tb);
					//LLamamos al método que vuelve a escribir toda la lista actualizada esta vez sin el contacto que hemos borrado
					//Internamente el método usa un ObjectOutputStream y sobreescribe el fichero
					guardarFichero(lista);
					System.out.println("Número borrado correctamente");
					
					//IMPORTANTE no se debe borrar un objeto de una lista con remove al recorrer con un for
					//Pero en este caso funciona porque justo después cerramos con un return
					return;
					//Si el bucle continuara daría un error de concurrencia
					
				}
			}
			System.out.println("No se encontró ningún teléfono con ese número.");
		}

}
