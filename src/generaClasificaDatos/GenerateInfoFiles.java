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
        String fileName = "ventas_" + id + ".txt"; //inicializar nombre archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Files/"+fileName))) { //crear archivo y buffer para escribir en el mismo
            writer.write("CC;" + id + "\n"); //escribir primera linea del archivo
            Random rand = new Random(); // crear numero aleatorio
            for (int i = 0; i < randomSalesCount; i++) {
                int productId = rand.nextInt(100) + 1; // ID aleatorio de producto
                int cantidad = rand.nextInt(20) + 1;  // Cantidad aleatoria
                writer.write(productId + ";" + cantidad + ";\n"); //escribir una fila en archivo plano con la estructura esperada
            }
            System.out.println("Archivo " + fileName + " generado correctamente.");
        } catch (IOException e) {
            System.err.println("Error al crear el archivo de ventas: " + e.getMessage());
        }
    }

    // Método para generar un archivo de productos
    public static void createProductsFile(int productsCount) {
        String fileName = "productos.txt"; //inicializar nombre archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Files/"+fileName))) { //crear archivo y buffer para escribir en el mismo
            Random rand = new Random(); // crear numero aleatorio
            for (int i = 0; i < productsCount; i++) {
                int productId = i + 1; //Crear consecutivo de producto
                String productName = "Producto_" + productId; //asignar nombre generico producto
                double price = 10 + (100 - 10) * rand.nextDouble(); // Precio entre 10 y 100
                writer.write(productId + ";" + productName + ";" + String.format("%.2f", price) + "\n"); //escribir una fila en archivo plano con la estructura esperada
            }
            System.out.println("Archivo de productos generado correctamente.");
        } catch (IOException e) {
            System.err.println("Error al crear el archivo de productos: " + e.getMessage());
        }
    }

    // Método para generar un archivo con información de vendedores
    public static Map<String, Long> createSalesManInfoFile(int salesmanCount) { //Metodo que returna lista de vendedores
        String fileName = "informacion_vendedores.txt"; // definir archivo de vendedores
        Map<String, Long> listSalesMan = new HashMap<>(); // definir lista para vendedores creados
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Files/"+fileName))) { // crear archivo y buffer para escribir en el mismo
            Random rand = new Random(); // crear numero aleatorio
            for (int i = 0; i < salesmanCount; i++) {
                String nombre = nombres[rand.nextInt(nombres.length)]; //Asignar nombre aleatorio
                String apellido = apellidos[rand.nextInt(apellidos.length)]; //Asignar apellido aleatorio
                String tipoDocumento = "CC"; // Tipo de documento fijo
                long numeroDocumento = rand.nextLong(100000000, 999999999); // Número aleatorio
                writer.write(tipoDocumento + ";" + numeroDocumento + ";" + nombre + ";" + apellido + "\n"); //agregar linea al archivo plano
                listSalesMan.put(nombre+" "+apellido,numeroDocumento); // agregar linea a la lista creada para archivos de ventas
            }
            System.out.println("Archivo de información de vendedores generado correctamente.");
        } catch (IOException e) {
            System.err.println("Error al crear el archivo de información de vendedores: " + e.getMessage());
        }
        return listSalesMan; // Retornar lista de vendedores
    }
    
    //Metodo para limpiar carpeta de archivos planos
    public static void clearFolderFiles() {
    	File carpeta = new File("Files/"); //definir carpeta de exportacion
    	File[] archivos = carpeta.listFiles(); // listar archivos de carpeta
    	if (archivos != null && archivos.length > 0) { //validar que la variable archivos no este nula
    		for (File archivo : archivos) {
    			if (archivo.isFile()) { //validar que el item de la iteracion exista
    				boolean valDelete = archivo.delete(); //borrar archivo de carpeta
    				if (!valDelete) { //validacion de eliminacion exitosa
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
    	
    	//Eliminar todos los archivos previos
    	clearFolderFiles();
    	    	
    	//Crear diccionario para recibir lista vendedores
    	Map<String, Long> listSalesMan = new HashMap<>();
    	
        // Prueba de los métodos para generar los archivos
        createProductsFile(50);
        //Crear archivo de vendedores y almacenar lista de vendedores
        listSalesMan = createSalesManInfoFile(5);
        
        //Crear un archivo de ventas por cada vendedor
        for (Map.Entry<String, Long> item : listSalesMan.entrySet()) {
        	Long idSalesMan = item.getValue(); //asignar numero de documento
        	String nameSalesMan = item.getKey();//asignar nombre
        	//Crear archivo de ventas por vendedor
        	createSalesMenFile(10, nameSalesMan, idSalesMan);
        }
        
        
        
    }
}
