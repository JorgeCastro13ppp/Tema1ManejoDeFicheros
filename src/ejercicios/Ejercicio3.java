package ejercicios;

import java.io.File;
import java.util.Scanner;

public class Ejercicio3 {
	
	//Método para obtener tipo
	private static String obtenerTipo(File contenido) {
		if(contenido.isDirectory()) {
			return "Carpeta";
		}else if(contenido.isFile()) {
			return "Archivo";
		}
		return "No es archivo ni carpeta";
		
	}
	//Método para obtener permisos
	
	private static String obtenerPermisos(File contenido) {
		StringBuilder permisos = new StringBuilder();
		permisos.append(contenido.canRead()?"r":"-");
		permisos.append(contenido.canWrite()?"w":"-");
		permisos.append(contenido.canExecute()?"e":"-");
		
		return permisos.toString();
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		try {
			
			System.out.println("Introduce una ruta de una carpeta: ");
			String rutaCarpeta=sc.nextLine();
			File carpeta = new File(rutaCarpeta);
			
			if(carpeta.exists()) {
				
				System.out.println("La ruta introducida es: "+rutaCarpeta);
				
				if(carpeta.isDirectory()) {
					
					System.out.println("Contenido de la ruta: "+carpeta.getAbsolutePath());
					File [] archivos = carpeta.listFiles();
					
					if(archivos!=null) {
						
						for(File contenido: archivos ) {
							String tipo = obtenerTipo(contenido);
							String permisos= obtenerPermisos(contenido);
							System.out.printf("%-20s  %-8s  [%s]%n", contenido.getName(), tipo, permisos);
						}
					}else {
						
						System.out.println("No se pudo acceder al contenido.");
						
					}
					
				}else {
					
					System.out.println("La ruta no es de una carpeta.");
					
				}
			}else {
				
				System.out.println("La ruta no existe.");
				
			}
			
		}catch(Exception e){
			
		}finally {
			sc.close();
		}
	}

}
