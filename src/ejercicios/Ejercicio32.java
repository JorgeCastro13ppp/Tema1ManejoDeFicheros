package ejercicios;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Ejercicio32 {
	//Tamaño fijo de un registro 4+40+8+4 = 56 bytes
	private static final int TAM_REGISTRO = 56;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String ruta = "productos.bin";
		
		try(RandomAccessFile raf = new RandomAccessFile(ruta,"rw")){
			
			//Escribir 3 productos
			EscribirProducto(raf,1,"leche",1.50,100);
			EscribirProducto(raf,2,"pan",8.75,200);
			EscribirProducto(raf,3,"zumo",13.99,300);
			
			//Leer el producto número
			int numeroProducto = 1;
			
			leerProducto(raf,numeroProducto);
			
		}catch(IOException e) {
			System.out.println("Error: "+e.getMessage());
		}
	}

	//Throws se usa en la firma del método para indicar que el método podría generar excepciones
	private static void leerProducto(RandomAccessFile raf, int numeroProducto) throws IOException{
		// TODO Auto-generated method stub
		//Método para acceder a un producto por su número
		long posicionLeer = (numeroProducto-1) * TAM_REGISTRO;
		
		//Movemos el puntero del archivo hasta la posición calculada
		//Así podemos leer directamente el producto que queremos
		raf.seek(posicionLeer);
		
		//Leemos los campos en el mismo orden en el que los escribimos
		int codigo = raf.readInt();
		
		//Leer los 20 caracteres del nombre, cada char = 2bytes -> 40bytes
		//Esto reconstruye el nombre caracter por caracter
		StringBuilder sb = new StringBuilder();
		for(int i = 0;i<20;i++) {
			sb.append(raf.readChar());
		}
		
		//Convertimos el StringBuilder en String y eliminamos los espacios de relleno
		String nombreLeer = sb.toString().trim();
		double precioLeer = raf.readDouble(); //8 bytes -> precio del producto
		int cantidadLeer = raf.readInt();//4 bytes -> cantidad en stock
		
		//Mostrar el producto
		System.out.println("Producto: "+codigo);
		System.out.println("Nombre:"+nombreLeer);
		System.out.println("Precio:"+precioLeer);
		System.out.println("Cantidad en stock:"+cantidadLeer);
	}
	
	//Método para escribir un producto en una posición determinada
	private static void EscribirProducto(RandomAccessFile raf, int codigo, String nombre, double precio, int cantidad) throws IOException{
		// TODO Auto-generated method stub
		// Calculamos la posición dentro del archivo
		// El registro 1 empieza en la posición 0
		// El registro 2 empieza en la posición 56
		// El registro 3 empieza en la posición 112
		long posicion = (codigo -1) * TAM_REGISTRO;
		raf.seek(posicion); //Mueve el puntero
		
		//Escribir los datos en orden
		raf.writeInt(codigo); //4 bytes
		
		//Nombre con longitud fija (20 caracteres)
		StringBuilder sb = new StringBuilder(nombre);
		sb.setLength(20); //Rellena con espacios o corta con 20 caracteres
		raf.writeChars(sb.toString()); //40 bytes -> 2 caracteres
		
		raf.writeDouble(precio); //8 bytes
		raf.writeInt(cantidad); // 4 bytes
		
	}

}
