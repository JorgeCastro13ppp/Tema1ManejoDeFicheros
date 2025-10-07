package ejercicios;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio15 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Introduce el nombre del fichero:");
		String nombreFichero = sc.nextLine();
		
		int contadorPersonas=0;
		
		try(BufferedReader br =new BufferedReader(new FileReader(nombreFichero))){
			
			String linea;
			System.out.println("Contenido del fichero: ");
			
			while((linea=br.readLine())!=null) {
				//Dividir los campos separados por ";"
				
				String [] datos = linea.split(";");
				
				if(datos.length == 3) {
					String nombre = datos [0];
					String apellido = datos [1];
					String numero = datos [2];
					
					System.out.println("Nombre: "+nombre);
					System.out.println("Apellido: "+apellido);
					System.out.println("Número de teléfono: "+numero);
					
					contadorPersonas++;
				}else {
					System.out.println("Línea con formato incorrecto.");
				}
			}
			System.out.println("------------------------");
			System.out.println("Núnero de personas: "+contadorPersonas);
			
		}catch(IOException e) {
			System.out.println("Error al leer el fichero: "+e.getMessage());
		}finally {
			sc.close();
		}
		
	}

}
