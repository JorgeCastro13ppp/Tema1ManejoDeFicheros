package ejercicios;

import java.io.File;
import java.util.Scanner;

public class Ejercicio5 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		try {
			System.out.println("Introduce el nombre del archivo: ");
			
			String archivoCrear = sc.nextLine();
			File archivo = new File(archivoCrear); //Crea el archivo en la ruta por defecto, directorio actual "."
			
			if(archivo.exists()) {
				System.out.println("El archivo ya existe en: "+archivo.getAbsolutePath());
			}else {
				archivo.createNewFile();
				System.out.println("Archivo creado en: "+archivo.getAbsolutePath());
			}
			
		}catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
		}finally {
			sc.close();
		}
	}

}
