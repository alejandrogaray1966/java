package org.example.entrega3.controllers;

import org.example.entrega3.dto.DTOReporte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.example.entrega3.service.InscripcionService;



import java.util.List;

@RestController
@RequestMapping("/inscriptos")
public class InscriptosController {
    @Autowired
    private InscripcionService estudianteCarreraService;

    @PostMapping("/matricular")
    // Endpoint para matricular un estudiante en una carrera
    // Ejemplo de uso: /estudiantes-carreras/matricular?estudianteId=1&carreraId=2
    public ResponseEntity<String> matricularEstudiante(@RequestParam int estudianteId, @RequestParam int carreraId) {
        try {
            estudianteCarreraService.matricularEstudiante(estudianteId, carreraId);
            return ResponseEntity.status(201).body("Estudiante matriculado en la carrera exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al matricular el estudiante en la carrera");
        }
    }

    @GetMapping("/reportes")
    public ResponseEntity<?> getReportesCarreras() {
        List<DTOReporte> reportes = estudianteCarreraService.getReportesCarreras();
        if(reportes.isEmpty()){
            // Codigo 204 "no content"
            return ResponseEntity.status(204).body("No hay reportes disponibles");
        }
        return ResponseEntity.status(200).body(reportes);
    }
}
