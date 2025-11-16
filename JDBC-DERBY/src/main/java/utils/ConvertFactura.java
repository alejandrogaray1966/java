package utils;

import entities.Factura;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Integer.parseInt;

public class ConvertFactura {

    public List<Factura> convertCsv(String ruta) throws IOException {
        CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(ruta));
        ArrayList<Factura> salida = new ArrayList<>();
        for(CSVRecord row: parser) {
            int idFactura = parseInt(row.get("idFactura"));
            int idCliente = parseInt(row.get("idCliente"));
            Factura nuevaFactura = new Factura(idFactura, idCliente);
            salida.add(nuevaFactura);
        }
        return salida;
    }

}
