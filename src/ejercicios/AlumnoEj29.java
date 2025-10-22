package ejercicios;

import java.io.Serializable;
import java.util.Objects;

public class AlumnoEj29 implements Serializable{ 
	private static final long SerialVersionUID = 1L; //Recomendado para objetos serializables
	
	private int expediente;
	private String nombre;
	private double nota;
	
	public AlumnoEj29() {
		
	}

	public AlumnoEj29(int expediente, String nombre, double nota) {
		this.expediente = expediente;
		this.nombre = nombre;
		//this.nota = nota;
		this.nota = -1; //Modificamos la nota al inicializar el constructor para saber que no tiene nota 
	}

	public int getExpediente() {
		return expediente;
	}

	public void setExpediente(int expediente) {
		this.expediente = expediente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}

	@Override
	public String toString() {
		String notaTexto = (nota == -1) ? "Sin nota" : String.format("%.2f", nota);
		return "AlumnoEj29 [expediente=" + expediente + ", nombre=" + nombre + ", nota=" + notaTexto + "]";
	}
	
	//Relizamos el hashCode y el equals basados en el expediente porque solo hay uno por alumno

	@Override
	public int hashCode() {
		return Objects.hash(expediente);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AlumnoEj29 other = (AlumnoEj29) obj;
		return expediente == other.expediente;
	}
	
	
}
