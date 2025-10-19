package org.example.entrega3.utils;

import org.example.entrega3.repository.RepositoryCarrera;
import org.example.entrega3.repository.RepositoryEstudiante;
import org.example.entrega3.repository.RepositoryInscripcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;

@Component
public class CargarDatos implements CommandLineRunner {

    private final String rutaCsv="src/main/resources/csv/";

    @Autowired
    private RepositoryEstudiante estudianteRepositorio;

    @Autowired
    private RepositoryCarrera carreraRepositorio;

    @Autowired
    private RepositoryInscripcion estudianteCarreraRepositorio;

    // Metodo que se ejecuta al iniciar la aplicacion ya que extiende de CommandLineRunner
    @Override
    public void run(String... args) throws Exception {
        cargarDatos();
    }

    public void cargarDatos() throws IOException, ParseException {
        CSVEstudiante csvEstudiante = new CSVEstudiante(rutaCsv+"estudiantes.csv");
        estudianteRepositorio.saveAll(csvEstudiante.getAlumnos());

        CSVCarrera csvCarrera = new CSVCarrera(rutaCsv+"carreras.csv");
        carreraRepositorio.saveAll(csvCarrera.getCarreras());

        CSVInsripcion csvInscripcion = new CSVInsripcion(rutaCsv+"estudianteCarrera.csv");
        estudianteCarreraRepositorio.saveAll(csvInscripcion.getInscripciones());
    }

}


