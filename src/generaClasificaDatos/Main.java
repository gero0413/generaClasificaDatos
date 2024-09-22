package generaClasificaDatos;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        try {
            // Leer la información de los vendedores
            List<Vendedor> vendedores = leerVendedores("vendedores.txt");

            // Leer la información de los productos
            Map<String, Producto> productos = leerProductos("productos.txt");

            // Procesar las ventas de todos los archivos de ventas en una carpeta
            File carpetaVentas = new File("carpetaVentas");
            File[] archivosVentas = carpetaVentas.listFiles((dir, name) -> name.endsWith(".txt"));

            if (archivosVentas != null) {
                for (File archivo : archivosVentas) {
                    procesarVentas(archivo, vendedores, productos);
                }
            }

            // Generar el archivo con los vendedores ordenados por dinero recaudado
            generarReporteVendedores(vendedores, "reporteVendedores.csv");

            // Generar el archivo con los productos vendidos por cantidad
            generarReporteProductos(productos, "reporteProductos.csv");

            System.out.println("Proceso completado con éxito.");

        } catch (Exception e) {
            System.err.println("Ocurrió un error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para leer los datos de vendedores
    public static List<Vendedor> leerVendedores(String archivoVendedores) throws IOException {
        List<Vendedor> vendedores = new ArrayList<>();
        List<String> lineas = Files.readAllLines(Paths.get(archivoVendedores));

        for (String linea : lineas) {
            String[] datos = linea.split(";");
            String tipoDocumento = datos[0];
            String numeroDocumento = datos[1];
            String nombre = datos[2];
            String apellido = datos[3];
            vendedores.add(new Vendedor(tipoDocumento, numeroDocumento, nombre, apellido));
        }

        return vendedores;
    }

    // Método para leer los datos de productos
    public static Map<String, Producto> leerProductos(String archivoProductos) throws IOException {
        Map<String, Producto> productos = new HashMap<>();
        List<String> lineas = Files.readAllLines(Paths.get(archivoProductos));

        for (String linea : lineas) {
            String[] datos = linea.split(";");
            String idProducto = datos[0];
            String nombreProducto = datos[1];
            double precioPorUnidad = Double.parseDouble(datos[2]);
            productos.put(idProducto, new Producto(idProducto, nombreProducto, precioPorUnidad));
        }

        return productos;
    }

    // Método para procesar los archivos de ventas
    public static void procesarVentas(File archivoVentas, List<Vendedor> vendedores, Map<String, Producto> productos) throws IOException {
        List<String> lineas = Files.readAllLines(archivoVentas.toPath());
        String[] datosVendedor = lineas.get(0).split(";");
        String tipoDocumento = datosVendedor[0];
        String numeroDocumento = datosVendedor[1];

        // Buscar al vendedor
        Vendedor vendedor = vendedores.stream()
                .filter(v -> v.getTipoDocumento().equals(tipoDocumento) && v.getNumeroDocumento().equals(numeroDocumento))
                .findFirst()
                .orElse(null);

        if (vendedor == null) {
            System.err.println("No se encontró al vendedor con documento: " + numeroDocumento);
            return;
        }

        // Procesar las ventas del vendedor
        for (int i = 1; i < lineas.size(); i++) {
            String[] datosVenta = lineas.get(i).split(";");
            String idProducto = datosVenta[0];
            int cantidadVendida = Integer.parseInt(datosVenta[1]);

            Producto producto = productos.get(idProducto);

            if (producto != null) {
                // Actualizar la cantidad vendida del producto
                producto.incrementarCantidadVendida(cantidadVendida);
                // Actualizar el dinero recaudado por el vendedor
                vendedor.incrementarRecaudacion(cantidadVendida * producto.getPrecioPorUnidad());
            } else {
                System.err.println("Producto no encontrado con ID: " + idProducto);
            }
        }
    }

    // Método para generar el reporte de vendedores
    public static void generarReporteVendedores(List<Vendedor> vendedores, String nombreArchivo) throws IOException {
        Collections.sort(vendedores, Comparator.comparingDouble(Vendedor::getRecaudacion).reversed());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (Vendedor vendedor : vendedores) {
                writer.write(vendedor.getNombre() + " " + vendedor.getApellido() + ";" + vendedor.getRecaudacion());
                writer.newLine();
            }
        }
    }

    // Método para generar el reporte de productos
    public static void generarReporteProductos(Map<String, Producto> productos, String nombreArchivo) throws IOException {
        List<Producto> listaProductos = new ArrayList<>(productos.values());
        Collections.sort(listaProductos, Comparator.comparingInt(Producto::getCantidadVendida).reversed());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (Producto producto : listaProductos) {
                writer.write(producto.getNombre() + ";" + producto.getCantidadVendida());
                writer.newLine();
            }
        }
    }
}

