package ejercicios;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LeerEmpleadoEj26 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("empleado.bin"))
				){
			EmpleadoEj26 empleadoLeer =  (EmpleadoEj26) ois.readObject();
			System.out.println("Empleado leído correctamente del fichero");
			System.out.println(empleadoLeer);
		}catch(IOException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
