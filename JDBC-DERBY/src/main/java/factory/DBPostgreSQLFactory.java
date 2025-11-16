package factory;

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

import java.sql.*;

public class DBPostgreSQLFactory extends BaseDeDatosFactory {

    private String uri = "jdbc:postgresql://localhost:5433/integrador1";
    private static Connection conn;
    private static DBPostgreSQLFactory instance = null;
    
    private String baseDb = "postgres";
    private String targetDb = "integrador1";
    private String host = "localhost";
    private String port = "5433";
    private String user = "admin";
    private String pass = "admin";


    private DBPostgreSQLFactory() {}
    

    public static synchronized DBPostgreSQLFactory getInstance() {
        if (instance == null) {
            instance = new DBPostgreSQLFactory();
        }
        return instance;
    }

    //Joya son unos capos!! gracias sisis de 10000!
    public Connection connect() {
        if (conn == null) {
            try {
                String urlBase = String.format("jdbc:postgresql://%s:%s/%s", host, port, baseDb);
                try (Connection initConn = DriverManager.getConnection(urlBase, user, pass)) {
                    String checkDb = "SELECT 1 FROM pg_database WHERE datname = ?";
                    try (PreparedStatement ps = initConn.prepareStatement(checkDb)) {
                        ps.setString(1, targetDb);
                        try (ResultSet rs = ps.executeQuery()) {
                            if (!rs.next()) {
                                System.out.println("⚡ Creando base de datos " + targetDb + "...");
                                try (Statement st = initConn.createStatement()) {
                                    st.executeUpdate("CREATE DATABASE \"" + targetDb + "\"");
                                }
                            }
                        }
                    }
                }
                String urlTarget = String.format(
                        "jdbc:postgresql://%s:%s/%s?currentSchema=public",
                        host, port, targetDb
                );
                conn = DriverManager.getConnection(urlTarget, user, pass);
                conn.setAutoCommit(false);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return conn;
    }

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

    @Override
    public ClienteDaoInterface<Cliente> getClienteDao() {
        return new ClienteDao();
    }

    @Override
    public FacturaDaoInterface<Factura> getFacturaDao() {
        return new FacturaDao();
    }
    
    @Override
    public DetalleDaoInterface<Detalle> getDetalleDao() { return new DetalleDao(); }
    
    @Override
    public ProductoDaoInterface<Producto> getProductoDao() {
        return new ProductoDao();
    }
    
}

