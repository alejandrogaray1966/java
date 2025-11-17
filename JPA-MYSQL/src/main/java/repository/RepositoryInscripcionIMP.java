package repository;

import dto.DTOReporte;
import entities.Inscripcion;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class RepositoryInscripcionIMP implements RepositoryInscripcion {
    private EntityManagerFactory emf;
    private EntityManager em;
    private static RepositoryInscripcionIMP instance;

    private RepositoryInscripcionIMP(EntityManagerFactory emf){
        this.emf = emf;
    }

    public static RepositoryInscripcionIMP getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            instance = new RepositoryInscripcionIMP(emf);
        }
        return instance;
    }

    @Override
    public void save(Inscripcion i) {
        em = emf.createEntityManager();
        if (!em.contains(i)) {
            em.getTransaction().begin();
            em.persist(i);
            em.getTransaction().commit();
        } else {
            em.merge(i);
        }
        em.close();

        // Verificación posterior al guardado
        em = emf.createEntityManager();
//        Inscripcion savedInscripcion = em.find(Inscripcion.class, i.getIdInscripcion());
//        if (savedInscripcion != null) {
//            System.out.println("La inscripción se guardó correctamente con ID: " + savedInscripcion.getIdInscripcion());
//        } else {
//            System.out.println("Error al guardar la inscripción.");
//        }
        em.close();
    }

    @Override
    public List<DTOReporte> crearInforme() {
        em = emf.createEntityManager();

        String query =
                "SELECT fj.nombreCarrera, fj.anio, SUM(fj.inscriptos) AS totalInscriptos, SUM(fj.graduados) AS totalGraduados " +
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
                "ORDER BY fj.nombreCarrera, fj.anio";

        @SuppressWarnings("unchecked")
        List<Object[]> queryList = em.createNativeQuery(query).getResultList();
        List<DTOReporte> report = new ArrayList<>();

        for (Object[] queryListRow : queryList) {
            String nombreCarrera = (String) queryListRow[0];
            Integer anio = ((Number) queryListRow[1]).intValue();
            Long inscriptos = ((Number) queryListRow[2]).longValue();
            Long graduados = ((Number) queryListRow[3]).longValue(); 
            DTOReporte r = new DTOReporte( nombreCarrera, anio, inscriptos, graduados);
            report.add(r);
        }

        em.close();
        return report;
    }
}
