package utils;

import entities.Producto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class ConvertProducto {

    public List<Producto> convertCsv(String ruta) throws IOException {
        CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(ruta));
        ArrayList<Producto> salida = new ArrayList<>();
        for(CSVRecord row: parser) {
            int idProducto = parseInt(row.get("idProducto"));
            String nombre = row.get("nombre");
            float valor = parseFloat(row.get("valor"));
            Producto nuevoProducto = new Producto(idProducto, nombre, valor);
            salida.add(nuevoProducto);
        }
        return salida;
    }

}
