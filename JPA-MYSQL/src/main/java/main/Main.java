package main;

import dto.DTOEstudiante;
import dto.DTOInscriptosCarrera;
import dto.DTOReporte;
import entities.Carrera;
import entities.Estudiante;
import entities.Inscripcion;
import factory.EntityManagerFactory;
import repository.RepositoryCarreraIMP;
import repository.RepositoryEstudianteIMP;
import repository.RepositoryInscripcionIMP;
import utils.CSVCarrera;
import utils.CSVEstudiante;
import utils.CSVInsripcion;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
	
    private static RepositoryCarreraIMP carreraRepo;
    private static RepositoryEstudianteIMP alumnoRepo;
    private static RepositoryInscripcionIMP inscripcionRepo;

    private static void csvUpload(RepositoryEstudianteIMP alumnoRepo, RepositoryCarreraIMP carreraRepo,
                                  RepositoryInscripcionIMP inscripcionRepo) throws IOException, ParseException {
        String filePath = new File("").getAbsolutePath();
        LinkedList<Estudiante> alumnos = new CSVEstudiante(filePath +
                "/src/main/resources/csv/estudiantes.csv").getAlumnos();
        for (Estudiante a : alumnos) {
            alumnoRepo.save(a);
        }

        LinkedList<Carrera> carreras = new CSVCarrera(filePath +
                "/src/main/resources/csv/carreras.csv").getCarreras();
        for (Carrera c : carreras) {
            carreraRepo.save(c);
        }

        LinkedList<Inscripcion> inscripciones = new CSVInsripcion(filePath +
                "/src/main/resources/csv/estudianteCarrera.csv").getInscripciones();
        for (Inscripcion i : inscripciones) {
            inscripcionRepo.save(i);
        }
    }

    public static void main(String[] args) throws ParseException, IOException, SQLException {
    	
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE); // Sólo se imprimen errores severos

        EntityManagerFactory mysqlFactory = EntityManagerFactory.getDAOFactory(EntityManagerFactory.MYSQL);

        carreraRepo = mysqlFactory.getCarreraRepository();
        alumnoRepo = mysqlFactory.getAlumnoRepository();
        inscripcionRepo = mysqlFactory.getInscripcionRepository();

        System.out.println("--- CARGA DE ARCHIVOS .CSV ---\n");
        System.out.println("Se cargan los datos de los archivos .csv a las tablas correspondientes...");
        csvUpload(alumnoRepo, carreraRepo, inscripcionRepo); // Se cargan los datos de los CSV a las tablas

        System.out.println("\n--- EJERCICIO 2 ---");

        // 2A) Dar de alta un estudiante.
        System.out.println("\n2A) Dar de alta un estudiante.");

        Estudiante a1 = new Estudiante(55555555,"Sergio", "Zurich",35,"Masculino","Tandil",123456);
        alumnoRepo.save(a1);
        System.out.println("Se dio de alta al estudiante: " + a1);

        // 2B) Matricular un estudiante en una carrera.
        System.out.println("\n2B) Matricular un estudiante en una carrera.");
        Carrera c1 = new Carrera("Ingenieria en la Naza",15);
        carreraRepo.save(c1);
        Inscripcion i1 = new Inscripcion(a1,c1, 2022,2025,3);
        inscripcionRepo.save(i1);
        System.out.println("Se matriculó al estudiante " + a1.getNombre() + " en la carrera " + c1.getNombreCarrera());

        // 2C) Recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple.
        System.out.println("\n2C) Recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple.");
        System.out.println("Listado de alumnos ordenado por apellido de forma ascendente: ASC");
        for (DTOEstudiante s : alumnoRepo.getAll()){
            System.out.println(s);
        }

        // 2D) Recuperar un estudiante, en base a su número de libreta universitaria.
        System.out.println("\n2D) Recuperar un estudiante, en base a su número de libreta universitaria.");
        System.out.println("Estudiante con numero de libreta = 123456:");
        System.out.println(alumnoRepo.getByNroLibreta(123456));

        // 2E) Recuperar todos los estudiantes, en base a su género.
        System.out.println("\n2E) Recuperar todos los estudiantes, en base a su género.");
        String genero = "Masculino";
        System.out.println("Estudiantes cuyo genero es '" + genero + "':");
        for (DTOEstudiante s : alumnoRepo.getByGenero(genero)){
            System.out.println(s);
        }

        // 2F) Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.
        System.out.println("\n2F) Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.");
        for (DTOInscriptosCarrera c : carreraRepo.getCarreraOrderByCantEstudiantes()){
            System.out.println(c);
        }

        // 2G) recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.
        System.out.println("\n2G) Estudiantes de la carrera de Ingenieria en la Naza, que viven en Tandil.");
        int idCarrera = 16;
        for (DTOEstudiante s : alumnoRepo.getByCarreraCiudad(idCarrera, "Tandil")){
            System.out.println(s);
        }

        // 3) Generar un reporte de las carreras, que para cada carrera incluya información de los inscriptos
        // y egresados por año. Se deben ordenar las carreras alfabeticamente, y presentar los años de manera
        // cronológica.
        System.out.println("\n--- EJERCICIO 3 ---");

        System.out.println("\n3) Reporte de las carreras ordenadas alfabeticamente, con cantidad de inscriptos y egresados por año.");
        for (DTOReporte i : inscripcionRepo.crearInforme()){
            System.out.println(i);
        }
        
        System.out.println("\n                                                   --- FIN ---");
    }

}
