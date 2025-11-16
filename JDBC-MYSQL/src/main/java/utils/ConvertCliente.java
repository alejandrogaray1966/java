package utils;

import entities.Cliente;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Integer.parseInt;

public class ConvertCliente {

    public List<Cliente> convertCsv(String ruta) throws IOException {
        CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(ruta));
        ArrayList<Cliente> salida = new ArrayList<>();
        for(CSVRecord row: parser) {
            int idCliente = parseInt(row.get("idCliente"));
            String nombre = row.get("nombre");
            String email = row.get("email");
            Cliente nuevoCliente = new Cliente(idCliente, nombre, email);
            salida.add(nuevoCliente);
        }
        return salida;
    }
}
