package ejercicios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio19 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		try {
			System.out.println("Introduce el nombre del fichero original: ");
			String nombreOriginal = sc.nextLine();
			
			File ficheroOriginal = new File(nombreOriginal);
			
			if(!ficheroOriginal.exists()||!ficheroOriginal.isFile()) {
				System.out.println("El fichero no existe o no es un fichero");
				return;
			}
			
			File ficheroDestino = new File("cotizaciones2.txt");
			
			try(
					BufferedReader br = new BufferedReader(new FileReader(ficheroOriginal));
					BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroDestino))
					){
				String linea;
				int numeroLinea = 0;
				String empresa="";
				String precio="";
				
				//Inicializamos las variables vacías
				
				while((linea=br.readLine())!=null) {
					numeroLinea++;
					
					//Usamos el módulo 5 para identificar la posición
					int resto = numeroLinea % 5;
					
					if(resto==2) { //línea 2 de cada bloque, nombre de la empresa
						empresa = linea;
					}else if (resto ==3) { //línea 3 de cada bloque, valor de la acción
						precio = linea;
						//Debe ir dentro de este else if justo donde ya tenemos los dos datos disponible
						//Ahora que tenemos nombre y precio lo escribimos
						bw.write(empresa+";"+precio);
						bw.newLine(); // Escribe salto de línea en el fichero
					}
					
					
				}
				
				System.out.println("Fichero generado correctamente: "+ficheroDestino.getAbsolutePath());
				
			}
			
		}catch(IOException e) {
			System.out.println("Error de entrada/salida: "+e.getMessage());
		}finally {
			sc.close();
		}
	}

}
