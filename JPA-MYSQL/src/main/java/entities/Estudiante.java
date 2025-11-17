package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Estudiante implements Serializable {
	
	// Java requiere que las clases serializables definan un identificador de versión 
	// para asegurar la compatibilidad durante la serialización y deserialización a lo largo del tiempo
	private static final long serialVersionUID = 1L;
	
    @Id
    private int dni;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private int edad;

    @Column
    private String genero;

    @Column
    private String ciudad;

    @Column
    private int nroLibreta;

    // Relación
    @OneToMany(mappedBy="alumno", fetch=FetchType.LAZY)
    private List<Inscripcion> inscripciones;

    // Constructores
    public Estudiante() {}

    public Estudiante(int idEstudiante) {
        this.dni = idEstudiante;
        this.inscripciones = new ArrayList<>();
    }

    public Estudiante(String nombre, String apellido, int edad, String genero, String ciudad, int nroLibreta) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.genero = genero;
        this.ciudad = ciudad;
        this.nroLibreta = nroLibreta;
        this.inscripciones = new ArrayList<>();
    }

    public Estudiante(int idEstudiante, String nombre, String apellido, int edad, String genero, String ciudad,int nroLibreta) {
        this(nombre, apellido, edad, genero, ciudad, nroLibreta);
        this.dni = idEstudiante;
    }

    // Getters y Setters
    public int getIdEstudiante() {
        return dni;
    }

    public int getDni() {
        return dni;
    }

    public int getNroLibreta() {
        return nroLibreta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "dni=" + dni +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad=" + edad +
                ", genero='" + genero + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", nroLibreta=" + nroLibreta +
                '}';
    }
}
