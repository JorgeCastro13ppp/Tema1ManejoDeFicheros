package ejercicios;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio22 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		try {
			System.out.println("Introduce la ruta del fichero a leer:");
			String rutaFicheroLeer = sc.nextLine();
			
			try(DataInputStream dis = new DataInputStream(new FileInputStream(rutaFicheroLeer))){
				int numero = dis.readInt();
				System.out.println("El fichero contiene el número: "+numero);
				System.out.println("Su carácter ASCII es: "+(char) numero+"'");
			}catch(IOException e) {
				System.out.println(e.getMessage());
			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
