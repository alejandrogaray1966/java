package repository;

import dto.DTOReporte;
import entities.Inscripcion;
import java.util.List;

public interface RepositoryInscripcion {
    void save(Inscripcion i);
    List<DTOReporte> crearInforme();
}
