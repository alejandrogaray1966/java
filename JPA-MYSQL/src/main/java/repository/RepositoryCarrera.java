package repository;

import dto.DTOInscriptosCarrera;
import entities.Carrera;
import java.util.List;

public interface RepositoryCarrera {
    public void save(Carrera c);
    public Carrera getById(int id);
    public List<DTOInscriptosCarrera> getCarreraOrderByCantEstudiantes();
}
