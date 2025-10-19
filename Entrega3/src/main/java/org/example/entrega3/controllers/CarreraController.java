package org.example.entrega3.controllers;

import java.util.List;

import org.example.entrega3.dto.DTOInscriptosCarrera;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.example.entrega3.service.CarreraService;

@RestController
@RequestMapping("/carreras")
public class CarreraController {
    // Inyectar el servicio de carreras
    @Autowired
    private CarreraService carreraService;

    @GetMapping("/")
    public ResponseEntity<?> getAllCarreras() {
        try {
            List<DTOInscriptosCarrera> carreras = carreraService.findAll();
            return ResponseEntity.status(200).body(carreras);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener carreras: " + e.getMessage());
        }
    }

    // f) recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad
    // de inscriptos.
    // Ejemplo de solicitud: GET /carreras/con-estudiantes
    @GetMapping("/con-estudiantes")
    public ResponseEntity<?> getCarrerasConEstudiantes() {
        try {
            return ResponseEntity.ok(carreraService.findCarrerasConEstudiantes());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener carreras con estudiantes: " + e.getMessage());
        }
    }
}
