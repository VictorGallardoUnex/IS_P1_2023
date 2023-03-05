import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
public class ControladorSalida {
    LinkedList<String> salida_texto = new LinkedList<>();
    Configuracion configuracion;

    public void println(String texto) {
        // Si salida pantalla esta off no mostramos nada en pantalla ni mostramos nada en el archivo
        if (!configuracion.salida_pantalla) {
            return;
        }
        System.out.println(texto);
        salida_texto.add(texto);
    }
    public void print(String texto) {
        if (!configuracion.salida_pantalla) {
            return;
        }
        System.out.print(texto);
        // Añade texto a el ultimo mensaje sin salto de linea
        salida_texto.set(salida_texto.size(), salida_texto.peekLast() + texto);
    }

    public void setArchivoSalida(String fichero_salida) {
        if (configuracion.salida_fichero) {
            guardarFichero();
        }
        configuracion.setFichero_salida(fichero_salida);
    }

    public void guardarFichero() {
        // En este punto se asume que salidafichero está siempre en ON
        System.out.println("GUardando fichero");
        System.out.println(salida_texto.toString());
        boolean sobrescribir = configuracion.isReescribir_fichero_salida(); // establecer en true para sobrescribir el archivo
        String nombre_fichero = configuracion.getFichero_salida();
        // Si sobreescribir está en off
        if (!sobrescribir) {
            // Comprobamos si existe y preguntamos un nuevo nombre si existe.
            nombre_fichero = Utils.comprobarSiExisteYReescribir(configuracion.getFichero_salida());
        }
        if (!salida_texto.isEmpty()) {
            escribirFichero(nombre_fichero);
        }

    }

    public void escribirFichero(String nombre_fichero) {

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(nombre_fichero, false))) {
            escritor.write(""); // crear un archivo vacío si no existe
            for (String linea : salida_texto) {
                escritor.write(linea);
                escritor.newLine(); // agregar carácter de nueva línea después de cada línea
            }
            System.out.println("Archivo escrito exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo: " + e.getMessage());
            e.printStackTrace();
        }
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
