package org.example.entrega3.repository;

import org.example.entrega3.dto.DTOReporte;
import org.example.entrega3.entities.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryInscripcion extends JpaRepository<Inscripcion, Integer>{

    @Query("SELECT new org.example.entrega3.dto.DTOReporte( fj.nombreCarrera, fj.anio, SUM(fj.inscriptos) AS totalInscriptos, SUM(fj.graduados) AS totalGraduados) " +
            "FROM (" + // <-- ¡Paréntesis de apertura CRÍTICO!
            // 1. Inscritos (usando el campo i.fechaInscripcion directamente como anio)
            "SELECT c.nombreCarrera, i.fechaInscripcion AS anio, COUNT(*) AS inscriptos, 0 AS graduados " +
            "FROM Inscripcion i JOIN Carrera c ON c.idCarrera = i.carrera_idCarrera " +
            "GROUP BY c.nombreCarrera, anio " +
            "UNION ALL " +
            // 2. Graduados (usando el campo i.fechaEgreso directamente como anio)
            "SELECT c.nombreCarrera, i.fechaEgreso AS anio, 0 AS inscriptos, COUNT(*) AS graduados " +
            "FROM Inscripcion i JOIN Carrera c ON c.idCarrera = i.carrera_idCarrera " +
            "WHERE i.fechaEgreso IS NOT NULL " +
            "GROUP BY c.nombreCarrera, anio " +
            ") AS fj " + // <-- ¡Paréntesis de cierre CRÍTICO!
            "GROUP BY fj.nombreCarrera, fj.anio " +
            "ORDER BY fj.nombreCarrera, fj.anio")
    List<DTOReporte> getReportesCarreras();

}