import modelos.Bandera;
import modelos.Comando;
import modelos.Instruccion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Clase de soporte intermedio. Convierte entrada de usuario en configuración útil.
 */
public class Parser {

    ArrayList<Instruccion> instrucciones;
    Configuracion configuracion;
    ControladorSalida syso = ControladorSalida.getInstance();
    public Parser(ArrayList<Instruccion> instrucciones, Configuracion configuracion) {
        this.instrucciones = instrucciones;
        this.configuracion = configuracion;
    }

    public void leer_argumentos(Configuracion configuracion, String[] argumentos) {
        if (argumentos.length>=2) {
            for (int i = 0; i < argumentos.length; i++) {
                if (argumentos[i].startsWith("-")) {
                    if (i + 1 < argumentos.length) {
                        System.out.println("Argumento recibido");
                        switch (argumentos[i]) {
                            case "-fe": {
                                configuracion.setFichero_entrada(argumentos[i + 1]);
                                break;
                            }
                            case "-fs": {
                                configuracion.setFichero_salida(argumentos[i + 1]);
                                break;
                            }
                        }
                    } else {
                        syso.println("Error de sintaxis. Faltan argumentos");
                        Utils.mostrar_ayuda();
                    }

                }
            }
        }
    }

    public void leer_archivo() {
        BufferedReader lector;
        int contador = 1;
        try {
            lector = new BufferedReader(new FileReader(configuracion.getFichero_entrada()));
            String linea = lector.readLine();

            while (linea != null) {
                if (!linea.equals("") && !linea.startsWith("#")) {
                    String cleanedString = linea.replaceAll("\\s{2,}", " ");
                    procesar_linea(cleanedString);
                    // leemos siguiente linea
                }
                linea = lector.readLine();
                contador++;
            }

        } catch (IOException e) {
            System.out.println("Error al leer linea: " + contador);
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
            syso.println("Error al procesar comando '"+linea+"'");
        }
        String[] comando_sin_bandera;
        if (comando.length == 2) {
            comando[0] = comando[1];
            comando[1] = "";
            comando_sin_bandera = comando;
        } else {
            comando_sin_bandera = Arrays.copyOfRange(comando,1,comando.length); }


        switch (comando[0]) {
            case "@": {
                Instruccion nueva_instruccion = new Bandera(comando_sin_bandera[0],comando_sin_bandera[1]);
                instrucciones.add(nueva_instruccion);
                break;
            }
            case "&": {
                Instruccion nueva_instruccion = new Comando(comando_sin_bandera[0],comando_sin_bandera[1]);
                instrucciones.add(nueva_instruccion);
                break;
            }
        }
    }
}
