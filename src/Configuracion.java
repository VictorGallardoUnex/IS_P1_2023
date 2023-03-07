/**
 * Almacena la configuracion actual
 */
public class Configuracion {
    /**
     * Indica si se muestra en la terminal la salida
     */
    boolean salida_pantalla = true;
    /**
     * Indica si se muestra en el fichero la salida
     */
    boolean salida_fichero = false;
    /**
     * Indica si se debe de reescribir el ficher salida por defecto
     */
    boolean reescribir_fichero_salida = true;

    /**
     * Nombre del fichero que se lee
     */
    String fichero_entrada;
    /**
     * Nombre del fichero donde se guarda la salida
     */
    String fichero_salida = "salida.txt";

    public boolean isReescribir_fichero_salida() {
        return reescribir_fichero_salida;
    }

    public void setReescribir_fichero_salida(boolean reescribir_fichero_salida) {
        this.reescribir_fichero_salida = reescribir_fichero_salida;
    }

    public boolean isSalida_fichero() {
        return salida_fichero;
    }

    public void setSalida_fichero(boolean salida_fichero) {
        this.salida_fichero = salida_fichero;
    }

    public boolean isSalida_pantalla() {
        return salida_pantalla;
    }

    public void setSalida_pantalla(boolean salida_pantalla) {
        this.salida_pantalla = salida_pantalla;
    }

    public void setFichero_entrada(String fichero_entrada) {
        this.fichero_entrada = fichero_entrada;
    }

    public String getFichero_entrada() {
        return fichero_entrada;
    }

    public void setFichero_salida(String fichero_salida) {
        this.fichero_salida = fichero_salida;
    }

    public String getFichero_salida() {
        return fichero_salida;
    }
}
