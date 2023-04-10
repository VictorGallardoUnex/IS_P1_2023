package com.interconexionsistemas.practica2;

import com.interconexionsistemas.practica2.Modelos.*;
import com.interconexionsistemas.practica2.Modelos.Errores.ErrorJpcap;
import com.interconexionsistemas.practica2.Modelos.Errores.ErrorTarjetaNoExiste;
import com.interconexionsistemas.practica2.Singletons.Configuracion;
import com.interconexionsistemas.practica2.Singletons.Controladores.ControladorTarjeta;
import com.interconexionsistemas.practica2.Singletons.Controladores.ControladorSalida;

import java.util.ArrayList;

import static com.interconexionsistemas.practica2.Implementaciones.FuncionesPractica1.*;
import static com.interconexionsistemas.practica2.Implementaciones.Practica2.FuncionesPractica2.*;

/**
 * Clase que contiene los metodos que se ejecutan, es decir los comandos o instrucciones ya leidos son hechas su funcion
 */
public class Ejecutor {
    static Configuracion conf = Configuracion.getInstance();
    static ControladorSalida syso = ControladorSalida.getInstance();
    static ControladorTarjeta ct;

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
                        case "ficherosalida": {
                            syso.setArchivoSalida(instruccion.valor);
                            break;
                        }
                        case "seleccionatarjeta": {
                            ct.setTarjeta_seleccionada(Integer.parseInt(instruccion.valor));
                            break;
                        }
                        case "infotarjeta": {
                            if (instruccion.valor.equals("")) {
                                infoTarjeta(ct.getTarjeta_seleccionada());
                            } else {
                                infoTarjeta(Integer.parseInt(instruccion.valor));
                            }
                            break;
                        }
                        case "infoip": {
                            infoIP(Integer.parseInt(instruccion.valor));
                            break;
                        }
                        case "infoethernet": {
                            infoEthernet(Integer.parseInt(instruccion.valor));
                            break;
                        }

                        // Practica 2 IS 2023

                        case "recibirtramas": {
                            recibirTramas(instruccion.valor);
                            break;
                        }

                        // Enviar tramas por la red
                        case "repertirenvio": {
                            repetirEnvio(Integer.parseInt(instruccion.valor));
                            break;
                        }
                        case "texto": {
                            conf.setMensaje_a_enviar(instruccion.valor);
                            break;
                        }
                        // Modulo C - Verificar	que	 recibimos	 lo	 enviado	 en	 el	 módulo
                        // anterior
                        case "pin": {
                            conf.setPin(instruccion.valor);
                            break;
                            /*
                            & pin <palabra>
                            § En “palabra” irá un pin que se colocará a partir del primer byte del
                            campo datos de la trama ethernet y seguidamente se colocará el
                            texto del comando texto, y se enviará las veces que indique el
                            comando “repetirenvio” en número vendrá indicado la cantidad de
                            veces que se enviará el mismo paquete de datos, donde irá el
                            mismo texto, seguido de un numero que irá de 1 a <numero> tal
                            cual se puede ver en el ejemplo del comando “texto”. Sólo cuando
                            este comando esté en el fichero de configuración se modificará el
                            comportamiento del módulo B.
                             */
                        }
                        case "recibirconpin": {
                            recibirTramas("longitud");
                            break;
                            /*
                            & recibirconpin
                            § En este caso se activará la recepción y mostrará sólo aquellas
                            tramas que siendo de tipo “longitud” venga el “pin” correctamente.
                            Si estuviera este comando activo, pero no hubiera pin definido en
                            el fichero, serán válidas todas las tramas, dado que es como recibirTramas
                            sin pin
                             */
                        }

                        default: {
                            syso.println("No existe el comando");
                            break;
                        }
                    }
                } catch (ErrorTarjetaNoExiste excepcion) {
                    syso.println("Error al ejecutar el comando. La tarjeta no existe");
                } catch (Exception exception) {
                    syso.println("Error al ejecutar comando '"+instruccion.clave + "' con valor: '"+instruccion.valor+"'");
                    exception.printStackTrace();
                }
            }
        }
    }

    /**
     * Ejecuta el cambio de configuracion
     * */
    private static void procesar_configuracion(Bandera bandera) {
        switch (bandera.clave.toLowerCase()) {
            case "salidapantalla": {
                conf.setSalida_pantalla(bandera.valor.equals("ON"));
                break;
            }
            case "salidafichero": {
                conf.setSalida_fichero(bandera.valor.equals("ON"));
                break;
            }
            case "reescribirficherosalida": {
                conf.setReescribir_fichero_salida(bandera.valor.equals("ON"));
                break;
            }
        }
    }
}
