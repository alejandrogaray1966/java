package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import dao.derby.DERBYClienteDAO;
import dao.derby.DERBYFacturaDAO;
import dao.derby.DERBYDetalleDAO;
import dao.derby.DERBYProductoDAO;
import dao.interfaces.ClienteDaoInterface;
import dao.interfaces.DetalleDaoInterface;
import dao.interfaces.FacturaDaoInterface;
import dao.interfaces.ProductoDaoInterface;
import entities.Cliente;
import entities.Detalle;
import entities.Factura;
import entities.Producto;

/**
 * @author AlejandroGaray1966
 * @version 1.0
 * @since 2025
 * @see factory.BaseDeDatosFactory factory creador
 */

public class DERBYFactory extends BaseDeDatosFactory {

    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String URL = "jdbc:derby:integrador1;create=true;territory=en_US";
    private static DERBYFactory instance = null;
    private static Connection connection;

    private DERBYFactory() {}

    public static synchronized DERBYFactory getInstance() {
        if (instance == null) {
            instance = new DERBYFactory();
        }
        return instance;
    }

    public Connection connect() throws Exception {
        if (connection == null) {
            Class.forName(DRIVER).getDeclaredConstructor().newInstance();
            connection = DriverManager.getConnection(URL);
            connection.setAutoCommit(false);
        }
        return connection;
    }

    public void disconnect() throws Exception {
        if (connection != null) {
            connection.rollback();
            connection.close();
            connection = null;
        }
    }


    @Override
    public ClienteDaoInterface<Cliente> getClienteDao() {
        return new DERBYClienteDAO();
    }

    @Override
    public FacturaDaoInterface<Factura> getFacturaDao() {
        return new DERBYFacturaDAO();
    }
    
    @Override
    public DetalleDaoInterface<Detalle> getDetalleDao() {
        return new DERBYDetalleDAO();
    }
    
    @Override
    public ProductoDaoInterface<Producto> getProductoDao() {
        return new DERBYProductoDAO();
    }

}
