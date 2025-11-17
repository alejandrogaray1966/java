package factory;

import repository.RepositoryCarreraIMP;
import repository.RepositoryEstudianteIMP;
import repository.RepositoryInscripcionIMP;
import java.sql.SQLException;

public abstract class EntityManagerFactory {
	
    public static final int MYSQL = 1;

    public abstract RepositoryCarreraIMP getCarreraRepository();
    public abstract RepositoryEstudianteIMP getAlumnoRepository();
    public abstract RepositoryInscripcionIMP getInscripcionRepository();
    public abstract void closeEntityManagerFactory();

    public static EntityManagerFactory getDAOFactory(int persistence) throws SQLException {
        switch (persistence) {
            case MYSQL: return MySqlFactory.getInstance();
            default: return null;
        }
    }
}
