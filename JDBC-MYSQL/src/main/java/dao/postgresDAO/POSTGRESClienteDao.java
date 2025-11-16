package dao.postgresDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.ClienteDaoInterface;
import dto.ClienteDto;
import entities.Cliente;

public class POSTGRESClienteDao implements ClienteDaoInterface<Cliente> {

    @Override
    public void createTable(Connection conn) throws Exception {
        System.out.println();
        System.out.println("	Creando la tabla (Cliente) ...");
        String sql = "CREATE TABLE IF NOT EXISTS cliente ( " +
                "idCliente SERIAL PRIMARY KEY, " +
                "nombre VARCHAR(500) NOT NULL, " +
                "email VARCHAR(150) NOT NULL)";
        try {
            conn.prepareStatement(sql).execute();
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Error al intentar crear la tabla Cliente: " + e);
        }
    }

    @Override
    public void loadCSVData(List<Cliente> data, Connection conn) throws Exception {
        System.out.println();
        System.out.println("	Cargando los datos (Cliente) ...");
        // 1) Vaciar la tabla antes de insertar
        String deleteSQL = "DELETE FROM cliente";
        try (PreparedStatement psDelete = conn.prepareStatement(deleteSQL)) {
            psDelete.executeUpdate();
            conn.commit();
        }
        // 2) Insertar todos los registros desde la CSV
        String insertSQL = "INSERT INTO cliente (nombre, email) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(insertSQL)) {
            for (Cliente cliente : data) {
                ps.setString(1, cliente.getNombre());
                ps.setString(2, cliente.getEmail());
                ps.addBatch();
            }
            ps.executeBatch();
            conn.commit();
        }
    }

    @Override
    public void listTable(Connection conn) throws Exception {
        System.out.println();
        System.out.println("	Listando los datos (Cliente) ...");
        System.out.println();
        String sql = "SELECT idCliente, nombre, email FROM cliente";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("ID \t| Nombre \t \t \t| Email");
            System.out.println("---------------------------------------------------------------------------------");
            while (rs.next()) {
                int idCliente = rs.getInt("idCliente");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                System.out.println(idCliente + " \t| " + nombre + " \t \t| " + email);
            }
        }
        conn.commit();
    }

    @Override
    public void dropTable(Connection conn) throws Exception {
        System.out.println();
        System.out.println("	Borrando la tabla (Cliente) ...");
        String sql = "DROP TABLE IF EXISTS cliente";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
            conn.commit();
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public List<ClienteDto> listarClientesMayorFacturacion(Connection conn) throws Exception {
        System.out.println();
        System.out.println("	Buscando...");
        System.out.println();
        List<ClienteDto> salida = new ArrayList<>();
        String sql =
                "SELECT c.idCliente, c.nombre, " +
                        "       COALESCE(SUM(d.cantidad * p.valor), 0) AS total_facturado " +
                        "FROM cliente c " +
                        "LEFT JOIN factura f ON c.idCliente = f.idCliente " +
                        "LEFT JOIN detalle d ON f.idFactura = d.idFactura " +
                        "LEFT JOIN producto p ON d.idProducto = p.idProducto " +
                        "GROUP BY c.idCliente, c.nombre " +
                        "ORDER BY total_facturado DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("idCliente");
                String nombre = rs.getString("nombre");
                Float total = rs.getFloat("total_facturado");
                ClienteDto nuevo = new ClienteDto(id, nombre, total);
                salida.add(nuevo);
            }
        }
        conn.commit();
        return salida;
    }
}
