package com.interconexionsistemas.practica2.Singletons;

/**
 * Clase que contiene la configuracion del programa
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

    public boolean isNotSalida_pantalla() {
        return !salida_pantalla;
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


    // Metodos singleton
    private static Configuracion instance;

    protected Configuracion() {
        // Prevenimos que se cree una instancia desde fuera de la clase
    }

    public static Configuracion getInstance() {
        if (instance == null) {
            instance = new Configuracion();
        }
        return instance;
    }

    // Practica 2
    /**
     * El mensaje que se va a enviar si se va a enviar algo. Se reinicia a null despues de ser enviado
     */
    String mensaje_a_enviar = null;

    public String getMensaje_a_enviar() {
        if (mensaje_a_enviar == null || mensaje_a_enviar.isEmpty()) {
            mensaje_a_enviar = "esto se hace en la practica 2 de interconexión de sistemas";

        }
        return mensaje_a_enviar;
    }

    public void setMensaje_a_enviar(String mensaje_a_enviar) {
        this.mensaje_a_enviar = mensaje_a_enviar;
    }
    String pin = "";
    public void setPin(String valor) {
        this.pin = valor;
    }

    public String getPin() {
        return pin;
    }

    public boolean hasPin() {
        return pin != null && !pin.isEmpty();
    }

    int pospin = 1;

    public void setPospin(int pospin) {
        if (pospin<=20) {
            this.pospin = pospin;
        }
    }

    public int getPospin() {
        return pospin;
    }
}
