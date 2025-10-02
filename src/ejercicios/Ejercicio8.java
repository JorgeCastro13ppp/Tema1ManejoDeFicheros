package ejercicios;

import java.io.File;
import java.util.Scanner;

public class Ejercicio8 {
	
	private static void mostrarContenido(File archivo,String prefijo,boolean esUltimo) {
		try {
			// 1) Prepara la etiqueta: [D] si es directorio, [F] si es fichero
			String tipo = archivo.isDirectory()?"[D]":"[F]";
			// 2) Imprime la línea actual: prefijo + símbolo (`└──` o `├──`) + etiqueta + nombre
			System.out.println(prefijo + (esUltimo?"└──":"├──")+tipo+ archivo.getName());
					 
			if(archivo.isDirectory()) {
				File [] archivos = archivo.listFiles(); // puede devolver null si no hay permisos o error
				if(archivos!=null) {
					for(int i=0;i<archivos.length;i++) {
						
						boolean ultimo = (i==archivos.length -1); // ¿es el último hijo en este directorio? "¿Estoy en la última posición del array?"
						
						// 4) Calcula el nuevo prefijo para los hijos
	                    //    - Si este archivo actual (padre) es el último hermano de su padre, se añade "    " (espacios)
	                    //    - Si no lo es, se añade "│   " para mostrar la línea vertical que continúa
						
						// 5) Llamada recursiva para el hijo
						mostrarContenido(archivos[i], prefijo+(esUltimo?"  ":"|  "),ultimo);
					}
				}
			}
		}catch(Exception e) {
			System.out.println("Error accediendo a "+archivo.getAbsolutePath()+": "+e.getMessage());
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		File carpeta = null;
		
		try {
			System.out.println("Introduce la ruta de la carpeta: ");
			String ruta = sc.nextLine();
			carpeta = new File(ruta);
			
			if(carpeta.exists() && carpeta.isDirectory()) {
				System.out.println("Contenido de la carpeta: "+ carpeta.getAbsolutePath());
				mostrarContenido(carpeta,"",true);
			}else {
				System.out.println("La ruta no existe o no es una carpeta.");
			}
		}catch(Exception e ) {
			System.out.println("Error general: "+e.getMessage());
		}finally {
			sc.close();
			System.out.println("Programa finalizado.");
		}
	}

}
