package com.interconexionsistemas.practica2;

import com.interconexionsistemas.practica2.Modelos.*;
import com.interconexionsistemas.practica2.Modelos.Errores.ErrorJpcap;
import com.interconexionsistemas.practica2.Modelos.Errores.ErrorTarjetaNoExiste;
import com.interconexionsistemas.practica2.Singletons.Configuracion;
import com.interconexionsistemas.practica2.Singletons.Controladores.ControladorTarjeta;
import com.interconexionsistemas.practica2.Singletons.Controladores.ControladorSalida;

import java.util.ArrayList;

//import static com.interconexionsistemas.practica2.Implementaciones.FuncionesPractica1.*;
//import static com.interconexionsistemas.practica2.Implementaciones.Practica2.FuncionesPractica2.*;

/**
 * Clase que contiene los metodos que se ejecutan, es decir los comandos o instrucciones ya leidos son hechas su funcion
 */
public class Ejecutor {
    static final Configuracion conf = Configuracion.getInstance();
    static final ControladorSalida syso = ControladorSalida.getInstance();
    static final ControladorTarjeta ct;

    static {
        try {
            ct = ControladorTarjeta.getInstance();
        } catch (ErrorJpcap e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Recibe una lista de instrucciones y ejecuta el comanado de cada instruccion si este esta registrado
     *
     * @param instrucciones lista de instrucciones a ejecutar
     */
    public static void procesar_instrucciones(ArrayList<Instruccion> instrucciones) {
        for (Instruccion instruccion : instrucciones) {
            if (instruccion.getClass() == Bandera.class) {
                procesar_configuracion((Bandera) instruccion);
            } else {
                // Añadimos un try catch para nuestra propia excepcion
                System.out.println("Ejecutando comando: "+instruccion.clave);
                try {
                    // Comparamos el nombre de instruccion en minusculas para que no se sensible a mayus y minusculas
                    switch (instruccion.clave.toLowerCase()) {

                        case "seleccionatarjeta": {
                            ct.setTarjeta_seleccionada(Integer.parseInt(instruccion.valor));
                            break;
                        }
                        case "pin": {
                            conf.setPin(instruccion.valor);
                            break;
                        }

                        case "ficherofuente": {
                            conf.setFicheroFuente(instruccion.valor);
                            break;
                        }

                        case "postramais": {
                            conf.setPosTramaIs(Integer.parseInt(instruccion.valor));
                            break;
                        }
                        case "pospin": {
                            conf.setPospin(Integer.parseInt(instruccion.valor));
                            break;
                        }

                        default: {
                            syso.println("[ERROR] Ese comando no esta disponible");
                            break;
                        }
                    }
//                } catch (ErrorTarjetaNoExiste excepcion) {
//                    syso.println("Error al ejecutar el comando. La tarjeta no existe");
                } catch (Exception exception) {
                    syso.println("Error al ejecutar comando '"+instruccion.clave + "' con valor: '"+instruccion.valor+"'");
                    exception.printStackTrace();
                }
            }
        }
    }

    /**
     * Ejecuta el cambio de configuracion
     * @param bandera bandera a ejecutar
     * */
    private static void procesar_configuracion(Bandera bandera) {
        switch (bandera.clave.toLowerCase()) {
            case "maestro": {
                conf.setMaestro(bandera.valor.equals("ON"));
                break;
            }
            case "traza": {
                conf.setSalida_pantalla(bandera.valor.equals("ON"));
                break;
            }

            }
        }
}

