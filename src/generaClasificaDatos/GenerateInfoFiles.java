package generaClasificaDatos;

import java.io.*;
import java.util.*;
import java.util.HashMap;
import java.util.Map;

public class GenerateInfoFiles {

    // Lista de nombres y apellidos para generar nombres de vendedores aleatorios
    private static final String[] nombres = {"Juan", "María", "Pedro", "Lucía", "Carlos", "Ana"};
    private static final String[] apellidos = {"Pérez", "Gómez", "Rodríguez", "López", "Martínez", "García"}; 
    
    // Método para generar un archivo de ventas de un vendedor
    public static void createSalesMenFile(int randomSalesCount, String name, long id) {
        String fileName = "ventas_" + id + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Files/"+fileName))) {
            writer.write("CC;" + id + "\n");
            Random rand = new Random();
            for (int i = 0; i < randomSalesCount; i++) {
                int productId = rand.nextInt(100) + 1; // ID aleatorio de producto
                int cantidad = rand.nextInt(20) + 1;  // Cantidad aleatoria
                writer.write(productId + ";" + cantidad + ";\n");
            }
            System.out.println("Archivo " + fileName + " generado correctamente.");
        } catch (IOException e) {
            System.err.println("Error al crear el archivo de ventas: " + e.getMessage());
        }
    }

    // Método para generar un archivo de productos
    public static void createProductsFile(int productsCount) {
        String fileName = "productos.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Files/"+fileName))) {
            Random rand = new Random();
            for (int i = 0; i < productsCount; i++) {
                int productId = i + 1;
                String productName = "Producto_" + productId;
                double price = 10 + (100 - 10) * rand.nextDouble(); // Precio entre 10 y 100
                writer.write(productId + ";" + productName + ";" + String.format("%.2f", price) + "\n");
            }
            System.out.println("Archivo de productos generado correctamente.");
        } catch (IOException e) {
            System.err.println("Error al crear el archivo de productos: " + e.getMessage());
        }
    }

    // Método para generar un archivo con información de vendedores
    public static Map<String, Long> createSalesManInfoFile(int salesmanCount) {
        String fileName = "informacion_vendedores.txt";
        Map<String, Long> listSalesMan = new HashMap<>();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Files/"+fileName))) {
            Random rand = new Random();
            for (int i = 0; i < salesmanCount; i++) {
                String nombre = nombres[rand.nextInt(nombres.length)];
                String apellido = apellidos[rand.nextInt(apellidos.length)];
                String tipoDocumento = "CC"; // Tipo de documento fijo
                long numeroDocumento = rand.nextLong(100000000, 999999999); // Número aleatorio
                writer.write(tipoDocumento + ";" + numeroDocumento + ";" + nombre + ";" + apellido + "\n"); //agregar linea al archivo plano
                listSalesMan.put(nombre+" "+apellido,numeroDocumento); // agregar linea a la lista creada
            }
            System.out.println("Archivo de información de vendedores generado correctamente.");
        } catch (IOException e) {
            System.err.println("Error al crear el archivo de información de vendedores: " + e.getMessage());
        }
        return listSalesMan;
    }
    
    public static void clearFolderFiles() {
    	File carpeta = new File("Files/");
    	File[] archivos = carpeta.listFiles();
    	if (archivos != null && archivos.length > 0) {
    		for (File archivo : archivos) {
    			if (archivo.isFile()) {
    				boolean valDelete = archivo.delete();
    				if (!valDelete) {
    					System.out.println("Error al eliminar el archivo: " + archivo.getName());
    				}
    			}
    		}
    		System.out.println("El directorio ya esta vacio");
    	} else {
    		System.out.println("El directorio ya esta vacio");
    	}
    	
    }

    public static void main(String[] args) {
    	
    	clearFolderFiles();
    	    	
    	//Crear diccionario
    	Map<String, Long> listSalesMan = new HashMap<>();
    	
        // Prueba de los métodos para generar los archivos
        createProductsFile(50);
        listSalesMan = createSalesManInfoFile(5);
        
        for (Map.Entry<String, Long> item : listSalesMan.entrySet()) {
        	Long idSalesMan = item.getValue();
        	String nameSalesMan = item.getKey();
        	createSalesMenFile(10, nameSalesMan, idSalesMan);
        }
        
        
        
    }
}
