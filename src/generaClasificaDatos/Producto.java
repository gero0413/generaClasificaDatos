package generaClasificaDatos;

public class Producto {
    private String idProducto;
    private String nombre;
    private double precioPorUnidad;
    private int cantidadVendida;

    public Producto(String idProducto, String nombre, double precioPorUnidad) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precioPorUnidad = precioPorUnidad;
        this.cantidadVendida = 0;
    }

    public String getIdProducto() { return idProducto; }
    public String getNombre() { return nombre; }
    public double getPrecioPorUnidad() { return precioPorUnidad; }
    public int getCantidadVendida() { return cantidadVendida; }

    public void incrementarCantidadVendida(int cantidad) {
        this.cantidadVendida += cantidad;
    }
}
