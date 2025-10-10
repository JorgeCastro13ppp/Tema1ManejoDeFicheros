package ejercicios;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio25 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Introduce la ruta del fichero");
		String rutaFichero = sc.nextLine();
		
		File ficheroOriginal = new File(rutaFichero);
		
		if(!ficheroOriginal.exists()) {
			System.out.println("El fichero no existe.");
			sc.close();
			return;
		}
		
		//Creamos el fichero temporal
		
		File ficheroTemporal = new File("temporalEj26.txt");
		
		//Usamos el try-with-resources, puesto que de una vez leeremos en nuestro ficheroOriginal
		//y escribiremos en el ficheroTemporal caracter a caracter
		try(FileReader fr = new FileReader(ficheroOriginal);
			FileWriter fw = new FileWriter(ficheroTemporal)	){
			int caracter;
			
			//fr.read() lee un caracter del fichero y devuelve un int
			while((caracter=fr.read())!= -1) { // leer carácter a caracter, si llega al final del fichero devuelve -1
				char c = (char) caracter; //Esta conversión es necesaria porque queremos trabajar con el carácter real no con su código ASCII
				//Si es vocal minúscula convertir a mayúscula
				if(c=='a'|c=='e'|c=='i'|c=='o'|c=='u') {
					c = Character.toUpperCase(c);
				}
				fw.write(c); //Escribir dentro del fichero temporal
			}
			
			
			
		}catch(IOException e) {
			System.out.println("Error al procesar el fichero: "+e.getMessage());
			e.printStackTrace();
		}finally {
			sc.close();
		}
		
		//Borrar el fichero original y renombrar el temporal
		if(ficheroOriginal.delete()) { //esto elimina el fichero original, devuelve true si lo borra y false si no pudo
			ficheroTemporal.renameTo(ficheroOriginal);//renombramos el fichero temporal con el nombre del fichero original 
			System.out.println("Fichero procesado correctamente las vocales están en mayúscula.");
		}else {
			System.out.println("No se pudo borrar el fichero original.");
		}
		
		
	}

}
