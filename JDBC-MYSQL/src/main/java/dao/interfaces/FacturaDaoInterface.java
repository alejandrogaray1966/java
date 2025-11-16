package dao.interfaces;

import java.sql.Connection;
import java.util.List;

public interface FacturaDaoInterface<Factura> {

    // table management

    void createTable(Connection conn) throws Exception;

    void loadCSVData(List<Factura> data, Connection conn) throws Exception;

    void listTable(Connection conn) throws Exception;

    void dropTable(Connection conn) throws Exception;

}
