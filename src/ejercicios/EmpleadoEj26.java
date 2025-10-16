package ejercicios;

import java.io.Serializable;
import java.util.Objects;

public class EmpleadoEj26 implements Serializable{
	
	/*Sirve para especificar una versión explícita de una clase serializable, 
	lo cual es crucial para la deserialización, el proceso de reconstruir un objeto a partir 
	de un flujo de bytes, garantizando que el objeto deserializado sea compatible con la clase original. 
	Si no se define, Java genera un serialVersionUID por defecto, pero esto puede causar errores si la clase 
	se modifica, por lo que se recomienda 
	definirlo siempre para evitar inconsistencias durante la deserialización de objetos. */
	
	private static final long serialVersionUID = 1L; 
	
	// asegura que, aunque cambie la clase, los objetos antiguos se puedan leer sin problemas.
	
	private String dni;
	private String nombre;
	private double sueldo;
	
	public EmpleadoEj26() {
	}

	public EmpleadoEj26(String dni, String nombre, double sueldo) {
		this.dni = dni;
		this.nombre = nombre;
		this.sueldo = sueldo;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getSueldo() {
		return sueldo;
	}

	public void setSueldo(double sueldo) {
		this.sueldo = sueldo;
	}

	@Override
	public String toString() {
		return "EmpleadoEj26 [dni=" + dni + ", nombre=" + nombre + ", sueldo=" + sueldo + "]";
	}
	
	//Ejercicio 28
	//Es fundamental sobreescribir equals() y hashCode() 
	//Así Set funcionará correctamente y detectará duplicados por DNI
	//Lo generamos con source, o manualmente de 0, igualmente debemos modificarlo si utilizamos el source
	//Estos dos métodos son correctos pero está mejor si controlamos las mayúsculas y minúsculas para evitar falsos duplicados
	@Override
	public int hashCode() {
		//return Objects.hash(dni);
		return dni.toLowerCase().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmpleadoEj26 other = (EmpleadoEj26) obj;
		//return Objects.equals(dni, other.dni);
		return dni.equalsIgnoreCase(other.dni);
	}
	
	
	
	
	
	
}
