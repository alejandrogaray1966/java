package dao.derby;

import dao.interfaces.DetalleDaoInterface;
import entities.Detalle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DERBYDetalleDAO implements DetalleDaoInterface<Detalle> {

    @Override
    public void createTable(Connection conn) throws Exception {
        System.out.println();
        System.out.println("	Creando la tabla (Detalle) ...");
        String sql = "CREATE TABLE detalle ( " +
                "idFactura INT NOT NULL, " +
                "idProducto INT NOT NULL, " +
                "cantidad INT, " +
                "PRIMARY KEY (idFactura, idProducto) )";
        try {
            conn.prepareStatement(sql).execute();
            conn.commit();
        } catch (SQLException e) {
            if (e.getSQLState().equals("X0Y32")) { // X0Y32 = "Table already exists..."
                System.out.println("	La tabla Detalle ya existía, se continúa...");
                conn.rollback();
            } else {
                throw e; // if he threw other errors
            }
        }
    }

    @Override
    public void loadCSVData(List<Detalle> data, Connection conn) throws Exception {
        System.out.println();
        System.out.println("	Cargando los datos (Detalle) ...");
        // 1) Empty the table before inserting
        String deleteSQL = "DELETE FROM detalle";
        try (PreparedStatement psDelete = conn.prepareStatement(deleteSQL)) {
            psDelete.executeUpdate();
            conn.commit();
        }
        // 2) Insert all records from the CSV
        String insertSQL = "INSERT INTO detalle (idFactura, idProducto, cantidad) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(insertSQL)) {
            for (Detalle detalle: data) {
                ps.setInt(1, detalle.getIdFactura());
                ps.setInt(2, detalle.getIdProducto());
                ps.setInt(3, detalle.getCantidad());
                ps.addBatch();
            }
            ps.executeBatch();
            conn.commit();
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
        String sql = "DROP TABLE detalle";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
            conn.commit();
        } catch (SQLException e) {
            if ("42Y55".equals(e.getSQLState())) { // Table does not exist
                System.out.println("La tabla Detalle no existe, se omite el DROP...");
            } else {
                throw e;
            }
        }
    }

}

