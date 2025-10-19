package org.example.entrega3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.entrega3.entities.Carrera;
import org.example.entrega3.entities.Estudiante;
import org.example.entrega3.entities.Inscripcion;
import org.example.entrega3.repository.RepositoryInscripcion;
import org.example.entrega3.dto.DTOReporte;


import java.util.List;
@Service
public class InscripcionService {
    @Autowired
    private RepositoryInscripcion estudianteCarreraRepositorio;
    @Autowired
    private CarreraService carreraService;
    @Autowired
    private EstudianteService estudianteService;

    @Transactional
    public void matricularEstudiante(int estudianteId, int carreraId) {
        Estudiante estudiante = estudianteService.findByIdEntity(estudianteId);
        Carrera carrera = carreraService.findByIdEntity(carreraId);
        if (estudiante != null && carrera != null) {
            Inscripcion estudianteCarrera = new Inscripcion();
            estudianteCarrera.setAlumno(estudiante);
            estudianteCarrera.setCarrera(carrera);
            estudianteCarreraRepositorio.save(estudianteCarrera);
        }
        else {
            throw new IllegalArgumentException("Estudiante o Carrera no encontrados");
        }
    }

    @Transactional(readOnly = true)
    public List<DTOReporte> getReportesCarreras() {
        return estudianteCarreraRepositorio.getReportesCarreras();
    }

}
