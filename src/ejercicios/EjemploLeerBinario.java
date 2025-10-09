package ejercicios;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class EjemploLeerBinario {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String nombreArchivo = "datos.bin";
		
		try(FileInputStream fis = new FileInputStream(nombreArchivo);
			DataInputStream dis = new DataInputStream(fis)	){
			//Leer los datos en el mismo orden que los escribimos
			int numero = dis.readInt();
			double decimal = dis.readDouble();
			boolean booleano = dis.readBoolean();
			String texto = dis.readUTF();
			
			System.out.println("Número: "+numero);
			System.out.println("Decimal: "+decimal);
			System.out.println("Booleano: "+booleano);
			System.out.println("Texto: "+texto);
			
		}catch(IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
