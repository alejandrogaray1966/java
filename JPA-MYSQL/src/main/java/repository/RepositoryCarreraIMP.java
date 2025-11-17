package repository;

import dto.DTOInscriptosCarrera;
import entities.Carrera;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class RepositoryCarreraIMP implements RepositoryCarrera{
	
    private EntityManagerFactory emf;
    private EntityManager em;
    private static RepositoryCarreraIMP instance;

    private RepositoryCarreraIMP(EntityManagerFactory emf){
        this.emf = emf;
    }

    public static RepositoryCarreraIMP getInstance(EntityManagerFactory emf){
        if(instance==null){
            return new RepositoryCarreraIMP(emf);
        }
        return instance;
    }

    @Override
    public void save(Carrera c) {
        em = emf.createEntityManager();
        if(!em.contains(c)){
            em.getTransaction().begin();
            em.persist(c);
            em.getTransaction().commit();
        }
        else{
            em.merge(c);
        }
        em.close();
        // Verificación posterior al guardado
        em = emf.createEntityManager();
//        Carrera savedCarrera = em.find(Carrera.class, c.getIdCarrera());
//        if (savedCarrera != null) {
//            System.out.println("La carrera se guardó correctamente: " + savedCarrera.getNombreCarrera());
//        } else {
//            System.out.println("Error al guardar la carrera.");
//        }
        em.close();
    }

    @Override
    public Carrera getById(int id) {
        em = emf.createEntityManager();
        Carrera c = em.find(Carrera.class, id);
        em.close();
        return c;
    }

    @Override
    public List<DTOInscriptosCarrera> getCarreraOrderByCantEstudiantes() {
        em = emf.createEntityManager();
        Query q = em.createQuery(
                "SELECT c.nombreCarrera, COUNT(a) " +
                        "FROM Carrera c " +
                        "LEFT JOIN c.inscripciones a " +
                        "GROUP BY c.nombreCarrera " +
                        "ORDER BY COUNT(a) DESC"
        );

        @SuppressWarnings("unchecked")
        List<Object[]> resultados = q.getResultList();
        List<DTOInscriptosCarrera> inscriptosPorCarrera = new ArrayList<>();

        for (Object[] resultado : resultados) {
            String nombreCarrera = (String) resultado[0];
            long cantidadEstudiantes = (Long) resultado[1]; 
            DTOInscriptosCarrera dto = new DTOInscriptosCarrera(nombreCarrera, cantidadEstudiantes);
            inscriptosPorCarrera.add(dto);
        }

        em.close();
        return inscriptosPorCarrera;
    }
}
