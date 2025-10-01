package ejercicios;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Ejercicio7 {
	
	private static String obtenerPermisos(File archivoNuevo) {
		StringBuilder permisos = new StringBuilder();
		
		permisos.append(archivoNuevo.canRead()?"r":"-");
		permisos.append(archivoNuevo.canWrite()?"w":"-");
		permisos.append(archivoNuevo.canExecute()?"e":"-");
		
		return permisos.toString();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		try {
			ArrayList<String> ficherosLista = new ArrayList<>();
			String nombre;
			while(true) {
				System.out.println("Nombre del fichero ( escribe salir para terminar ):");
				nombre = sc.nextLine();
				
				if(nombre.equalsIgnoreCase("salir")) {
					break;
				}
				ficherosLista.add(nombre);
				System.out.println("Fichero añadido.");
			}
			
			
			System.out.println("----------------------------");
			
			//Recorrer lista y mostrar información.
			
			for(String ficheroNombre : ficherosLista) {
				File archivoNuevo = new File(ficheroNombre);
				System.out.println(ficheroNombre);
				System.out.println("----------------------------");
				
				if(archivoNuevo.exists()) {
					if(archivoNuevo.isFile()) {
						System.out.println("EL fichero existe.");
					}else if(archivoNuevo.isDirectory()) {
						System.out.println("La carpeta existe.");
					}else {
						System.out.println("Existe pero no es un fichero o carpeta");
					}
					System.out.println("Nombre del fichero: "+archivoNuevo.getAbsoluteFile());
				}else {
					System.out.println("EL fichero o carpeta no existe.");
				}
				
				System.out.println("Fichero: "+archivoNuevo.isFile()+" | Carpeta: "+archivoNuevo.isDirectory());
				
				
				String permisos = obtenerPermisos(archivoNuevo);
				
				if(archivoNuevo.isFile()) {
					System.out.println("Tamaño:"+archivoNuevo.length()+" b");
				}
				System.out.println("----------------------------");
				
			}
			
		}catch(Exception e){
			System.out.println("Error: "+e.getMessage());
		}finally {
			sc.close();
		}
		
	}

}
