package org.example.entrega3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.example.entrega3.dto.DTOEstudiante;
import org.example.entrega3.entities.Estudiante;
import org.example.entrega3.repository.RepositoryEstudiante;



import java.util.ArrayList;
import java.util.List;

@Service
public class EstudianteService {
    // Inyectar el repositorio de estudiantes
    @Autowired
    private RepositoryEstudiante estudianteRepositorio;

    @Transactional(readOnly = true)
    public List<DTOEstudiante> findAll() {
        return estudianteRepositorio.findAll().stream().map(DTOEstudiante::new).toList();
    }

    @Transactional(readOnly = true)
    public DTOEstudiante findById(int id) {
        return estudianteRepositorio.findById(id).map(DTOEstudiante::new)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
    }

    @Transactional(readOnly = true)
    public Estudiante findByIdEntity(int id){
        return estudianteRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
    }

    @Transactional
    public DTOEstudiante save(Estudiante estudiante) {
        Estudiante esSave = estudianteRepositorio.save(estudiante);
        return new DTOEstudiante(esSave);
    }

    @Transactional
    public void deleteById(int id) {
        estudianteRepositorio.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<DTOEstudiante> getEstudiantesOrderBy(String criterio) {
        criterio = criterio.toLowerCase();

        List<String> camposOrdenados = List.of("dni", "nombre","apellido", "nroLibreta", "genero", "edad", "ciudad");
        if(!camposOrdenados.contains(criterio)){
            return new ArrayList<>();
        }

        return estudianteRepositorio.findAll(Sort.by(criterio))
                .stream().map(DTOEstudiante::new).toList();
    }

    @Transactional(readOnly = true)
    public DTOEstudiante findEstudianteByNroLibreta(int nroLibreta) {
        Estudiante estudiante = estudianteRepositorio.findEstudianteByNroLibreta(nroLibreta);
        if (estudiante == null) {
            throw new RuntimeException("Estudiante con libreta " + nroLibreta + " no encontrado");
        }
        return new DTOEstudiante(estudiante);
    }

    @Transactional(readOnly = true)
    public List<DTOEstudiante> findEstudiantesByGenero(String genero) {
        return estudianteRepositorio.findEstudiantesByGenero(genero)
                .stream().map(DTOEstudiante::new).toList();
    }

    @Transactional(readOnly = true)
    public List<DTOEstudiante> findEstudiantesByCarreraAndCiudad(String carrera,
                                                                 String ciudad) {
        return estudianteRepositorio.findEstudiantesByCarreraAndCiudad(carrera, ciudad)
                .stream().map(DTOEstudiante::new).toList();
    }
}