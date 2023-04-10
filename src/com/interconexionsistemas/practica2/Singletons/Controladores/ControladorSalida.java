package com.interconexionsistemas.practica2.Singletons.Controladores;
import com.interconexionsistemas.practica2.Main;
import com.interconexionsistemas.practica2.Utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Clase singleton que se encarga de mostrar el texto o guardarlo en el archivo
 */
public class ControladorSalida {
    final LinkedList<String> salida_texto = new LinkedList<>();



    /**
     * Metodo que se llama para escribir
     */
    public void println(String texto) {
        // Si salida pantalla esta off no mostramos nada en pantalla ni mostramos nada en el archivo
        if (Main.configuracion.isNotSalida_pantalla()) {
            return;
        }
        System.out.println(texto);
        salida_texto.add(texto);
    }

    /**
     * Configura el archivo salida
     */
    public void setArchivoSalida(String fichero_salida) {
        if (Main.configuracion.isSalida_fichero()) {
            guardarFichero();
        }
        Main.configuracion.setFichero_salida(fichero_salida);
    }

    /**
     * Guarda el fichero. Escribe linea  alinea y sobre escribe si lo tiene que hacerr
     */
    public void guardarFichero() {
        // En este punto se asume que salidafichero está siempre en ON
        boolean sobrescribir = Main.configuracion.isReescribir_fichero_salida(); // establecer en true para sobrescribir el archivo
        String nombre_fichero = Main.configuracion.getFichero_salida();
        // Si sobreescribir está en off
        if (!salida_texto.isEmpty()) {
//            System.out.println(salida_texto.toString());
            if (!sobrescribir) {
                // Comprobamos si existe y preguntamos un nuevo nombre si existe.
                nombre_fichero = Utils.comprobarSiExisteYReescribir(Main.configuracion.getFichero_salida());
            }

            escribirFichero(nombre_fichero);
        }

    }

    /**
     * Metodo que escribe de verdad en el fichero.
     */
    public void escribirFichero(String nombre_fichero) {

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(nombre_fichero, false))) {
            escritor.write(""); // crear un archivo vacío si no existe
            System.out.println("Archivo escrito exitosamente.");
            for (String linea : salida_texto) {
                escritor.write(linea);
                escritor.newLine(); // agregar carácter de nueva línea después de cada línea
            }
            salida_texto.clear();
        } catch (IOException e) {
            println("Error al escribir el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Guarda o escribe el texto en el fichero y sale del programa
     */
    public void salirYGuardar(int status){
        guardarFichero();
        System.exit(status);
    }



    // Metodos singleton
    private static ControladorSalida instance;

    protected ControladorSalida() {
        // Prevent instantiation from outside the class
    }

    public static ControladorSalida getInstance() {
        if (instance == null) {
            instance =  new ControladorSalida();
        }
        return instance;
    }
}
