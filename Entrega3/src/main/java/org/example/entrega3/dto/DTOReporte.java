package org.example.entrega3.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DTOReporte {
    private String nombreCarrera;
    private Integer fechaEgreso;
    private Long cantInscriptos;
    private Long cantGraduados;

//    public DTOReporte(String nombreCarrera, Integer fechaEgreso, Long cantInscriptos, Long cantGraduados) {
//        this.nombreCarrera = nombreCarrera;
//        this.fechaEgreso = fechaEgreso;
//        this.cantInscriptos = cantInscriptos;
//        this.cantGraduados = cantGraduados;
//    }


    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public Integer getFechaEgreso() {
        return fechaEgreso;
    }

    public void setFechaEgreso(Integer anio) {
        this.fechaEgreso = anio;
    }

    public Long getCantInscriptos() {
        return cantInscriptos;
    }

    public void setCantInscriptos(Long cantInscriptos) {
        this.cantInscriptos = cantInscriptos;
    }

    public Long getCantGraduados() {
        return cantGraduados;
    }

    public void setCantGraduados(Long cantGraduados) {
        this.cantGraduados = cantGraduados;
    }

    @Override
    public String toString() {
        return "Carrera: " + nombreCarrera + ", Año: " + fechaEgreso + ", Cantidad Inscriptos: " + cantInscriptos
                + ", Cantidad Egresos: " + cantGraduados;
    }
}
