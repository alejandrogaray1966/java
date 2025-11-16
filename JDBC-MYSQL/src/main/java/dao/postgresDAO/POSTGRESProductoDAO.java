package dao.postgresDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.ProductoDaoInterface;
import dto.ProductoDto;
import entities.Producto;

public class POSTGRESProductoDAO implements ProductoDaoInterface<Producto>{


    @Override
    public void createTable(Connection conn) throws Exception {
        System.out.println();
        System.out.println("	Creando la tabla (Producto) ...");
        String sql = "CREATE TABLE IF NOT EXISTS producto ( " +
                "idProducto SERIAL PRIMARY KEY, " +
                "nombre VARCHAR(45) NOT NULL, " +
                "valor REAL NOT NULL)";
        try {
            conn.prepareStatement(sql).execute();
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Error al intentar crear la tabla Producto: " + e);
        }
    }

    @Override
    public void loadCSVData(List<Producto> data, Connection conn) throws Exception {
        System.out.println();
        System.out.println("	Cargando los datos (Producto) ...");
        // 1) Vaciar la tabla antes de insertar
        String deleteSQL = "DELETE FROM producto";
        try (PreparedStatement psDelete = conn.prepareStatement(deleteSQL)) {
            psDelete.executeUpdate();
            conn.commit();
        }
        // 2) Insertar todos los registros desde la CSV
        String insertSQL = "INSERT INTO producto (nombre, valor) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(insertSQL)) {
            for (Producto producto : data) {
                ps.setString(1, producto.getNombre());
                ps.setFloat(2, producto.getValor());
                ps.addBatch();
            }
            ps.executeBatch();
            conn.commit();
        }
    }

    @Override
    public void listTable(Connection conn) throws Exception {
        System.out.println();
        System.out.println("	Listando los datos (Producto) ...");
        String sql = "SELECT idProducto, nombre, valor FROM producto";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("ID \t | Nombre \t \t \t | Valor");
            System.out.println("---------------------------------------------------------------------------------");
            while (rs.next()) {
                int idProducto = rs.getInt("idProducto");
                String nombre = rs.getString("nombre");
                float valor = rs.getFloat("valor");
                System.out.println(idProducto + " \t | " + nombre + " \t \t \t | " + valor);
            }
        }
        conn.commit();
    }

    @Override
    public void dropTable(Connection conn) throws Exception {
        System.out.println();
        System.out.println("	Borrando la tabla (Producto) ...");
        String sql = "DROP TABLE IF EXISTS producto";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
            conn.commit();
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public List<ProductoDto> listarProductosMayorRecaudacion(Connection conn) throws Exception {
        System.out.println();
        System.out.println("	Buscando...");
        List<ProductoDto> salida = new ArrayList<>();
        String sql =
                "SELECT p.idProducto, p.nombre, " +
                        "       COALESCE(SUM(d.cantidad * p.valor), 0) AS total_recaudado " +
                        "FROM producto p " +
                        "LEFT JOIN detalle d ON p.idProducto = d.idProducto " +
                        "GROUP BY p.idProducto, p.nombre " +
                        "ORDER BY total_recaudado DESC " +
                        "LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("idProducto");
                String nombre = rs.getString("nombre");
                Float total = rs.getFloat("total_recaudado");
                ProductoDto nuevo = new ProductoDto(id, nombre, total);
                salida.add(nuevo);
            }
        }
        conn.commit();
        return salida;
    }
}
