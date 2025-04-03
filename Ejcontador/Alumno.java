package Ejcontador;

public class Alumno {
	
	private static int contador = 0;
	private int numero;
	private String nombre;
	
	public Alumno(String nombre) {
		contador++;
		this.numero = contador;
		this.nombre = nombre;
	}
	
	public int getNumero() {
		return numero;
	}
	
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public static int getcontador() {
		return contador;
	}

	@Override
	public String toString() {
		return "Alumno [numero=" + numero + ", nombre=" + nombre + "]";
	}
	
	
	
}
