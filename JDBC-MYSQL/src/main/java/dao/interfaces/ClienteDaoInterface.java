package dao.interfaces;

import dto.ClienteDto;

import java.sql.Connection;
import java.util.List;

public interface ClienteDaoInterface<Cliente> {

    // table management

    void createTable(Connection conn) throws Exception;

    void loadCSVData(List<Cliente> data, Connection conn) throws Exception;

    void listTable(Connection conn) throws Exception;

    void dropTable(Connection conn) throws Exception;
    
    List<ClienteDto> listarClientesMayorFacturacion(Connection conn) throws Exception;

}
