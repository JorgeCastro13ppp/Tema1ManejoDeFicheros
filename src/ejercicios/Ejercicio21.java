package ejercicios;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio21 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		try {
			System.out.println("Introduce la ruta del fichero a añadir: ");
			String rutaFichero = sc.nextLine();
			
			System.out.println("Introduce un número entre 32 y 126: ");
			int numero = sc.nextInt();
			
			if(numero<32 || numero>126) {
				System.out.println("No has introducido un número entre 32 y 126");
				sc.close();
				return;
			}else {
				try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(rutaFichero))	){
					dos.writeInt(numero);
					System.out.println("Número escrito correctamente");
				}catch(IOException i) {
					System.out.println(i.getMessage());
				}
				
				try(DataInputStream dis = new DataInputStream(new FileInputStream(rutaFichero)) ){
					int numeroLeer= dis.readInt();
					char caracter = (char) numeroLeer;
					System.out.println("Número leído: "+numeroLeer);
					System.out.println("Carácter ASCII: "+caracter);
				}
				
				
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}

}
