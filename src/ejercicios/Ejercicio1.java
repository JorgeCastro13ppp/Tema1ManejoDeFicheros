package ejercicios;

import java.io.File;

public class Ejercicio1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Mostrar la ruta de la carpeta actual
		 File carpetaActual = new File("."); // "." representa la carpeta actual
		 System.out.println("Ruta absoluta es: "+carpetaActual.getAbsolutePath()); //Método para obtener la ruta absoluta
	}

}
