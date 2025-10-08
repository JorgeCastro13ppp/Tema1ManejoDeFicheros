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

	public TelefonoEj20(String nombre,String numero) {
		this.nombre=nombre;
		this.numero=numero;
	}
	@Override
	public String toString() {
		return "TelefonoEj20 [nombre=" + nombre + ", numero=" + numero + "]";
	}
}
