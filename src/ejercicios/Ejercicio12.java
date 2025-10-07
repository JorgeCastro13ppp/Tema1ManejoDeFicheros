package ejercicios;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Ejercicio12 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Introduce el nombre del fichero:");
		String nombreFichero = sc.nextLine();
		
		// Usamos try-with-resources disponible desde java 7
		// El segundo parámetro 'true' en FileWriter permite AÑADIR (no sobrescribir)
		//✅ No necesitas llamar a pw.close().
		//✅ No necesitas guardar el FileWriter en una variable aparte (ya lo gestiona el PrintWriter).
		//✅ Es seguro y limpio: evita fugas de recursos.
		// NO hace faltan hacer pw.close ni fw.close()
		try(PrintWriter pw = new PrintWriter(new FileWriter(nombreFichero,true))) {
					
			
			while(true) {
				System.out.println("Introduce el nombre (o salir para terminar):");
				String nombre = sc.nextLine();
				
				if(nombre.equalsIgnoreCase("salir")) {
					break;
				}
			
				System.out.println("Introduce el apellido:");
				String apellido = sc.nextLine();
				
				System.out.println("Introduce el número: ");
				String numero = sc.nextLine();
				
				//Guardamos los datos en formato nombre apellido numero
				
				pw.println(nombre+";"+apellido+";"+numero);
				System.out.println("Registro añadido correctamente.");
			}
			System.out.println("Datos guardados correctamente en el fichero.");
			
			
		}catch(IOException e) {
			System.out.println("Error al escribir en el fichero: "+e.getMessage());
		}finally {
			sc.close();
		}
	}
}
