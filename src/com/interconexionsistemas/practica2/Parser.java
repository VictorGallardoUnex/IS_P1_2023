package com.interconexionsistemas.practica2;


import com.interconexionsistemas.practica2.Modelos.Bandera;
import com.interconexionsistemas.practica2.Modelos.Comando;
import com.interconexionsistemas.practica2.Modelos.Instruccion;
import com.interconexionsistemas.practica2.Singletons.Configuracion;
import com.interconexionsistemas.practica2.Singletons.Controladores.ControladorSalida;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Clase de soporte intermedio. Convierte entrada de usuario en configuración útil.
 */
public class Parser {
    static final Configuracion conf = Configuracion.getInstance();
    static final ControladorSalida syso = ControladorSalida.getInstance();
    final ArrayList<Instruccion> instrucciones;

    /**
     * Clase que contiene los metodos para leer el fichero de entrada y sus posibles configuraciones
     */
    public Parser(ArrayList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
    }

    /**
     * Lee los argumentos pasados al programa desde fuera. Establece configuraciones iniciales
     * @param argumentos argumentos pasados al programa
     */
    public void leer_argumentos(String[] argumentos) {
        boolean fe_configurado = false;
//        if (argumentos.length >=1) {
//            syso.println("El programa no acepta argumentos");
//            syso.salirYGuardar(1);
//        } else {
//            conf.setFichero_entrada("config.txt");
//        }
//
        if (argumentos.length>=2) {
            for (int i = 0; i < argumentos.length; i++) {
                if (argumentos[i].startsWith("-")) {
                    System.out.println("Argumento recibido");
                    switch (argumentos[i]) {
                        case "-fe": {
                            conf.setFichero_entrada(argumentos[i + 1]);
                            fe_configurado = true;
                            break;
                        }
                        default: {
                            syso.println("Error de sintaxis. La bandera del argumento '" + argumentos[i] + "' no existe");
                            Utils.mostrar_ayuda();
                            syso.salirYGuardar(0);
                            break;
                        }
                    }
                }
            }
        }
        if (!fe_configurado) {
            syso.println("Error de sintaxis. Faltan el archivo de configuracion inicial");
            Utils.mostrar_ayuda();
            syso.salirYGuardar(0);
        }
    }

    /**
     * Lee el archivo de entrada linea a linea y las analiza y crea una instruccion o configuracion de cada una
     */
    public void leer_archivo() {
        BufferedReader lector;
        int contador = 1;
        try {
            lector = new BufferedReader(new FileReader(conf.getFichero_entrada()));
            String linea = lector.readLine();

            while (linea != null) {
                if (!linea.equals("") && !linea.startsWith("#")) {
                    // limpiamos la linea de espacios en blanco
                    String line_limpia = linea.replaceAll("\\s{2,}", " ");
                    procesar_linea(line_limpia);
                    // leemos siguiente linea
                }
                linea = lector.readLine();
                contador++;
            }
        } catch (FileNotFoundException e) {
            syso.println("Error: Archivo no encontrado: " + e.getMessage());
            syso.salirYGuardar(0);
        } catch (IOException e) {
            syso.println("Error al leer linea: " + contador);
            e.printStackTrace();
        }
    }

    /**
     * Recibe una lista de cadena de texto. Se encarga de llamar al procesador de lina dependiendo del tipo de bandera
     * @param linea Esta esta compuesta por: <bandera> <comando> <valor>
     */
    void procesar_linea(String linea) {
        String[] comando = linea.split(" ");
        if (comando.length <=1) {
            syso.println("Error al leer_y_enviar comando '"+linea+"'");
        }
        String[] comando_sin_bandera;
        comando_sin_bandera = Arrays.copyOfRange(comando,1,comando.length);

        switch (comando[0]) {
            case "@": {
                Instruccion nueva_instruccion = new Bandera(comando_sin_bandera[0],comando_sin_bandera[1]);
                instrucciones.add(nueva_instruccion);
                break;
            }
            case "&": {
                // Unimos el resto de la cadena en un solo string
                String comando_sin_bandera_valor = String.join(" ", Arrays.copyOfRange(comando_sin_bandera, 1, comando_sin_bandera.length));
                Instruccion nueva_instruccion = new Comando(comando_sin_bandera[0],comando_sin_bandera_valor);

                instrucciones.add(nueva_instruccion);
                break;
            }
        }
    }
}
