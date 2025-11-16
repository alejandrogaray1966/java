package factory;

import java.sql.Connection;
import dao.interfaces.ClienteDaoInterface;
import dao.interfaces.DetalleDaoInterface;
import dao.interfaces.FacturaDaoInterface;
import dao.interfaces.ProductoDaoInterface;
import entities.Cliente;
import entities.Detalle;
import entities.Factura;
import entities.Producto;

/**
 * Crea las conexiones con las db's.
 * @author MatiasBava
 * @version 1.0
 * @since 2025
 */
public abstract class BaseDeDatosFactory {

    /**
     * 1 Factory MYSQL
     * @see DBMySql
     * */
    public static final int MYSQL_JDBC = 1;

    /**
     * 2 Factory Derby
     * @see DERBYFactory
     **/
    public static final int DERBY_JDBC = 2;

    /**
     * 3 Factory Postgres
     * @see DBPostgreSQLFactory
     * */
    public static final int POSTGRES_JDBC = 3;

    /**
     * Crea un manejador de db's.
     * @param factory
     * @return {@code factory == 1} MYSQL; {@code factory == 2} DERBY; {@code factory == 3} POSTGRES;
     */
    public static BaseDeDatosFactory getFactory(int factory){
        BaseDeDatosFactory instance = null;
        switch (factory){
            case 1:
                instance = DBMySql.getInstance();
                break;
            case 2:
                instance = DERBYFactory.getInstance();
                break;
            case 3:
                //instance = DBPostgreSQLFactory.getInstance();
                break;
            default:
                System.out.println("Numero no valido.");
                instance = DBMySql.getInstance();
                break;
        }
        return instance;
    }

    /**
     * Establece la conexion con la db.
     * */
    public abstract Connection connect() throws Exception;

    /**
     * Cierra la conexion con la db.
     * @throws Exception
     */
    public abstract void disconnect() throws Exception;

    /**
     * Retorna un objeto que se encarga de manejar la tabla Cliente.
     * @return Interfaz ClienteDao
     * */
    public abstract ClienteDaoInterface<Cliente> getClienteDao();

    /**
     * Retorna un objeto que se encarga de manejar la tabla Factura.
     * @return Interfaz FacturaDao
     * */
    public abstract FacturaDaoInterface<Factura> getFacturaDao();

    /**
     * Retorna un objeto que se encarga de manejar la tabla Detalle.
     * @return Interfaz DetalleDao
     * */
    public abstract DetalleDaoInterface<Detalle> getDetalleDao();

    /**
     * Retorna un objeto que se encarga de manejar la tabla Producto.
     * @return Interfaz ProductoDao
     * */
    public abstract ProductoDaoInterface<Producto> getProductoDao();

}
