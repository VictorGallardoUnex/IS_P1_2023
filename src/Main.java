import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        String s = args[0];
        if (args.length == 0) {
            System.exit(1);
        }
        // Mostrar ayuda
        if (args.length == 1 && args[0].equals("-h")) {
            //todo mostrar ayuda
            System.exit(0);
        }
        ArrayList<Instruccion> instrucciones = new ArrayList<>();
        Configuracion configuracion = new Configuracion();

        Parser parseador = new Parser(instrucciones,configuracion);
        parseador.leer_argumentos(configuracion,args);
        parseador.leer_archivo();
        Ejecutor ejecutor = new Ejecutor(instrucciones,configuracion);
    }
}