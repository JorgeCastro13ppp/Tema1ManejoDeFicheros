package ejercicios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ejercicio16 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduce nombre del fichero: ");
		String nombreFichero = sc.nextLine();
		
		File fichero = new File(nombreFichero);
		
		if(!fichero.exists()) {
			System.out.println("El fichero no existe.");
			return;
		}
		List<String> lineas = new ArrayList<>();
		
		//Leer todas las líneas del fichero
		try(BufferedReader br = new BufferedReader(new FileReader(fichero))){
			String linea;
			int contador = 1;
			System.out.println("Contenido del fichero:");
			
			while((linea=br.readLine())!=null) {
				System.out.println(contador+"."+linea);
				lineas.add(linea);
				contador++;
				
			}
			
			
		}catch(IOException e) {
			System.out.println("Error al leer el fichero:"+e.getMessage());
		}
		
		//Pedir número de línea a borrar
		System.out.println("Introduce el número de línea que quieres borrar:");
		int numLinea = sc.nextInt();
		sc.nextLine();
		
		if(numLinea<1||numLinea>lineas.size()) {
			System.out.println("Número de línea no válida.");
			return;
		}
		
		//Eliminar Línea correspondiente
		
		lineas.remove(numLinea-1);
		
		//Sobreescribir el ficheo con las líneas restantes
		try(PrintWriter pw = new PrintWriter(new FileWriter(fichero,false))){
			for(String l:lineas) {
				pw.println(l);
			}
		}catch(IOException i) {
			System.out.println("Error al sobreescribir el fichero: "+i.getMessage());
		}finally {
			sc.close();
		}
		
		//Mostrar el fichero modificado
		System.out.println("Fichero modificado correctamente:");
		int contadorFicheroModificado =1;
		
		for(String li:lineas) {
			System.out.println(contadorFicheroModificado+"."+li);
			contadorFicheroModificado++;
		}
	}

}
