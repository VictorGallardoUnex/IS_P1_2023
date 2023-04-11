package com.interconexionsistemas.practica2;
import com.interconexionsistemas.practica2.Singletons.Configuracion;
import com.interconexionsistemas.practica2.Singletons.Controladores.ControladorSalida;

import java.io.File;
import java.util.Scanner;

/**
 * Clase con metodos de ayuda
 */
public class Utils {
    static final Configuracion conf = Configuracion.getInstance();
    static final ControladorSalida syso = ControladorSalida.getInstance();

    /**
     * Devuelve el nuevo nombre de despues de comprobar que el fichero no exista. Pregunta un nuevo nombre hasta que este no exista previamente
     */
    public static String comprobarSiExisteYReescribir(String fichero) {
        if (existe_archivo(fichero) && !conf.isReescribir_fichero_salida()) {
            return preguntarReescribirFicheroExiste();
        }
        return fichero;
    }

    /**
     * Pregunta el nombre de fichero hasta que no exista
     */
    public static String preguntarReescribirFicheroExiste() {
        String nuevoNombre;

        do {
            System.out.println("El archivo de salida ya existe. Introduce un nuevo nombre");
            Scanner scanner = new Scanner(System.in);
            nuevoNombre = scanner.nextLine().trim().toUpperCase();
        } while (existe_archivo(nuevoNombre));
        return nuevoNombre;
    }

    /**
     * Devuelve is existe un ficher
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
        syso.println("      PRACTICA 1");
        syso.println("      |- o & ficheroSalida <nombre>");
        syso.println("      |      En <nombre> vendrá una cadena para indicar cual será el fichero");
        syso.println("      |      de salida que se usará a partir de ese instante. En el caso de que la");
        syso.println("      |      bandera de salidafichero esté en ON se procederá a ponerla en");
        syso.println("      |      OFF; y se ejecutará todo el código asociado a la aparición de esta");
        syso.println("      |      bandera en OFF en el fichero de configuración");
        syso.println("      |- o & seleccionatarjeta [<numero>]");
        syso.println("      |      Elegirá en el sistema el numero de tarjeta asociado para trabajar");
        syso.println("      |      con ella en el resto de comandos si procede");
        syso.println("      |      Si no existe esa tarjeta en el sistema no se cambiará el numero de");
        syso.println("      |      tarjeta de trabajo, y además informará de que no es posible el");
        syso.println("      |      cambio");
        syso.println("      |- o & infotarjeta [<numero>]");
        syso.println("      |      Mostrará la información completa de la tarjeta seleccionada en el");
        syso.println("      |      <numero>. Si no se indica el número, se mostrará la de la tarjeta");
        syso.println("      |      elegida,.");
        syso.println("      |- o & infoIP [<numero>]");
        syso.println("      |      Mostrará la información sobre la dirección IP de la tarjeta asociada");
        syso.println("      |      a <numero>");
        syso.println("      |- o & infoEthernet [<numero>]");
        syso.println("             Mostrará la información sobre la dirección Ethernet de la tarjeta");
        syso.println("             asociada a <numero>\n");
        syso.println("      PRACTICA 2");
        syso.println("      |- o & recibir <longitud|tipo|todo>");
        syso.println("      |      Recibe un paquete cuyo campo tipo/longitud sea tipo, longitud o ambos");
        syso.println("      |      longitud: Mostrara las tramas en las que el valor campo tipo/longitud sea inferior a 1500");
        syso.println("      |      tipo: Mostrara las tramas en las que el valor campo tipo/longitud sea superior a 1500");
        syso.println("      |      todo: Mostrara todas las tramas");
        syso.println("      |- o & repetirenvio <numero>");
        syso.println("      |      El valor numérico del argumento indica la cantidad de veces que se enviará el mismo");
        syso.println("      |      paquete de datos.Este paquete incluirá el mismo texto y un número que irá de 1 a <numero>.");
        syso.println("      |      Este comportamiento se puede observar en el ejemplo del comando \"texto\".");
        syso.println("      |- o & texto <texto>");
        syso.println("      |      Donde <texto> es la cadena que se va a enviar en el campo de datos del paquete");
        syso.println("      |- o & pin <palabra>");
        syso.println("      |      Establece un pin que se añade al principio de los datos del paquete a enviar");
        syso.println("      |      Si no se establece un pin, la trama se enviará sin pin");
        syso.println("      |- o & recibirconpin");
        syso.println("             Recibe un paquete que contenga un pin que coincida con el establecido previamente.");
        syso.println("             En el caso de no haber pin establecido, muestra todas las tramas recibidas");
    }

    /**
     * Formatea los bytes de la mac en una string
     * @return String
     */
    public static String getMacComoString(byte[] mac) {
        return String.format("%02X:%02X:%02X:%02X:%02X:%02X", mac[0], mac[1], mac[2], mac[3], mac[4], mac[5]);
    }


}
