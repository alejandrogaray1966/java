package repository;

import dto.DTOEstudiante;
import entities.Estudiante;
import java.util.List;

public interface RepositoryEstudiante {
    public void save(Estudiante s);
    public List<DTOEstudiante> getAll();
    public DTOEstudiante getById(int id);
    public DTOEstudiante getByNroLibreta(int nlibreta);
    public List<DTOEstudiante> getByGenero(String genero);
    public List<DTOEstudiante> getByCarreraCiudad(int idCarrera, String ciudad);
}
