package dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dao.interfaces.FacturaDaoInterface;
import entities.Factura;

public class FacturaDao implements FacturaDaoInterface<Factura>{

    @Override
    public void createTable(Connection conn) throws Exception {
        System.out.println();
        System.out.println("	Creando la tabla (Factura) ...");
        String sql = "CREATE TABLE IF NOT EXISTS factura ( " +
                "idFactura INT PRIMARY KEY, " +
                "idCliente INT NOT NULL ) ";
        try {
            conn.prepareStatement(sql).execute();
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Error al intentar crear la tabla Producto" + e);
        }
    }

    @Override
    public void loadCSVData(List<Factura> data, Connection conn) throws Exception {
        System.out.println();
        System.out.println("	Cargando los datos (Factura) ...");
        // 1) Empty the table before inserting
        String deleteSQL = "DELETE FROM factura";
        try (PreparedStatement psDelete = conn.prepareStatement(deleteSQL)) {
            psDelete.executeUpdate();
            conn.commit();
        }
        // 2) Insert all records from the CSV
        String insertSQL = "INSERT INTO factura (idFactura, idCliente) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(insertSQL)) {
            for (Factura factura: data) {
                ps.setInt(1, factura.getIdFactura());
                ps.setInt(2, factura.getIdCliente());
                ps.addBatch();
            }
            ps.executeBatch();
            conn.commit();
        }
    }

    @Override
    public void listTable(Connection conn) throws Exception {
        System.out.println();
        System.out.println("	Listando los datos (Factura) ...");
        String sql = "SELECT idFactura, idCliente FROM factura";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("idFactura \t | idCliente");
            System.out.println("--------------------------------------");
            while (rs.next()) {
                int idFactura = rs.getInt("idFactura");
                int idCliente = rs.getInt("idCliente");
                System.out.println(idFactura + " \t \t | " + idCliente);
            }
        }
        conn.commit();
    }

    @Override
    public void dropTable(Connection conn) throws Exception {
        System.out.println();
        System.out.println("	Borrando la tabla (Factura) ...");
        String sql = "DROP TABLE factura";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
            conn.commit();
        } catch (SQLException e) {
            if ("42Y55".equals(e.getSQLState())) { // Table does not exist
                System.out.println("La tabla Factura no existe, se omite el DROP...");
            } else {
                throw e;
            }
        }
    }
	
	
}
