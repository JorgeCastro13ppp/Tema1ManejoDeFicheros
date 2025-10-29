package ejercicios;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Ejercicio33 {
	private static final int TAM_NOMBRE = 20;//Cada nombre ocupa siempre 20 caracteres (char)
	private static final int TAM_REGISTRO = 4 + 2 * TAM_NOMBRE + 8 + 4; //Tamaño total del registro de bytes, 56b
	// 4 bytes el int de código, 2 bytes * TAM_NOMBRE ( cada char ocupa 2 bytes), 8 bytes el double de precio, 4 bytes el int de stock

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		//Primero abrimos o creamos el archivo productos.dat con permisos de lectura y escritura
		RandomAccessFile archivo = new RandomAccessFile("productos.dat","rw");
		Scanner sc = new Scanner (System.in);
		int opcion;
		
		do {
            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Dar de alta producto");
            System.out.println("2. Listar productos");
            System.out.println("3. Modificar stock");
            System.out.println("4. Borrar producto");
            System.out.println("0. Salir");
            System.out.print("Elige opción: ");
            opcion = sc.nextInt();
            sc.nextLine();
            
            switch (opcion) {
	            case 0:
	            	System.out.println("Saliendo...");
	            	break;
	            case 1:
	            	altaProducto(sc,archivo);
	            	break;
	            case 2:
	            	listarProductos(archivo);
	            	break;
	            case 3:
	            	modificarStock(sc,archivo);
	            	break;
	            case 4:
	            	borrarProducto(sc,archivo);
	            	break;
	            default:
	            	System.out.println("Opción inválida.");
	            	break;
            }
            
		}while(opcion!=0);
	}

	private static void borrarProducto(Scanner sc, RandomAccessFile archivo) throws IOException{
		// TODO Auto-generated method stub
		//Simulamos el borrado del producto con un * la principio del nombre
		System.out.println("Código del producto a borrar:");
		int codigoBorrar = sc.nextInt();
		sc.nextLine();
		
		long posicion = (codigoBorrar -1)*TAM_REGISTRO;
		//Primero verificamos que el producto exista
		if(posicion >= archivo.length()) {
			System.out.println("No existe este producto.");
			return;
		}
		
		//Comprobar nombre actual, para saber si ya está marcado
		archivo.seek(posicion + 4);
		char primer = archivo.readChar();
		if(primer=='*') {
			System.out.println("El producto ya está marcado como borrado.");
		}
		
		archivo.seek(posicion + 4);
		archivo.writeChar('*');
		System.out.println("Producto marcado como borrado.");
	}

	private static void modificarStock(Scanner sc, RandomAccessFile archivo) throws IOException{
		// TODO Auto-generated method stub
		//Permite actualizar el stock de un producto
		System.out.println("Código del producto a modificar:");
		int codigoModificar = sc.nextInt();
		sc.nextLine();
		//Primero calcula donde está el producto según su código
		long posicion = (codigoModificar-1) * TAM_REGISTRO;
		if(posicion>=archivo.length()) {
			System.out.println("No existe ese producto.");
			return;
		}
		
		//Comprobar si está marcado como borrado
		archivo.seek(posicion + 4);//Saltar código 4 bytes para empezar a leer el nombre
		StringBuilder sb = new StringBuilder();
		for(int i = 0;i<TAM_NOMBRE;i++) {
			sb.append(archivo.readChar());
		}
		String nombre = sb.toString().trim();
		if(nombre.startsWith("*")) {//No modifica productos borrados
			System.out.println("El producto está marcado como borrado.");
			return;
		}
		
		
		archivo.seek(posicion + 4 + 2 * TAM_NOMBRE + 8 );//Nos colocamos justo donde empieza el campo stock
		System.out.println("Nuevo stock:");
		int nuevoStock = sc.nextInt();
		archivo.writeInt(nuevoStock);
		
		System.out.println("Stock actualizado.");
	}

	private static void listarProductos(RandomAccessFile archivo) throws IOException{
		// TODO Auto-generated method stub
		//Este método recorre todo el fichero y muestra solo los productos válidos
		if(archivo.length()==0) {
			System.out.println("No hay productos.");
			return;
		}
		
		archivo.seek(0);//Empieza desde el inicio
		//No para hasta que no llega al final
		while(archivo.getFilePointer() < archivo.length()) {
			
			int codigoListar = archivo.readInt();
			
			StringBuilder nombreObtener = new StringBuilder();
			
			for(int i=0;i<20;i++) {//Lee 20 caracteres cada uno con 2 bytes
				nombreObtener.append(archivo.readChar());
			}
			
			double precioListar = archivo.readDouble();
			int stockListar = archivo.readInt();
			String nombreListar = nombreObtener.toString().trim();
			
			if(!nombreListar.startsWith("*") && codigoListar!=0) {//Filtra los registros vacíos o borrados
				 System.out.printf("Código: %d | Nombre: %s | Precio: %.2f | Stock: %d%n",
	                        codigoListar, nombreListar, precioListar, stockListar);
			}
		
		}
	}

	private static void altaProducto(Scanner sc, RandomAccessFile archivo) throws IOException{
		// TODO Auto-generated method stub
		//Este método da de alta un producto en una posición calculada a partir de su código
		System.out.println("Introduce código del producto:");
		int codigoAlta = sc.nextInt();
		sc.nextLine();
		
		long posicion = (codigoAlta -1) * TAM_REGISTRO; //Calcula la posición del registro en el fichero
		//Por ejemplo, el producto con código 1 estará en la posición 0 bytes, al inicio
		//El 2 en la posición 56, el 3 en la 112
		
		//Comprobamos si ya existe este producto en el fichero
		if(posicion<archivo.length()) { //Si el fichero tiene datos hasta esa posición, se asume que ese código está usado
			//Se mueve el puntero .seek y se lee el código existente
			archivo.seek(posicion);
			int codigoExistente = archivo.readInt();
			
			//Leer nombre actual para comprobar si está borrado
			StringBuilder sbc = new StringBuilder();
			for(int i=0;i<TAM_NOMBRE;i++) {
				sbc.append(archivo.readChar());
			}
			String nombreExistente = sbc.toString().trim();
			
			//Si no está borrado (no empieza por '*'), ya existe ese producto
			System.out.println("Ya existe un producto con ese código.");
			return;//Salir del método y volver al menú
		}
		System.out.println("Nombre:");
		String nombreAlta = sc.nextLine();
		
		System.out.println("Precio:");
		String precioStr = sc.nextLine().replace(',', '.'); // sustituye , por .
		double precioAlta = Double.parseDouble(precioStr);
		
		System.out.println("Stock:");
		int stockAlta = sc.nextInt();
		sc.nextLine();
		
		
		archivo.seek(posicion);
		
		//Creamos un StringBuilder con el nombre ajustado a 20 caracteres exactos
		//Rellenando con espacios si hace falta
		StringBuilder sb = new StringBuilder(nombreAlta);
		sb.setLength(TAM_NOMBRE);
		archivo.writeInt(codigoAlta);
		archivo.writeChars(sb.toString());
		archivo.writeDouble(precioAlta);
		archivo.writeInt(stockAlta);
		//Se escribe todo en orden
		//Cada vez que ejecutamos esto el seek avanza automáticamente 56 bytes
		
		System.out.println("Producto añadido correctamente.");
		
	}

}



