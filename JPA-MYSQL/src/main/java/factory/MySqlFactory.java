package factory;

import javax.persistence.*;
import repository.RepositoryCarreraIMP;
import repository.RepositoryEstudianteIMP;
import repository.RepositoryInscripcionIMP;

public class MySqlFactory extends EntityManagerFactory {
	
    private static MySqlFactory instance;
    private javax.persistence.EntityManagerFactory emf;

    private MySqlFactory() {
        this.emf = Persistence.createEntityManagerFactory("Grupo-30");
    }

    public static MySqlFactory getInstance() {
        if(instance == null){
            instance = new MySqlFactory();
        }
        return instance;
    }

    @Override
    public RepositoryCarreraIMP getCarreraRepository() {
        return RepositoryCarreraIMP.getInstance(emf);
    }
    @Override
    public RepositoryEstudianteIMP getAlumnoRepository() {
        return RepositoryEstudianteIMP.getInstance(emf);
    }
    @Override
    public RepositoryInscripcionIMP getInscripcionRepository() {
        return RepositoryInscripcionIMP.getInstance(emf);
    }

    public void closeEntityManagerFactory() {
        emf.close();
    }
}
