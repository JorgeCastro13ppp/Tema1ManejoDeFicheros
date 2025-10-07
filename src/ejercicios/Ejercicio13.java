package ejercicios;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio13 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduce la ruta del fichero a leer:");
		String rutaFichero = sc.nextLine();
		
		//try-with-resources
		try(FileReader fr = new FileReader(rutaFichero)) {
			
			int caracter; //leeremos cada caracter como número unicode
			
			System.out.println("Contenido del fichero:");
			
			//read() devuelve -1 cada vez que llega al final del fichero
			
			
			while((caracter = fr.read())!=-1) {
				System.out.println((char) caracter);
			}
			
			
		}catch(IOException e) {
			System.out.println("Error al leer el fichero: "+e.getMessage());
		}finally {
			sc.close();
		}
	}

}
