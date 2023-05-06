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
     * Nombre del fichero de configuracion
     */
    String fichero_entrada = "config.txt";
    /**
     * Nombre del fichero donde se guarda la salida
     */
    String fichero_salida = "salida.txt";


    String fichero_fuente = "quijote.txt";

    int pospin = 1;

    String pin = "";

    int posTramaIs = 1;
    boolean maestro = false;

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


    public void setPin(String valor) {
        this.pin = valor;
    }

    public String getPin() {
        return pin;
    }

    public boolean hasPin() {
        return pin != null && !pin.isEmpty();
    }

    public void setPospin(int pospin) {
        if (pospin<=20) {
            this.pospin = pospin;
        }
    }

    public int getPospin() {
        return pospin-1;
    }
    public void setFicheroFuente(String fichero_fuente) {
        this.fichero_fuente = fichero_fuente;
    }

    public String getFicheroFuente() {
        return this.fichero_fuente;
    }

    public void setPosTramaIs(int posTramaIs) {
        this.posTramaIs = posTramaIs;
    }

    public int getPosTramaIs() {
        return posTramaIs -1;
    }


    public void setMaestro(boolean maestro) {
        this.maestro = maestro;
    }

    public boolean isMaestro() {
        return maestro;
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

}
