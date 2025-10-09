package ejercicios;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class EjemploEscribirBinario {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String nombreArchivo = "datos.bin";
		
		try(FileOutputStream fos = new FileOutputStream(nombreArchivo);
			DataOutputStream dos = new DataOutputStream(fos)	){
			
			//Escribir algunos datos en binario
			dos.writeInt(123); //Escribir un entero
			dos.writeDouble(45.67); //Escribir un double
			dos.writeBoolean(true); //Escribir un booleano
			dos.writeUTF("Hola mundo"); //Escribir un String en UTF-8
			
			System.out.println("Datos escrito correctamente en "+nombreArchivo);
			
		}catch(IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();		}
		
	}

}
