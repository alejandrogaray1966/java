package dao.postgresDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dao.interfaces.FacturaDaoInterface;
import entities.Factura;

public class POSTGRESFacturaDAO implements FacturaDaoInterface<Factura>  {
    
    @Override
    public void createTable(Connection conn) throws Exception {
        System.out.println();
        System.out.println("	Creando la tabla (Detalle) ...");
        String sql = "CREATE TABLE IF NOT EXISTS detalle ( " +
                "idFactura INT NOT NULL, " +
                "idProducto INT NOT NULL, " +
                "cantidad INT, " +
                "PRIMARY KEY (idFactura, idProducto) )";
        try {
            conn.prepareStatement(sql).execute();
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Error al intentar crear la tabla Detalle: " + e);
        }
    }

    
    @Override
    public void listTable(Connection conn) throws Exception {
        System.out.println();
        System.out.println("	Listando los datos (Detalle) ...");
        String sql = "SELECT idFactura, idProducto, cantidad FROM detalle";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("idFactura \t | idProducto \t | cantidad");
            System.out.println("-----------------------------------------------");
            while (rs.next()) {
                int idFactura = rs.getInt("idFactura");
                int idProducto = rs.getInt("idProducto");
                int cantidad = rs.getInt("cantidad");
                System.out.println(idFactura + " \t \t | " + idProducto + " \t \t | " + cantidad);
            }
        }
        conn.commit();
    }

    @Override
    public void dropTable(Connection conn) throws Exception {
        System.out.println();
        System.out.println("	Borrando la tabla (Detalle) ...");
        String sql = "DROP TABLE IF EXISTS detalle";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
            conn.commit();
        } catch (SQLException e) {
            throw e;
        }
    }


    @Override
    public void loadCSVData(List<Factura> data, Connection conn) throws Exception {
        System.out.println();
        System.out.println("	Cargando los datos (Factura) ...");
        // 1) Vaciar la tabla antes de insertar
        String deleteSQL = "DELETE FROM factura";
        try (PreparedStatement psDelete = conn.prepareStatement(deleteSQL)) {
            psDelete.executeUpdate();
            conn.commit();
        }
        // 2) Insertar todos los registros desde la CSV
        String insertSQL = "INSERT INTO factura (idCliente) VALUES (?)";
        try (PreparedStatement ps = conn.prepareStatement(insertSQL)) {
            for (Factura factura : data) {
                ps.setInt(1, factura.getIdCliente());
                ps.addBatch();
            }
            ps.executeBatch();
            conn.commit();
        }
    }
}
