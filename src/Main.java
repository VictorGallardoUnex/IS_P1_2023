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
            syso.println("");
            //todo mostrar ayuda
            System.exit(0);
        }

        Parser parseador = new Parser(instrucciones,configuracion);
        parseador.leer_argumentos(configuracion,args);
        parseador.leer_archivo();

        Ejecutor ejecutor = new Ejecutor(configuracion);
        ejecutor.procesar_instrucciones(instrucciones);

    }
}