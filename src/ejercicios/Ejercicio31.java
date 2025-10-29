package ejercicios;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Ejercicio31 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String ruta = "textoEj31.txt"; //Fichero de prueba existente
		
		//Abrimos el archivo en modo lectura/Escritura, no crea uno nuevo si ya existe
		try(RandomAccessFile raf = new RandomAccessFile(ruta, "rw")) { //rw = modo lectura escritura
			int caracter;
			while((caracter=raf.read())!=-1) { //leer byte a byte y devuelve su valor entero, hasta el final del archivo
				char c = (char) caracter;
				//Si es una vocal minúscula
				if("aeiou".indexOf(c)!=-1) {
					//Retrocede una posición
					//.seek() mueve el puntero del archivo a la posición
					raf.seek(raf.getFilePointer()-1); //Devuelve la posición actual dentro del archivo (el puntero)
					//Escribe la misma vocal pero en mayúscula
					//Escribe un byte a la posición actual
					raf.write(Character.toUpperCase(c));
				}
				
			}
			
			System.out.println("Conversión completada correctamente");
			
		}catch(IOException e) {
			System.out.println("Error al procesar el archivo: "+e.getMessage());
		}

	}

}
