package dto;

public class ClienteDto {
    private int idCliente;
    private String nombre;
    private Float total;

    public ClienteDto(int idCliente, String nombre, Float total) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.total = total;
    }

    public int getIdCliente() {
        return this.idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getTotal() {
        return this.total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "idCliente=" + this.idCliente + " \t \t nombre=" + this.nombre + " \t \t total=" + this.total + " \n";
    }
}
