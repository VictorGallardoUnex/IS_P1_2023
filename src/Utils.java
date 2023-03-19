import java.io.File;
import java.util.Scanner;

/**
 * Clase con metodos de ayuda
 */
public class Utils {
    static ControladorSalida syso = ControladorSalida.getInstance();

    /**
     * Devuelve el nuevo nombre de despues de comprobar que el fichero no exista. Pregunta un nuevo nombre hasta que este no exista previamente
     * @param fichero
     * @return
     */
    public static String comprobarSiExisteYReescribir(String fichero) {
        if (existe_archivo(fichero)) {
            return preguntarReescribirFicheroExiste();
        }
        return fichero;
    }

    /**
     * Pregunta el nombre de fichero hasta que no exista
     * @return
     */
    public static String preguntarReescribirFicheroExiste() {
        String nuevoNombre;
        do {
            System.out.println("El archivo ya existe. Introduce un nuevo nombre");
            Scanner scanner = new Scanner(System.in);
            nuevoNombre = scanner.nextLine().trim().toUpperCase();
        } while (existe_archivo(nuevoNombre));
        return nuevoNombre;
    }

    /**
     * Devuelve is existe un ficher
     * @param nuevoNombre
     * @return
     */
    public static boolean existe_archivo(String nuevoNombre) {
        return new File(nuevoNombre).exists();
    }


    /**
     * Muestra la sintaxis
     */
    public static void mostrar_sintaxis() {
        syso.println("    Sintaxis");
        syso.println("        IsP1 -fe <fichero1> [-fs <fichero2>]| -h");
        syso.println("        -h Muestra este mensaje de ayuda");
        syso.println("        -fe El fichero que contiene la configuracion/comandos a ejecutar");
        syso.println("        -fs El fichero de salida donde guardar la salida. 'salida.txt' por defecto");
    }
    /**
     * Muestra la ayuda
     */
    public static void mostrar_ayuda(){
        syso.println("    Sintaxis");
        syso.println("        IsP1 -fe <fichero1> [-fs <fichero2>]| -h");
        syso.println("        -h Muestra este mensaje de ayuda");
        syso.println("        -fe El fichero que contiene la configuracion/comandos a ejecutar");
        syso.println("        -fs El fichero de salida donde guardar la salida. 'salida.txt' por defecto");
        syso.println("        ");
        syso.println("    Banderas:");
        syso.println("        o @ salidapantalla ON | OFF");
        syso.println("            Indica si se muestra por la terminal la salida del programa");
        syso.println("        o @ salidafichero ON | OFF");
        syso.println("            Indica si se muestra en el archivo de texto la salida del programa");
        syso.println("        o @ reescribirficherosalida");
        syso.println("            Indica si se sobreescribe el archivo de salida por defecto o no");
        syso.println("    Comandos");
        syso.println("        o & ficheroSalida <nombre>");
        syso.println("            En <nombre> vendrá una cadena para indicar cual será el fichero");
        syso.println("            de salida que se usará a partir de ese instante. En el caso de que la");
        syso.println("            bandera de salidafichero esté en ON se procederá a ponerla en");
        syso.println("            OFF; y se ejecutará todo el código asociado a la aparición de esta");
        syso.println("            bandera en OFF en el fichero de configuración");
        syso.println("        o & seleccionatarjeta [<numero>]");
        syso.println("            Elegirá en el sistema el numero de tarjeta asociado para trabajar");
        syso.println("            con ella en el resto de comandos si procede");
        syso.println("            Si no existe esa tarjeta en el sistema no se cambiará el numero de");
        syso.println("            tarjeta de trabajo, y además informará de que no es posible el");
        syso.println("            cambio");
        syso.println("        o & infotarjeta [<numero>]");
        syso.println("            Mostrará la información completa de la tarjeta seleccionada en el");
        syso.println("            <numero>. Si no se indica el número, se mostrará la de la tarjeta");
        syso.println("            elegida,.");
        syso.println("        o & infoIP [<numero>]");
        syso.println("            Mostrará la información sobre la dirección IP de la tarjeta asociada");
        syso.println("            a <numero>");
        syso.println("        o & infoEthernet [<numero>]");
        syso.println("            Mostrará la información sobre la dirección Ethernet de la tarjeta");
        syso.println("            asociada a <numero>");
    }


}
