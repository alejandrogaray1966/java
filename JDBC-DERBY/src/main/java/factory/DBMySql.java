package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import dao.interfaces.ClienteDaoInterface;
import dao.interfaces.DetalleDaoInterface;
import dao.interfaces.FacturaDaoInterface;
import dao.interfaces.ProductoDaoInterface;
import dao.mysql.ClienteDao;
import dao.mysql.DetalleDao;
import dao.mysql.FacturaDao;
import dao.mysql.ProductoDao;
import entities.Cliente;
import entities.Detalle;
import entities.Factura;
import entities.Producto;

/**
 * Maneja la conexion con la DB (MYSQL).
 * @author MatiasBavaCc
 * @version 1.0
 * @since 2025
 * @see factory.BaseDeDatosFactory factory creador.
 * */

public class DBMySql extends BaseDeDatosFactory {

    /** Repesenta la url, puerto y nombre de db. */
    private String uri = "jdbc:mysql://localhost:3306/integrador1";

    /** Se utiliza para abrir y cerrar la conexion */
    private static Connection conn;

    /** Variable Singleton, utilizada para tener una unica conexion. */
    private static DBMySql instance = null;

    private DBMySql() {}

    /**
     * Se encarga de tener una unica instancia de conexion.
     * Patron Singleton.
     * @return {@code true} Retorna una nueva conexion; {@code false} Retorna una conexion existente.
     */
    public static synchronized DBMySql getInstance() {
        if (instance == null) {
            instance = new DBMySql();
        }
        return instance;
    }

    /**
     * Mantiene una unica conexion.
     * @return {@code true} Retorna una nueva conexion
     * {@code false} Retorna una conexion existente.
     */
    @Override
    public Connection connect() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(uri, "root", "");
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return conn;
    }

    /**
     * Cierra la conexion.
     */
    @Override
    public void disconnect() {
        try {
            if (conn != null) {
                conn.rollback();
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error cerrando la conexión", e);
        }
    }

    /**
     * Maneja las operaciones de la table Cliente.
     * @return {@code ClienteDao} Maneja la tabla Cliente en la db.
     * @see ClienteDao
     */
    @Override
    public ClienteDaoInterface<Cliente> getClienteDao() { return new ClienteDao(); }

    /**
     * Maneja las operaciones de la table Factura.
     * @return {@code FacturaDao} Maneja la tabla Factura de la db.
     * @see FacturaDao
     */
    @Override
    public FacturaDaoInterface<Factura> getFacturaDao() { return new FacturaDao(); }

    /**
     * Maneja las operaciones de la table Detalle.
     * @return {@code DetalleDao} Maneja la tabla Detalle de la db.
     * @see DetalleDao
     */
    @Override
    public DetalleDaoInterface<Detalle> getDetalleDao() { return new DetalleDao(); }

    /**
     * Maneja las operaciones de la table Producto.
     * @return {@code ProductoDao} Meneja la tabla Producto de la db.
     * @see ProductoDao
     */
    @Override
    public ProductoDaoInterface<Producto> getProductoDao() { return new ProductoDao(); }
    
}

