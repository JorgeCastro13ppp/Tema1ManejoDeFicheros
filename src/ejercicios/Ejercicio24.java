package ejercicios;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio24 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Introduce la ruta del fichero: ");
		String rutaFichero = sc.nextLine();
		
		//Ahora usamos el FileInputStream para abrir el fichero binario
		//Este flujo se encarga de abrir el fichero y leer bytes dentro de él 
		//NO interpreta los datos sólo los lee Byte a Byte
		
		//DataInputStream envuelve el flujo anterior y nos permite leer los mismos datos que escribimos anteriormente con DataOutputStream
		//
		try(DataInputStream dis = new DataInputStream(new FileInputStream(rutaFichero))){
			System.out.println("Registros del fichero: ");
			
			//Usamos el método dis.available porque es un método que nos permite saber 
			//el número de bytes que quedan en el fichero sin llegar al final
			//Si todavía quedan bytes sigue el bucle, sino termina
			while(dis.available()>0) {
				//No siempre es exacto en todos los sistemas, pero aquí funciona bien 
				//porque el fichero es pequeño y los datos están bien estructurados.
				int expediente = dis.readInt();
				
				double nota = dis.readDouble();
				
				StringBuilder nombre = new StringBuilder();
				char c;
				
				//El bucle va leyendo carácter por carácter
				//Cuando llega al salto de línea sabe que ha terminado el nombre y lo añade
				//Anteriormente lo añadimos así por eso lo leemos así 
				while((c = dis.readChar())!='\n') {
					nombre.append(c);
				}
				
				//Mostrar registro
				System.out.println("Expediente: "+expediente);
				System.out.println("Nota: "+nota);
				System.out.println("Nombre: "+nombre.toString());
				
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			sc.close();
		}
	}

}
