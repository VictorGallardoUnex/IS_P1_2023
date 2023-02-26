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
        String fichero_entrada = "";
        String fichero_salida = "";
        if (args.length>=2) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].startsWith("-") && i+1 < args.length) {
                    System.out.println("Instruccion");
                    switch (args[i]) {
                        case "-fe": {
                            fichero_entrada = args[i+1];
                            break;
                        }
                        case "-fs": {
                            fichero_salida = args[i+1];
                        }
                    }
                }
            }
        }

        ArrayList<Instruccion> instrucciones = new ArrayList<>();
        Configuracion configuracion = new Configuracion();


        Parser parseador = new Parser(fichero_entrada,instrucciones,configuracion);
        parseador.leer_archivo();
        Ejecutor ejecutor = new Ejecutor(instrucciones,configuracion);
    }
}