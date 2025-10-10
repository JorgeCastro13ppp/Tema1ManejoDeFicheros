package ejercicios;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class CrearEmpleadoEj26 {
	
	public static void main(String[] args) {
		
		EmpleadoEj26 empleadoCrear = new EmpleadoEj26("44411818Z","Jorge Leonardo",2100.00);
		
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("empleado.bin"))
				){
			oos.writeObject(empleadoCrear);
			System.out.println("Empleado guardado correctamente en 'empleado.bin'");
		}catch(IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
}
