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
     * Muestra la ayuda
     */
    public static void mostrar_ayuda(){
        syso.println("""
                Sintaxis
                    IsP1 -fe <fichero1> [-fs <fichero2>]| -h
                    -h Muestra este mensaje de ayuda
                    -fe El fichero que contiene la configuracion/comandos a ejecutar
                    -fs El fichero de salida donde guardar la salida. "salida.txt" por defecto
                
                Banderas:
                o @ salidapantalla ON | OFF
                    Indica si se muestra por la terminal la salida del programa
                o @ salidafichero ON | OFF
                    Indica si se muestra en el archivo de texto la salida del programa
                o @ reescribirficherosalida
                    Indica si se sobreescribe el archivo de salida por defecto o no
                Comandos
                o & ficheroSalida <nombre>
                    En <nombre> vendrá una cadena para indicar cual será el fichero
                    de salida que se usará a partir de ese instante. En el caso de que la
                    bandera de salidafichero esté en ON se procederá a ponerla en
                    OFF; y se ejecutará todo el código asociado a la aparición de esta
                    bandera en OFF en el fichero de configuración
                o & seleccionatarjeta [<numero>]
                    Elegirá en el sistema el numero de tarjeta asociado para trabajar
                    con ella en el resto de comandos si procede
                    Si no existe esa tarjeta en el sistema no se cambiará el numero de
                    tarjeta de trabajo, y además informará de que no es posible el
                    cambio
                o & infotarjeta [<numero>]
                    Mostrará la información completa de la tarjeta seleccionada en el
                    <numero>. Si no se indica el número, se mostrará la de la tarjeta
                    elegida,.
                o & infoIP [<numero>]
                    Mostrará la información sobre la dirección IP de la tarjeta asociada
                    a <numero>
                o & infoEthernet [<numero>]
                    Mostrará la información sobre la dirección Ethernet de la tarjeta
                    asociada a <numero>
                """
        );
    }


}
