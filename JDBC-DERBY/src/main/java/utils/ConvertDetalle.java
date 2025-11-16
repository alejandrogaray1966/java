package utils;

import entities.Detalle;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Integer.parseInt;

public class ConvertDetalle {

    public List<Detalle> convertCsv(String ruta) throws IOException {
        CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(ruta));
        ArrayList<Detalle> salida = new ArrayList<>();
        for(CSVRecord row: parser) {
            int idFactura = parseInt(row.get("idFactura"));
            int idProducto = parseInt(row.get("idProducto"));
            int cantidad = parseInt(row.get("cantidad"));
            Detalle nuevoDetalle = new Detalle(idFactura, idProducto, cantidad);
            salida.add(nuevoDetalle);
        }
        return salida;
    }

}
