package ejercicios;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio11 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		FileWriter fw = null;
		
		try {
			
			System.out.println("Introduce el nombre del fichero:");
			String fichero = sc.nextLine();
			
			System.out.println("Introduce el texto:");
			String texto = sc.nextLine();
			
			//true = añadir al final si ya existe
			fw= new FileWriter(fichero,true); // FileWriter(nombreFichero, true) → el true permite añadir texto sin borrar el contenido anterior.
			fw.write(texto +System.lineSeparator());
			
			System.out.println("Texto añadido correctamente al fichero: "+fichero);
			
		}catch(IOException e) {
			System.out.println("Error al escribir en el fichero: "+e.getMessage());
		}finally{
			try {
				if(fw!=null) {
					fw.close();
				}
			}catch(IOException i) {
				System.out.println("Error al cerrar el fichero: "+i.getMessage());
			}
			
			sc.close();
			System.out.println("Programa finalizado");
		}
	}

}
