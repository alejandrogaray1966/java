package org.example.entrega3.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.entrega3.entities.Estudiante;


@AllArgsConstructor
@Getter
@RequiredArgsConstructor

public class DTOEstudiante {

    private String nombre;
    private String apellido;
    private int edad;
    private String genero;
    private int dni;
    private String ciudad;
    private int nroLibreta;


    public DTOEstudiante(Estudiante estu) {
        this.nombre = estu.getNombre();
        this.apellido = estu.getApellido();
        this.edad = estu.getEdad();
        this.genero = estu.getGenero();
        this.dni = estu.getDni();
        this.ciudad = estu.getCiudad();
        this.nroLibreta = estu.getNroLibreta();
    }


    @Override
    public String toString() {
        return "DTOAlumno{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad=" + edad +
                ", genero='" + genero + '\'' +
                ", dni=" + dni +
                ", ciudad='" + ciudad + '\'' +
                ", nroLibreta=" + nroLibreta +
                '}';
    }

}
