package ejercicios;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Ejercicio32 {

    // Tamaño fijo del registro: 4 (int) + 40 (20 chars * 2 bytes) + 8 (double) + 4 (int)
    private static final int TAM_REGISTRO = 56;
    private static final int TAM_NOMBRE = 20;

    public static void main(String[] args) {
        String ruta = "productos.bin";

        try (RandomAccessFile raf = new RandomAccessFile(ruta, "rw")) {

            // Escribir 3 productos en el archivo (fijos para el ejemplo)
            escribirProducto(raf, 1, "Leche", 1.50, 100);
            escribirProducto(raf, 2, "Pan", 8.75, 200);
            escribirProducto(raf, 3, "Zumo", 15.30, 50);

            // Mostrar el producto con código 2 (buscándolo, no por posición)
            leerProductoPorCodigo(raf, 2);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Método que recorre el archivo buscando un producto por su código
    private static void leerProductoPorCodigo(RandomAccessFile raf, int codigoBuscado) throws IOException {
        raf.seek(0); // Empezamos desde el inicio
        boolean encontrado = false;

        // Mientras no lleguemos al final del archivo
        while (raf.getFilePointer() < raf.length()) {
            // Leemos el código de producto (primer campo del registro)
            int codigo = raf.readInt();

            // Leemos el nombre (20 caracteres = 40 bytes)
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < TAM_NOMBRE; i++) {
                sb.append(raf.readChar());
            }
            String nombre = sb.toString().trim();

            double precio = raf.readDouble();
            int cantidad = raf.readInt();

            if (codigo == codigoBuscado) {
                System.out.printf("Producto: %d\nNombre: %s\nPrecio: %.2f\nStock: %d\n",
                        codigo, nombre, precio, cantidad);
                encontrado = true;
                break; // Salimos del bucle, ya encontramos el producto
            }
        }

        if (!encontrado) {
            System.out.println("No se encontró el producto con código " + codigoBuscado);
        }
    }

    // Método que escribe un producto (siempre al final del archivo)
    private static void escribirProducto(RandomAccessFile raf, int codigo, String nombre, double precio, int cantidad)
            throws IOException {

        // Mover el puntero al final del archivo (modo append)
        raf.seek(raf.length());

        // Escribimos los campos en orden: código, nombre, precio, cantidad
        raf.writeInt(codigo);

        // Nombre a longitud fija (rellenamos o truncamos hasta 20 caracteres)
        StringBuilder sb = new StringBuilder(nombre);
        sb.setLength(TAM_NOMBRE);
        raf.writeChars(sb.toString());

        raf.writeDouble(precio);
        raf.writeInt(cantidad);
    }
}
