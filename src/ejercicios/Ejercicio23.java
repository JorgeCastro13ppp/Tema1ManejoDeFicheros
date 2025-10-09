package ejercicios;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

public class Ejercicio23 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduce la ruta del fichero a crear: ");
		String rutaFichero = sc.nextLine();
		
		try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(rutaFichero))
				) {
			//Booleana para seguir añadiendo alumnos
			boolean continuar = true;
			
			while(continuar) {
				System.out.println("Introduce el número de expediente: ");
				int numExpediente = Integer.parseInt(sc.nextLine()) ;
				System.out.println("Introduce el nomnre del alumno: ");
				String nombreAlumno = sc.nextLine();
				System.out.println("Introduce la nota del alumno: ");
				double notaAlumno = Double.parseDouble(sc.nextLine());
				
				//Escribimos los datos en el fichero binario
				
				dos.writeInt(numExpediente); // 4bytes
				dos.writeChars(nombreAlumno);// 2 bytes por cada carácter
				dos.writeDouble(notaAlumno); // 8bytes
			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
