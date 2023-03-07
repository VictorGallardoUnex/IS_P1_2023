import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Clase singleton que se encarga de mostrar el texto o guardarlo en el archivo
 */
public class ControladorSalida {
    LinkedList<String> salida_texto = new LinkedList<>();
    Configuracion configuracion;

    /**
     * Metodo que se llama para escribir
     * @param texto
     */
    public void println(String texto) {
        // Si salida pantalla esta off no mostramos nada en pantalla ni mostramos nada en el archivo
        if (!configuracion.salida_pantalla) {
            return;
        }
        System.out.println(texto);
        salida_texto.add(texto);
    }

    /**
     * Metodo que se llama para escribir
     * @param texto
     */
    public void print(String texto) {
        if (!configuracion.salida_pantalla) {
            return;
        }
        System.out.print(texto);
        // Añade texto a el ultimo mensaje sin salto de linea
        salida_texto.set(salida_texto.size(), salida_texto.peekLast() + texto);
    }

    /**
     * Configura el archivo salida
     * @param fichero_salida
     */
    public void setArchivoSalida(String fichero_salida) {
        if (configuracion.salida_fichero) {
            guardarFichero();
        }
        configuracion.setFichero_salida(fichero_salida);
    }

    /**
     * Guarda el fichero. Escribe linea  alinea y sobre escribe si lo tiene que hacerr
     */
    public void guardarFichero() {
        // En este punto se asume que salidafichero está siempre en ON
        boolean sobrescribir = configuracion.isReescribir_fichero_salida(); // establecer en true para sobrescribir el archivo
        String nombre_fichero = configuracion.getFichero_salida();
        // Si sobreescribir está en off
        if (!salida_texto.isEmpty()) {
            System.out.println(salida_texto.toString());
            if (!sobrescribir) {
                // Comprobamos si existe y preguntamos un nuevo nombre si existe.
                nombre_fichero = Utils.comprobarSiExisteYReescribir(configuracion.getFichero_salida());
            }

            escribirFichero(nombre_fichero);
        }

    }

    /**
     * Metodo que escribe de verdad en el fichero.
     * @param nombre_fichero
     */
    public void escribirFichero(String nombre_fichero) {

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(nombre_fichero, false))) {
            escritor.write(""); // crear un archivo vacío si no existe
            println("Archivo escrito exitosamente.");
            for (String linea : salida_texto) {
                escritor.write(linea);
                escritor.newLine(); // agregar carácter de nueva línea después de cada línea
            }
            salida_texto.clear();
        } catch (IOException e) {
            println("Error al escribir el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Guarda o escribe el texto en el fichero y sale del programa
     * @param status
     */
    public void salirYGuardar(int status){
        guardarFichero();
        System.exit(status);
    }

    // metodos del singleton

    // Constructor privado para evitar instancias desde fuera de la clase
    private ControladorSalida () {
    }
    private static ControladorSalida instancia;


    public static ControladorSalida getInstance(Configuracion configuracion) {
        ControladorSalida instancia = getInstance();
        instancia.configuracion = configuracion;
        return instancia;
    }
    public static ControladorSalida getInstance() {
        if (instancia == null) {
            instancia = new ControladorSalida();
        }
        return instancia;
    }
}
