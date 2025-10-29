package ejercicios;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Ejercicio33 {
	private static final int TAM_NOMBRE = 20;
	private static final int TAM_REGISTRO = 4 + 2 * TAM_NOMBRE + 8 + 4;

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
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
		System.out.println("Código del producto a borrar:");
		int codigoBorrar = sc.nextInt();
		sc.nextLine();
		
		long posicion = (codigoBorrar -1)*TAM_REGISTRO;
		if(posicion >= archivo.length()) {
			System.out.println("No existe este producto.");
			return;
		}
		
		//Comprobar nombre actual
		archivo.seek(posicion + 4);
		char primer = archivo.readChar();
		if(primer=='*') {
			System.out.println("El producto ya está marcado como borrado.");
		}
		
		archivo.seek(posicion);
		archivo.writeChar('*');
		System.out.println("Producto marcado como borrado.");
	}

	private static void modificarStock(Scanner sc, RandomAccessFile archivo) throws IOException{
		// TODO Auto-generated method stub
		System.out.println("Código del producto a modificar:");
		int codigoModificar = sc.nextInt();
		sc.nextLine();
		
		long posicion = (codigoModificar-1) * TAM_REGISTRO;
		if(posicion>=archivo.length()) {
			System.out.println("No existe ese producto.");
			return;
		}
		
		//Comprobar si está marcado como borrado
		archivo.seek(posicion + 4);//Saltar código
		StringBuilder sb = new StringBuilder();
		for(int i = 0;i<TAM_NOMBRE;i++) {
			sb.append(archivo.readChar());
		}
		String nombre = sb.toString().trim();
		if(nombre.startsWith("*")) {
			System.out.println("El producto está marcado como borrado.");
			return;
		}
		
		
		archivo.seek(posicion + 4 + 2 * TAM_NOMBRE + 8 );
		System.out.println("Nuevo stock:");
		int nuevoStock = sc.nextInt();
		archivo.writeInt(nuevoStock);
		
		System.out.println("Stock actualizado.");
	}

	private static void listarProductos(RandomAccessFile archivo) throws IOException{
		// TODO Auto-generated method stub
		
		if(archivo.length()==0) {
			System.out.println("No hay productos.");
			return;
		}
		
		archivo.seek(0);
		while(archivo.getFilePointer() < archivo.length()) {
			
			int codigoListar = archivo.readInt();
			
			StringBuilder nombreObtener = new StringBuilder();
			
			for(int i=0;i<20;i++) {
				nombreObtener.append(archivo.readChar());
			}
			
			double precioListar = archivo.readDouble();
			int stockListar = archivo.readInt();
			String nombreListar = nombreObtener.toString().trim();
			
			if(!nombreListar.startsWith("*") && codigoListar!=0) {
				 System.out.printf("Código: %d | Nombre: %s | Precio: %.2f | Stock: %d%n",
	                        codigoListar, nombreListar, precioListar, stockListar);
			}
		
		}
	}

	private static void altaProducto(Scanner sc, RandomAccessFile archivo) throws IOException{
		// TODO Auto-generated method stub
		System.out.println("Introduce código del producto:");
		int codigoAlta = sc.nextInt();
		sc.nextLine();
		
		long posicion = (codigoAlta -1) * TAM_REGISTRO;
		
		//Comprobamos si ya existe este producto en el fichero
		if(posicion<archivo.length()) {
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
		
		StringBuilder sb = new StringBuilder(nombreAlta);
		sb.setLength(TAM_NOMBRE);
		archivo.writeInt(codigoAlta);
		archivo.writeChars(sb.toString());
		archivo.writeDouble(precioAlta);
		archivo.writeInt(stockAlta);
		
		System.out.println("Producto añadido correctamente.");
		
	}

}



