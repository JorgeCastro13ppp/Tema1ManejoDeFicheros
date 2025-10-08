package ejercicios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio18 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		try{
			System.out.println("Introduce el nombre del fichero original: ");
			String nombreOriginal = sc.nextLine();
			
			File ficheroOriginal = new File(nombreOriginal);
			
			if(!ficheroOriginal.exists()||!ficheroOriginal.isFile()) {
				System.out.println("El fichero no existe o no es un fichero.");
				return;
			}
			
			System.out.println("Introduce el nombre del fichero destino:");
			String nombreDestino = sc.nextLine();
			
			File ficheroDestino = new File(nombreDestino);
			
			try(
					BufferedReader br = new BufferedReader(new FileReader(ficheroOriginal));
					BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroDestino))
					){
				
				int c;
				
				while((c=br.read())!=-1) {
					char ch = (char) c;
					if(Character.isUpperCase(ch)) {
						ch = Character.toLowerCase(ch);
					}else if (Character.isLowerCase(ch)) {
						ch = Character.toUpperCase(ch);
					}
					bw.write(ch);
				}
				
				System.out.println("Fichero convertido correctamente en: "+ficheroDestino.getAbsolutePath());
				
			}
			
		}catch(IOException e) {
			System.out.println("Error de entrada/salida:"+e.getMessage());
		}finally {
			sc.close();
		}
	}

}
