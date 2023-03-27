package com.interconexionsistemas.practica2.Implementaciones;

import com.interconexionsistemas.practica2.Modelos.Errores.ErrorJpcap;
import com.interconexionsistemas.practica2.Modelos.Errores.ErrorTarjetaNoExiste;
import com.interconexionsistemas.practica2.Singletons.Controladores.ControladorSalida;
import com.interconexionsistemas.practica2.Singletons.Controladores.ControladorTarjeta;

import static com.interconexionsistemas.practica2.Utils.getMacAsString;

public class FuncionesPractica1 {
        /*
     FUNCIONES IMPLEMENTACION
    * */
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
     * Muestra la informacion de la tarjeta, si hay multiples tarjetas acepta un numero para seleccionarla
     * @param valor
     * @throws ErrorTarjetaNoExiste
     */
    public static void infoTarjeta(int valor) throws ErrorTarjetaNoExiste {
        jpcap.NetworkInterface tarjeta = ct.getTarjeta(valor);
        syso.println("Info de la tarjeta numero " + valor);
        syso.println("Nombre: " + tarjeta.name);
        syso.println("Nombre del enlace: " + tarjeta.datalink_name);
        syso.println("Mac: " + getMacAsString(tarjeta.mac_address));
    }

    /**
     * Muestra la informacion de la tarjeta, si hay multiples tarjetas acepta un numero para seleccionarla
     */
    public static void infoTarjeta() throws ErrorTarjetaNoExiste {
        infoTarjeta(0);
    }

    /**
     * Muestra la informacion de la direccion ip
     */
    public static void infoIP(int valor) throws ErrorTarjetaNoExiste {
        jpcap.NetworkInterface tarjeta = ct.getTarjeta(valor);
        syso.println("Informacion IP de la tarjeta seleccionada (" + valor + ")" + " " + tarjeta.name);
        for (int j = 0; j < tarjeta.addresses.length; j++) {
            syso.println("Informacion del adaptador " + j + ": ");
            syso.println("    Direccion:" + tarjeta.addresses[j].address);
            syso.println("    Mascara:" + tarjeta.addresses[j].subnet);
            syso.println("    Destino:" + tarjeta.addresses[j].destination);
            syso.println("    Broadcast:" + tarjeta.addresses[j].broadcast);
        }
    }

    /**
     * Muestra la mac
     * @param valor
     * @throws ErrorTarjetaNoExiste
     */
    public static void infoEthernet(int valor) throws ErrorTarjetaNoExiste {
        jpcap.NetworkInterface tarjeta = ct.getTarjeta(valor);
        ;
        syso.println("Direccion mac de la tarjeta (" + valor + ") " + getMacAsString(tarjeta.mac_address));
    }
}
