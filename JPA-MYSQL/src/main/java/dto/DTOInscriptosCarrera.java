package dto;

public class DTOInscriptosCarrera {

    private String nombreCarrera;
    private long cantInscriptos;

    public DTOInscriptosCarrera(String nombreCarrera, long cantInscriptos) {
        this.nombreCarrera = nombreCarrera;
        this.cantInscriptos = cantInscriptos;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public long getcantInscriptos() {
        return cantInscriptos;
    }

    @Override
    public String toString() {
        return "Carrera: " + nombreCarrera
                + ", Cantidad Inscriptos: " + cantInscriptos;
    }

}
