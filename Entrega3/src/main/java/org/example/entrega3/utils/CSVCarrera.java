package org.example.entrega3.utils;

import org.example.entrega3.entities.Carrera;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.LinkedList;

public class CSVCarrera extends CSVReader{
    public CSVCarrera(String path) {
        super(path);
    }

    public LinkedList<Carrera> getCarreras() throws IOException {
        Iterable<CSVRecord> records =  this.read();
        LinkedList<Carrera> carreras = new LinkedList<>();
        for (CSVRecord record : records) {
            String nombreCarrera = record.get(1);
            Integer duracionCarrera = Integer.parseInt(record.get(2));
            carreras.add(new Carrera( nombreCarrera, duracionCarrera));
        }
        return carreras;
    }
}


