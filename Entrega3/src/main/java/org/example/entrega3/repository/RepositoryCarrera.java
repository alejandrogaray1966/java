package org.example.entrega3.repository;

import org.example.entrega3.dto.DTOInscriptosCarrera;
import org.example.entrega3.entities.Carrera;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("CarreraRepositorio")
public interface RepositoryCarrera extends JpaRepository<Carrera, Integer>{

    //f) recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.
    @Query("SELECT new org.example.entrega3.dto.DTOInscriptosCarrera(c.nombreCarrera, COUNT(a)) " +
            "FROM Carrera c " +
            "LEFT JOIN c.inscripciones a " +
            "GROUP BY c.nombreCarrera " +
            "ORDER BY COUNT(a) DESC")
    List<DTOInscriptosCarrera> findCarrerasConEstudiantes();
}






