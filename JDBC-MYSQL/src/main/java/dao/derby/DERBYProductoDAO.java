package dao.derby;

import dao.interfaces.ProductoDaoInterface;
import dto.ProductoDto;
import entities.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DERBYProductoDAO implements ProductoDaoInterface<Producto> {

    @Override
    public void createTable(Connection conn) throws Exception {
        System.out.println();
        System.out.println("	Creando la tabla (Producto) ...");
        String sql = "CREATE TABLE producto ( " +
                "idProducto INT PRIMARY KEY, " +
                "nombre VARCHAR(45) NOT NULL, " +
                "valor FLOAT NOT NULL) ";
        try {
            conn.prepareStatement(sql).execute();
            conn.commit();
        } catch (SQLException e) {
            if (e.getSQLState().equals("X0Y32")) { // X0Y32 = "Table already exists..."
                System.out.println("	La tabla Producto ya existía, se continúa...");
                conn.rollback();
            } else {
                throw e; // if he threw other errors
            }
        }
    }

    @Override
    public void loadCSVData(List<Producto> data, Connection conn) throws Exception {
        System.out.println();
        System.out.println("	Cargando los datos (Producto) ...");
        System.out.println();
        // 1) Empty the table before inserting
        String deleteSQL = "DELETE FROM producto";
        try (PreparedStatement psDelete = conn.prepareStatement(deleteSQL)) {
            psDelete.executeUpdate();
            conn.commit();
        }
        // 2) Insert all records from the CSV
        String insertSQL = "INSERT INTO producto (idProducto, nombre, valor) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(insertSQL)) {
            for (Producto producto: data) {
                ps.setInt(1, producto.getIdProducto());
                ps.setString(2, producto.getNombre());
                ps.setFloat(3, producto.getValor());
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
        System.out.println();
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
        String sql = "DROP TABLE producto";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
            conn.commit();
        } catch (SQLException e) {
            if ("42Y55".equals(e.getSQLState())) { // Table does not exist
                System.out.println("La tabla Producto no existe, se omite el DROP...");
            } else {
                throw e;
            }
        }
    }

    @Override
    public List<ProductoDto> listarProductosMayorRecaudacion(Connection conn) throws Exception{
        System.out.println();
        System.out.println("	Buscando...");
        System.out.println();
        List<ProductoDto> salida = new ArrayList<>();
        String sql =
                "SELECT p.idProducto, p.nombre, " +
                "       COALESCE(SUM(d.cantidad * p.valor), 0) AS total_recaudado " +
                "FROM producto p " +
                "LEFT JOIN detalle d ON p.idProducto = d.idProducto " +
                "GROUP BY p.idProducto, p.nombre " +
                "ORDER BY total_recaudado DESC " +
                "FETCH FIRST 1 ROWS ONLY";   // Derby (si fuera MySQL -> LIMIT 5)
        try (PreparedStatement ps = conn.prepareStatement(sql);
        	ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("idProducto");
                    String nombre = rs.getString("nombre");
                    Float total = rs.getFloat("total_recaudado");
                    ProductoDto nuevo = new ProductoDto(id,nombre,total);
                    salida.add(nuevo);
                }
        }
        conn.commit();
        return salida;
    }

}
