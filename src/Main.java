import modelos.Instruccion;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
//        System.out.println("Hello world!");
        ArrayList<Instruccion> instrucciones = new ArrayList<>();
        Configuracion configuracion = new Configuracion();
        ControladorSalida syso = ControladorSalida.getInstance(configuracion);
        
        syso.println("Iniciando Programa");
        if (args.length == 0) {
            syso.println("No hay parametros saliendo");
            System.exit(0);
        }
        // Mostrar ayuda
        if (args.length == 1 && args[0].equals("-h")) {
            Utils.mostrar_ayuda();
            System.exit(0);
        }
        syso.println("Procesando parametros");
        Parser parseador = new Parser(instrucciones,configuracion);
        parseador.leer_argumentos(configuracion,args);

        syso.println("Procesando configuracion");
        parseador.leer_archivo();

        syso.println("Ejecutando instrucciones");
        Ejecutor ejecutor = new Ejecutor(configuracion);
        ejecutor.procesar_instrucciones(instrucciones);

    }
}