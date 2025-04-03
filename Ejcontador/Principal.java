package Ejcontador;

public class Principal {
	
	public static void main(String[] args) {
	
	System.out.println("---------------------------------------------------------------------------------------------------------");
	System.out.println("                             Ejercicio que maneja un contador STATIC");
	System.out.println("---------------------------------------------------------------------------------------------------------");
	System.out.println("Valor del Contador sin haber instanciado ningun alumno: " + Alumno.getcontador());
	System.out.println();
	Alumno a1 = new Alumno("Juan");
	Alumno a2 = new Alumno("Pedro");
	Alumno a3 = new Alumno("Tomas");
	System.out.println(a1);
	System.out.println(a2);
	System.out.println(a3);
	System.out.println();
	System.out.println("Valor del Contador luego de instanciar a 3 alumnos: " + Alumno.getcontador());
	System.out.println();
	System.out.println();
	System.out.println("--------FIN --------");

	}
}
