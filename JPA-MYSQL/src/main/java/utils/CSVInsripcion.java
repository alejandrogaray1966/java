package utils;

import entities.Carrera;
import entities.Estudiante;
import entities.Inscripcion;
import org.apache.commons.csv.CSVRecord;
import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedList;

public class CSVInsripcion extends CSVReader{

    public CSVInsripcion(String path) {
        super(path);
    }

    public LinkedList<Inscripcion> getInscripciones() throws IOException, ParseException {
        Iterable<CSVRecord> records = this.read();
        LinkedList<Inscripcion> inscripciones = new LinkedList<>();
        for (CSVRecord record : records) {
            Integer idInscripcion = Integer.parseInt(record.get(0));
            Estudiante alumno = new Estudiante(Integer.parseInt(record.get(1)));
            Carrera carrera = new Carrera(Integer.parseInt(record.get(2)));
            Integer fechaInscripcion = Integer.parseInt(record.get(3));

            // A las fechas vacías se les asigna null
            Integer fechaEgreso = null;
            String fechaEgresoStr = record.get(4);
            if (!fechaEgresoStr.isEmpty()) {
                fechaEgreso = Integer.parseInt(record.get(4));
            }

            int antiguedad = Integer.parseInt(record.get(5));

            // Se crea un objeto Inscripcion por cada registro
            Inscripcion inscripcion = new Inscripcion(idInscripcion,
                    carrera,
                    alumno,
                    fechaInscripcion,
                    fechaEgreso,
                    antiguedad);
            inscripciones.add(inscripcion);
        }

        return inscripciones;
    }

}
