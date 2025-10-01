package ejercicios;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Ejercicio2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		
		
		try {
			//Pedimos al usuario una ruta
			System.out.println("Introduce una ruta:");
			String ruta = sc.nextLine();
			File archivo = new File(ruta);
			
			if(archivo.exists()) {
				System.out.println("La ruta introducida es: "+ruta);
				if(archivo.isFile()) {
					System.out.println("Es un fichero.");
					System.out.println("Tamaño: "+archivo.length()+" b");
				}else if(archivo.isDirectory()) {
					System.out.println("Es un directorio.");
					System.out.println("Tamaño: "+archivo.length()+" b");
				}
			}else {
				System.out.println("La ruta no existe.");
			}
		}catch(InputMismatchException e) {
			System.out.println("Error: entrada de datos no válida.");
		}catch(Exception e) {
			System.out.println("Se ha producido un error: "+e.getMessage());
		}finally {
			sc.close();
		}

	}

}
