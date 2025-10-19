package org.example.entrega3.controllers;

//import com.integrador3.dto.EstudianteDTO;
//import com.integrador3.model.Estudiante;
//import com.integrador3.servicios.EstudianteService;

import org.example.entrega3.dto.DTOEstudiante;
import org.example.entrega3.entities.Estudiante;
import org.example.entrega3.service.EstudianteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {
    @Autowired
    private EstudianteService estudianteService;

    @GetMapping("/")
    public ResponseEntity<?> getEstudiantes() {
        try {
            return new ResponseEntity<>(estudianteService.findAll(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Error al obtener estudiantes: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEstudianteById(@PathVariable int id) {
        try {
            DTOEstudiante estudiante = estudianteService.findById(id);
            if (estudiante == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estudiante no encontrado");
            }
            return ResponseEntity.status(HttpStatus.OK).body(estudiante);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener estudiante: " + e.getMessage());
        }

    }

    // c) recuperar todos los estudiantes, y especificar algún criterio de
    // ordenamiento simple.
    // Ejemplo de solicitud: GET /estudiantes/ordenar?criterio=nombre
    @GetMapping("/ordenar")
    public ResponseEntity<?> getEstudiantesOrderBy(@RequestParam String criterio) {
        try {
            List<DTOEstudiante> estudiantes = estudianteService.getEstudiantesOrderBy(criterio);
            if (estudiantes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Criterio de ordenamiento no valido");
            }
            return ResponseEntity.status(HttpStatus.OK).body(estudiantes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener estudiantes: " + e.getMessage());
        }
    }

    // d) recuperar un estudiante por su número de libreta universitaria.
    // Ejemplo de solicitud: GET /estudiantes/nroLibreta/12345
    @GetMapping("/nroLibreta/{nroLibreta}")
    public ResponseEntity<?> getEstudianteByNroLibreta(@PathVariable int nroLibreta) {
        try {
            DTOEstudiante estudiante = estudianteService.findEstudianteByNroLibreta(nroLibreta);
            if (estudiante == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Estudiante con libreta " + nroLibreta + " no encontrado");
            }
            return ResponseEntity.ok(estudiante);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar estudiante: " + e.getMessage());
        }
    }

    // e) recuperar todos los estudiantes, en base a su género.
    // Ejemplo de solicitud: GET /estudiantes/genero/Masculino
    @GetMapping("/genero/{genero}")
    public ResponseEntity<?> getEstudiantesByGenero(@PathVariable String genero) {
        try {
            List<DTOEstudiante> estudiantes = estudianteService.findEstudiantesByGenero(genero);
            if (estudiantes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontraron estudiantes con género: " + genero);
            }
            return ResponseEntity.status(HttpStatus.OK).body(estudiantes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener estudiantes: " + e.getMessage());
        }
    }

    // g) recuperar los estudiantes de una determinada carrera, filtrado por ciudad
    // de residencia.
    // Ejemplo de solicitud: GET
    // /estudiantes/filtro?carrera=Ingenieria&ciudad=Tandil
    @GetMapping("/filtro")
    public ResponseEntity<?> getEstudiantesByCarreraAndCiudad(
            @RequestParam String carrera,
            @RequestParam String ciudad) {
        try {
            List<DTOEstudiante> estudiantes = estudianteService.findEstudiantesByCarreraAndCiudad(carrera, ciudad);
            if (estudiantes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontraron estudiantes en la carrera " + carrera + " y ciudad " + ciudad);
            }
            return ResponseEntity.status(HttpStatus.OK).body(estudiantes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener estudiantes: " + e.getMessage());
        }
    }

    // a) dar de alta un estudiante
    // Ejemplo de solicitud: POST /estudiantes
    @PostMapping("/")
    public ResponseEntity<?> createEstudiante(@RequestBody Estudiante estudiante) {
        try {
            DTOEstudiante nuevoEstudiante = estudianteService.save(estudiante);
            return new ResponseEntity<>(nuevoEstudiante, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear estudiante: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
