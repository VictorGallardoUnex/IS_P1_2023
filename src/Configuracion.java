public class Configuracion {
    boolean salida_pantalla = false;
    boolean salida_fichero = false;
    boolean reescribir_fichero_salida = false;

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

}
