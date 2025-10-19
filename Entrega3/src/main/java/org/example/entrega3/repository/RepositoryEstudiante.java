package org.example.entrega3.repository;

//import org.example.entrega3.dto.DTOEstudiante;
import org.example.entrega3.entities.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("EstudianteRepositorio")
public interface RepositoryEstudiante extends JpaRepository<Estudiante, Integer>{

    @Query("SELECT e " +
            "FROM Estudiante e WHERE e.nroLibreta=:nroLibreta")
    Estudiante findEstudianteByNroLibreta(int nroLibreta);

    @Query("SELECT e " +
            "FROM Estudiante e WHERE LOWER(e.genero) = LOWER(:genero)")
    List<Estudiante> findEstudiantesByGenero(String genero);

    @Query("SELECT e " +
            "FROM Estudiante e JOIN e.inscripciones m JOIN m.carrera c " +
            "WHERE LOWER(c.nombreCarrera) = LOWER(:carrera) AND LOWER(e.ciudad) = LOWER(:ciudad)")
    List<Estudiante> findEstudiantesByCarreraAndCiudad(String carrera, String ciudad);

}
