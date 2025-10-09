package ejercicios;

import java.io.Serializable;

public class TelefonoEj20 implements Serializable{
	
	//Es un identificador de clase 
	//Sirve para asegurar que si cambias de clase el programa pueda seguir leyendo los objetos antiguos guardados
	//No hace nada en la lógica del programa pero es una buena práctica OBLIGATORIA cuando una clase
	//es Serializable

	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private String numero;
	
	//En esta clase no hace falta constructor vacío 
	//Usamos serializable lo que significa que los objetos se van a guardar y leer desde un fichero usando flujos de objetos
	
	//Cuando java deserializa, es decir, lee un objeton desde un fichero
	//NO LLAMA al constructor parametrizado porque crea un objeto vacío en memoria y luego le asigna los valores de los atributos directamente
	
	
	//Funciona sin el constructor vacío porque no usa el mecanismo de serialización directamente
	
	public TelefonoEj20() {
		
	}
	
	//Sin embargo es una buena práctica, aún sabiendo todo ello lo pondremos
	//Mantenemos compatibilidad futura con librerías o Frameworks y seguimos el estándar JavaBeans
	
	public TelefonoEj20(String nombre,String numero) {
		this.nombre=nombre;
		this.numero=numero;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	
	@Override
	public String toString() {
		return "TelefonoEj20 [nombre=" + nombre + ", numero=" + numero + "]";
	}
}
