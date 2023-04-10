package com.interconexionsistemas.practica2;
import com.interconexionsistemas.practica2.Modelos.Errores.ErrorJpcap;
import com.interconexionsistemas.practica2.Modelos.Instruccion;
import com.interconexionsistemas.practica2.Singletons.Configuracion;
import com.interconexionsistemas.practica2.Singletons.Controladores.ControladorTarjeta;
import com.interconexionsistemas.practica2.Singletons.Controladores.ControladorSalida;

import java.util.ArrayList;

public class Main {
    public static final Configuracion configuracion = Configuracion.getInstance();
    public static final ControladorSalida syso = ControladorSalida.getInstance();
    public static ControladorTarjeta controladorTarjeta;

    public static void main(String[] args) {
        ArrayList<Instruccion> instrucciones = new ArrayList<>();
        syso.println("Iniciando Programa");

        try {
            controladorTarjeta = ControladorTarjeta.getInstance();
        } catch (ErrorJpcap e) {
            syso.println("Error al iniciar packet driver. Asegurate que esta correctamente instalado. Saliendo...");
            syso.salirYGuardar(1);
        }

        if (args.length == 0) {
            syso.println("No se han aportado parametros. Para usar el programa use la siguiente sintaxis:\nIsP1 -fe <fichero1> [-fs <fichero2>]| -h");
            syso.salirYGuardar(0);
        }
        // Mostrar ayuda
        if (args.length == 1 && args[0].equals("-h")) {
            Utils.mostrar_sintaxis();
            syso.salirYGuardar(0);
        }

        syso.println("Procesando parametros");
        Parser parseador = new Parser(instrucciones);
        parseador.leer_argumentos(args);

        syso.println("Procesando configuracion");
        parseador.leer_archivo();


        syso.println("Ejecutando instrucciones");
        Ejecutor.procesar_instrucciones(instrucciones);

        syso.salirYGuardar(0);

    }
}