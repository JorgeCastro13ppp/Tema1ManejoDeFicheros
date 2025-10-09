package ejercicios;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

public class Ejercicio23 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduce la ruta del fichero a crear: ");
		String rutaFichero = sc.nextLine(); //alumnos.dat para simplificar este paso
		
		//FileOutputStream crea un fichero binario (si no existe, en caso de que no exista lo crea)
		//Sobreescribe su contenido, con bytes puros, NO texto
		//Trabaja directamente a nivel de bytes en el disco duro
		// DataOutputStream envuelve al FileOutputStream y lo mejora
		// Usamos DataOutputStream que permite escribir datos primitivos sin tener que convertirlos a bytres manualmente
		
		try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(rutaFichero))
				// DataOutputStream transforma los valores (int, double, char...) en bytes.
				//FileOutputStream se encarga de guardar esos bytes en el fichero físico.
				) {
			//Booleana para seguir añadiendo alumnos
			boolean continuar = true;
			
			while(continuar) {
				System.out.println("Introduce el número de expediente: ");
				int numExpediente = Integer.parseInt(sc.nextLine()) ;
				System.out.println("Introduce el nomnre del alumno: ");
				String nombreAlumno = sc.nextLine();
				System.out.println("Introduce la nota del alumno: ");
				String notaAlumno = sc.nextLine().replace(',','.'); //Sustituye coma por punto
				double notaAlumnoParseada = Double.parseDouble(notaAlumno);
				
				//Escribimos los datos en el fichero binario
				
				dos.writeInt(numExpediente); // 4bytes
				dos.writeDouble(notaAlumnoParseada); // 8 bytes
				dos.writeChars(nombreAlumno +"\n");// 2 bytes por cada carácter
				
				
				//El ordenador no guarda texto, sino los valores binarios reales de cada tipo de dato.
				//Por eso luego necesitamos un DataInputStream para leerlos correctamente
				System.out.println("Alumno guardado correctamente.");
				
				//Seguimos añadiendo alumnos o no según pida el usuario
				System.out.println("Deseas añadir otro alumno? (S/N");
				String respuesta = sc.nextLine();
				if(!respuesta.equalsIgnoreCase("s")) {
					continuar=false;
					System.out.println("Has salido del programa");
				}
			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}finally {
			sc.close();
		}
	}

}
