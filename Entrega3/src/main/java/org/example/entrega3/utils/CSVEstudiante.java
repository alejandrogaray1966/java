package org.example.entrega3.utils;

import org.example.entrega3.entities.Estudiante;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedList;

public class CSVEstudiante extends CSVReader{
    public CSVEstudiante(String path) {
        super(path);
    }

    public LinkedList<Estudiante> getAlumnos() throws IOException, ParseException {
        Iterable<CSVRecord> records =  this.read();
        LinkedList<Estudiante> alumnos = new LinkedList<>();
        for (CSVRecord record : records) {
            int dni = Integer.parseInt(record.get(0));
            String nombre = (record.get(1));
            String apellido = (record.get(2));
            int edad = Integer.parseInt(record.get(3));
            String genero = (record.get(4));
            String ciudad = (record.get(5));
            int nroLibreta = Integer.parseInt(record.get(6));

            alumnos.add(new Estudiante(dni,nombre, apellido, edad, genero, ciudad, nroLibreta));
        }
        return alumnos;
    }
}
