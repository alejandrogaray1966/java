package main;

import java.util.List;
import dao.interfaces.ClienteDaoInterface;
import dao.interfaces.DetalleDaoInterface;
import dao.interfaces.FacturaDaoInterface;
import dao.interfaces.ProductoDaoInterface;
import dto.ClienteDto;
import dto.ProductoDto;
import entities.Cliente;
import entities.Detalle;
import entities.Factura;
import entities.Producto;
import factory.BaseDeDatosFactory;
import utils.ConvertCliente;
import utils.ConvertDetalle;
import utils.ConvertFactura;
import utils.ConvertProducto;

public class Main {

    //Types of databases:  MYSQL_JDBC  DERBY_JDBC  POSTGRES_JDBC
    //private static BaseDeDatosFactory factory = BaseDeDatosFactory.getFactory(BaseDeDatosFactory.MYSQL_JDBC);
    private static BaseDeDatosFactory factory = BaseDeDatosFactory.getFactory(BaseDeDatosFactory.DERBY_JDBC);
    //private static BaseDeDatosFactory factory = BaseDeDatosFactory.getFactory(BaseDeDatosFactory.POSTGRES_JDBC);

    // DAO for table
    private static ClienteDaoInterface<Cliente> clienteDAO;
    private static FacturaDaoInterface<Factura> facturaDAO;
    private static DetalleDaoInterface<Detalle> detalleDAO;
    private static ProductoDaoInterface<Producto> productoDAO;

    // Archives Excel
    private static final String CLIENTESCSV = "src/main/resources/csv/clientes.csv";
    private static final String FACTURASCSV = "src/main/resources/csv/facturas.csv";
    private static final String DETALLESCSV = "src/main/resources/csv/facturas-productos.csv";
    private static final String PRODUCTOSCSV = "src/main/resources/csv/productos.csv";

    public static void main(String[] args) throws Exception {

        System.out.println();
        System.out.println("========================= Trabajo Práctico Integrador N°: 1 ======================");

        System.out.println();
        System.out.println("- 1 - Creates the database and its driver...");
        instanciateDAOs();

        System.out.println();
        System.out.println("- 2 - Create the database schema...");
        createTables();

        System.out.println();
        System.out.println("- 3 - Loading data from CSV files...");
        fillTables();

        System.out.println();
        System.out.println("- 4 - List all data in the tables...");
        listAllTables();

        System.out.println();
        System.out.println("- 5 - List the highest-billing clients...");
        listarRankingCliente(clienteDAO.listarClientesMayorFacturacion(factory.connect()));

        System.out.println();
        System.out.println("- 6 - List the highest-grossing products...");
        listarRankingProducto( productoDAO.listarProductosMayorRecaudacion(factory.connect()) );

        System.out.println();
        System.out.println("- 7 - Delete tables...");
        dropTables();

        System.out.println();
        System.out.println("- 8 - Close the connection to the database...");
        factory.disconnect();

        System.out.println();
        System.out.println("============================================================================== FIN");

    }

    public static void instanciateDAOs() throws Exception {
    	
        clienteDAO = factory.getClienteDao();
        facturaDAO = factory.getFacturaDao();
        detalleDAO = factory.getDetalleDao();
        productoDAO = factory.getProductoDao();
        
    }

    public static void createTables() throws Exception {
    	
        clienteDAO.createTable(factory.connect());
        facturaDAO.createTable(factory.connect());
        detalleDAO.createTable(factory.connect());
        productoDAO.createTable(factory.connect());
        
    }

    public static void fillTables() throws Exception {
    	
        ConvertCliente nuevoC = new ConvertCliente();
        List<Cliente> dataC = nuevoC.convertCsv(CLIENTESCSV);
        clienteDAO.loadCSVData(dataC , factory.connect());
       
        ConvertFactura nuevaF = new ConvertFactura();
        List<Factura> dataF = nuevaF.convertCsv(FACTURASCSV);
        facturaDAO.loadCSVData(dataF ,  factory.connect());

        ConvertDetalle nuevoD = new ConvertDetalle();
        List<Detalle> dataD = nuevoD.convertCsv(DETALLESCSV);
        detalleDAO.loadCSVData(dataD , factory.connect());;

        ConvertProducto nuevoP = new ConvertProducto();
        List<Producto> dataP = nuevoP.convertCsv(PRODUCTOSCSV);
        productoDAO.loadCSVData(dataP , factory.connect());

    }

    public static void listAllTables() throws Exception {
    	
        //clienteDAO.listTable(factory.connect());      	// <------ ya probado funciona bien
    	//facturaDAO.listTable(factory.connect());			// <------ ya probado funciona bien
    	//detalleDAO.listTable(factory.connect());			// <------ ya probado funciona bien
    	//productoDAO.listTable(factory.connect());		    // <------ ya probado funciona bien
    	
    }

    public static void dropTables() throws Exception {
    	
        clienteDAO.dropTable(factory.connect());
    	facturaDAO.dropTable(factory.connect());
    	detalleDAO.dropTable(factory.connect());
    	productoDAO.dropTable(factory.connect());
    	
    }

    public static  void listarRankingCliente(List<ClienteDto> lista){
        System.out.printf("%-5s | %-30s | %-15s%n", "ID", "Nombre", "Total Facturado");
        System.out.println("-----------------------------------------------------------");
        for(ClienteDto cliente : lista){
            System.out.printf("%-5d | %-30s | $%.2f%n",
                    cliente.getIdCliente(), cliente.getNombre(), cliente.getTotal());
        }
    }

    public static  void listarRankingProducto(List<ProductoDto> lista){
        System.out.printf("%-5s | %-30s | %-15s%n", "ID", "Producto", "Total Recaudado");
        System.out.println("-----------------------------------------------------------");
        for(ProductoDto producto : lista){
            System.out.printf("%-5d | %-30s | $%.2f%n",
                    producto.getIdProducto(), producto.getNombre() , producto.getTotal());
        }
    }



}

