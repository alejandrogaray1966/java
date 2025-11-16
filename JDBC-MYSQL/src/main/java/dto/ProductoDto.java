package dto;

public class ProductoDto {

    private int idProducto;
    private String nombre;
    private Float total;

    public ProductoDto(int idProducto, String nombre, Float total) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.total = total;
    }

    public int getIdProducto() {
        return this.idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
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
        return "Producto [idProducto=" + this.idProducto + ", nombre=" + this.nombre + ", valor=" + this.total + "] \n";
    }
}
