package ejercicios;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class EjemploAleatorioNumero {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		try {
			//Crear o abrir un fichero para escribir
			RandomAccessFile archivo = new RandomAccessFile("numero.txt","rw");
			
			//Escribir datos en el fichero
			archivo.writeInt(1); //4byte
			archivo.writeDouble(5.5); //8byte
			
			archivo.writeInt(2);
			archivo.writeDouble(7.7);
			
			archivo.writeInt(3);
			archivo.writeDouble(9.9);
			
			//Variables
			int numPosicion;
			double valor;
			
			//Solicitar al usuario el nombre del fichero
			System.out.println("Introduce el nombre de la nota a buscar");
			int numeroLeer = Integer.parseInt(sc.nextLine());
			
			//Ir al inicio del archivo
			archivo.seek(0);
			
			//Bucle para leer todos los datos
			try {
				while(true) {//Leer hasta el final del archivo
					numPosicion = archivo.readInt();
					valor = archivo.readDouble();
					archivo.skipBytes(8);
					if(numPosicion==numeroLeer) {
						//valor = archivo.readDouble();
						System.out.println("Número encontrado"+numPosicion+", valor: "+valor);
						break;
					}
				}
			}catch(EOFException e) {
				//LLegamos al final del archivo
				System.out.println("Fin del archivo.");
			}
			
			archivo.close();//Cerramos el archivo
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
