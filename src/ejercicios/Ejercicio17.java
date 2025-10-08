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

public class Ejercicio17 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Introduce el nombre del fichero:");
		String ficheroModificar= sc.nextLine();
		
		File fichero = new File(ficheroModificar);
		
		if(!fichero.exists()) {
			System.out.println("El fichero no existe.");
			return;
		}
		
		List <String> lineas = new ArrayList<>();
		
		//Leer todas las líneas del fichero
		try(BufferedReader br = new BufferedReader(new FileReader(fichero))){
			String linea;
			int contador = 1;
			System.out.println("Contenido del fichero:");
			
			while((linea = br.readLine())!=null) {
				System.out.println(contador+"."+linea);
				lineas.add(linea);
				contador++;
			}
			
		}catch(IOException e) {
			System.out.println("Error al leer el fichero: "+e.getMessage());
		}
		
		//Pedir número de línea a modificar
		System.out.println("Introduce número de línea a modificar:");
		int lineaModificar = sc.nextInt();
		sc.nextLine();
		
		if(lineaModificar<0||lineaModificar>lineas.size()) {
			System.out.println("Número de línea no válido");
			return;
		}
		
		//Pedir el nuevo contenido
		System.out.println("Introduce el nuevo contenido para la línea:");
		String nuevaLinea = sc.nextLine();
			
		//Reemplazar la linea correspondiente
		lineas.set(lineaModificar-1, nuevaLinea);
		
		//Sobreescribir el nuevo contenido
		// "false" sobreescribe, borra el contenido anterior y escribe el nuevo desde cero
		try(PrintWriter pw = new PrintWriter(new FileWriter(fichero,false))){
			for(String l : lineas) {
				pw.println(l);
			}
			
		}catch(IOException i) {
			System.out.println("Error al leer el fichero: "+i.getMessage());
			return;
		}finally {
			sc.close();
		}
		
		//Mostrar el fichero modificado
		System.out.println("Fichero modificado correctamente:");
		int contadorModificado = 1;
		for(String li:lineas) {
			System.out.println(contadorModificado+"."+li);
			contadorModificado++;
		}
		
		
		
	}

}
