import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Lee y parsea el fichero de salida.
 */
public class Parser {
    private String fichero_configuracion;
    private ArrayList<Instruccion> instrucciones;
    private Configuracion configuracion;

    public Parser(String fichero_configuracion) {
        this.fichero_configuracion = fichero_configuracion;
        instrucciones = new ArrayList<>();
        configuracion = new Configuracion();
    }

    private void leer_archivo() {
        BufferedReader lector;

        try {
            lector = new BufferedReader(new FileReader(this.fichero_configuracion));
            String linea = lector.readLine();

            while (linea != null) {
                procesar_linea(linea);
                // leemos siguiente linea
                linea = lector.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void procesar_linea(String linea) {
        String[] comando = linea.split(" ");
        switch (comando[0]) {
            case "@": {
                procesar_configuracion(comando[1].split(" "));
                break;
            }
            case "#": {
                procesar_instruccion(comando[1].split(" "));
            }
        }
    }

    /**
     * Recibe una línea de configuración y establece el ajuste especificado en la configuracion de la clase
     * @param configuracion El array de string con la configuracion y su valor
     */
    private void procesar_configuracion(String[] configuracion) {
        switch (configuracion[0]) {
            case "salidapantalla": {
                    this.configuracion.salida_pantalla = configuracion[1].equals("ON");
                    break;
                }
            case "salidafichero": {
                this.configuracion.salida_fichero = configuracion[1].equals("ON");
                break;
            }
            case "reescribirficherosalida": {
                this.configuracion.reescribir_fichero_salida = configuracion[1].equals("ON");
                break;
            }
        }
    }
    private void procesar_instruccion(String[] instruccion){

    }
}
