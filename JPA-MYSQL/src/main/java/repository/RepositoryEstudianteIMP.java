package repository;

import dto.DTOEstudiante;
import entities.Estudiante;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class RepositoryEstudianteIMP implements RepositoryEstudiante {
    private static RepositoryEstudianteIMP instance;
    private EntityManagerFactory emf;
    private EntityManager em;

    private RepositoryEstudianteIMP(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public static RepositoryEstudianteIMP getInstance(EntityManagerFactory emf){
        if(instance==null){
            return new RepositoryEstudianteIMP(emf);
        }
        return instance;
    }

    @Override
    public void save(Estudiante a) {
        em = emf.createEntityManager();
        if (!em.contains(a)) {
            em.getTransaction().begin();
            em.persist(a);
            em.getTransaction().commit();
        } else {
            em.merge(a);
        }
        em.close();

        // Verificación posterior al guardado
        em = emf.createEntityManager();
//        Estudiante savedAlumno = em.find(Estudiante.class, a.getIdEstudiante());  // Recupera el objeto por ID
//        if (savedAlumno != null) {
//            System.out.println("El alumno se guardó correctamente: " +
//                    "Nombre: "+savedAlumno.getNombre()+
//                    ", Apellido: "+savedAlumno.getApellido());
//        } else {
//            System.out.println("Error al guardar el alumno.");
//        }
        em.close();
    }

    @Override
    public List<DTOEstudiante> getAll() {
        em = emf.createEntityManager();
        Query query = em.createQuery("SELECT a FROM Estudiante a ORDER BY a.apellido");
        @SuppressWarnings("unchecked")
        List<Estudiante> alumnos = query.getResultList();
        List<DTOEstudiante> alumnosDTOList = this.crearDTOAlumnos(alumnos);
        em.close();
        return alumnosDTOList;
    }

    @Override
    public DTOEstudiante getById(int id) {
        em = emf.createEntityManager();
        Estudiante a = em.find(Estudiante.class, id);
        if (a == null) {
            System.out.println("No se encontró un alumno con el id: " + id);
            return null;
        }
        return this.crearDTOAlumno(a);
    }

    @Override
    public DTOEstudiante getByNroLibreta(int nroLibreta) {
        em = emf.createEntityManager();
        Estudiante alumno = this.em.createQuery("SELECT a FROM Estudiante a WHERE a.nroLibreta = :nroLibreta", Estudiante.class)
                .setParameter("nroLibreta", nroLibreta)
                .getSingleResult();
        em.close();
        return crearDTOAlumno(alumno);
    }

    @Override
    public List<DTOEstudiante> getByGenero(String genero) {
        em = emf.createEntityManager();
        Query q = em.createQuery("SELECT a FROM Estudiante a WHERE a.genero = :genero");
        q.setParameter("genero", genero);
        @SuppressWarnings("unchecked")
        List<Estudiante> alumnos = q.getResultList();
        List<DTOEstudiante> alumnosByGenero = this.crearDTOAlumnos(alumnos);
        em.close();
        return alumnosByGenero;
    }

    @Override
    public List<DTOEstudiante> getByCarreraCiudad(int idCarrera, String ciudad) {
        em = emf.createEntityManager();
        Query q = em.createQuery("SELECT a FROM Estudiante a, IN(a.inscripciones) i WHERE i.carrera.id = :idCarrera AND a.ciudad = :ciudadOrigen");
        q.setParameter("idCarrera", idCarrera);
        q.setParameter("ciudadOrigen", ciudad);
        @SuppressWarnings("unchecked")
        List<Estudiante> alumnos = q.getResultList();
        List<DTOEstudiante> alumnosByCarreraCiudad = this.crearDTOAlumnos(alumnos);
        em.close();
        return alumnosByCarreraCiudad;
    }

    private List<DTOEstudiante> crearDTOAlumnos (List<Estudiante> alumnoList) {
        List<DTOEstudiante> DTOAlumnoList = new ArrayList<>();
        for (Estudiante alumno : alumnoList) {
            DTOAlumnoList.add(crearDTOAlumno(alumno));
        }
        return DTOAlumnoList;
    }

    //String nombre, String apellido, int edad, String genero, int dni, String ciudad, int nroLibreta
    private DTOEstudiante crearDTOAlumno (Estudiante alumno) {
        return new DTOEstudiante(
                alumno.getNombre(),
                alumno.getApellido(),
                alumno.getEdad(),
                alumno.getGenero(),
                alumno.getDni(),
                alumno.getCiudad(),
                alumno.getNroLibreta());
    }
}
