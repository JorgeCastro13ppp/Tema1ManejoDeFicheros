package ejercicios;

import java.io.File;
import java.util.Scanner;

public class Ejercicio4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		try {
			System.out.println("Introduce el nombre de la carpeta: ");
			
			String carpetaCrear = sc.nextLine();
			File carpeta = new File(carpetaCrear); //Crea la carpeta en la ruta por defecto, directorio actual"."
			
			if(carpeta.exists()) {
				System.out.println("La carpeta ya existe en: "+carpeta.getAbsolutePath());
			}else {
				carpeta.mkdir();
				System.out.println("Carpeta creada en: "+carpeta.getAbsolutePath());
			}
			
		}catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
		}finally {
			sc.close();
		}
	}

}
