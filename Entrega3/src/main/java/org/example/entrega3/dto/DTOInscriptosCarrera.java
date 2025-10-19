package org.example.entrega3.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.entrega3.entities.Carrera;


@AllArgsConstructor
@Getter
@RequiredArgsConstructor
public class DTOInscriptosCarrera {

    private String nombreCarrera;
    private long cantInscriptos;

    public DTOInscriptosCarrera(Carrera carrera) {
        this.nombreCarrera = carrera.getNombreCarrera();
        this.cantInscriptos = carrera.getInscripciones().size();
    }


    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public long getcantInscriptos() {
        return cantInscriptos;
    }

    @Override
    public String toString() {
        return "Carrera: " + nombreCarrera
                + ", Cantidad Inscriptos: " + cantInscriptos;
    }

}
