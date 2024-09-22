package generaClasificaDatos;

public class Vendedor {
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombre;
    private String apellido;
    private double recaudacion;

    public Vendedor(String tipoDocumento, String numeroDocumento, String nombre, String apellido) {
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.recaudacion = 0;
    }

    public String getTipoDocumento() { return tipoDocumento; }
    public String getNumeroDocumento() { return numeroDocumento; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public double getRecaudacion() { return recaudacion; }

    public void incrementarRecaudacion(double monto) {
        this.recaudacion += monto;
    }
}
