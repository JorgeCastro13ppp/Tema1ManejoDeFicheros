package ejercicios;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio14 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduce la ruta del fichero a leer:");
		String rutaFichero = sc.nextLine();
		
		//try-with-resources ahora con BufferedReader que mejora la feiciencia de lectura ( usa un buffe interno )
		try(BufferedReader br = new BufferedReader(new FileReader(rutaFichero))) {
			
			String linea; //leeremos cada línea
			
			System.out.println("Contenido del fichero:");
			
			//read.line() ahora y lo comparamos con !=null		
			while((linea = br.readLine())!=null) {
				System.out.println(linea);
			}
			
			
		}catch(IOException e) {
			System.out.println("Error al leer el fichero: "+e.getMessage());
		}finally {
			sc.close();
		}
	}

}
