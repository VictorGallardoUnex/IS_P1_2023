package com.interconexionsistemas.practica2.Implementaciones.practica3.fase2;

import com.interconexionsistemas.practica2.Implementaciones.practica3.fase2.Caracteres;
import com.interconexionsistemas.practica2.Singletons.Controladores.ControladorTarjeta;
import jpcap.packet.EthernetPacket;
import jpcap.packet.Packet;

import java.util.Arrays;

import static com.interconexionsistemas.practica2.Main.configuracion;
import static com.interconexionsistemas.practica2.Main.syso;

public class PacketHelper {
    public static final byte[] MAC_BROADCAST = { (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff };

    /**
     * Constructor de Paquete
     * Carga en el campo datos el contenido byteDatos
     * @param bytesDatos el contenido a introducir en el paquete en el campo datos
     * @return
     */
    public static Packet buildPacket(byte[] bytesDatos) {

        byte[] mac_origen;
        mac_origen = ControladorTarjeta.getMacAdress();

        // Creamos el paquete
        Packet paquete = new Packet();
        // Creamos la cabecera Ethernet
        EthernetPacket EthP = new EthernetPacket();
        // Rellenamos los campos de la cabecera Ethernet
        EthP.frametype = (short) bytesDatos.length;
        // La direccion MAC de destino es la direccion MAC de broadcast
        EthP.dst_mac = MAC_BROADCAST;
        // La direccion MAC de origen es la direccion MAC de la tarjeta de red
        EthP.src_mac = mac_origen;

        paquete.datalink = EthP;
        paquete.data = bytesDatos;

        // Enviamos el paquete
        return paquete;
    }


    /**
     * Recibe un texto/datos y lo coloca en la posición requerida por la configuracion
     * Tambien establece el pin en la posicion deseada con el offset deseado
     * @return
     */
    public static byte[] tramaIS_a_bytesDatosPaquete(byte[] bytesTramaISsin_bce) {
        byte bce = BCE.calcularBCE(bytesTramaISsin_bce);

        byte[] bytesTramaIS = new byte[bytesTramaISsin_bce.length + 1];
        bytesTramaIS[bytesTramaIS.length] = bce;

        byte[] bytesDatos_temp = bytesTramaIS;
        // Nos aseguramos que el posttrama_is es menor que el final de pin
        int posicion_ultimo_caracter_pin = configuracion.getPospin() + configuracion.getPin().getBytes().length;
        if (posicion_ultimo_caracter_pin >= configuracion.getPosTramaIs()) {
            syso.println("[AVISO] El valor posttrama_is solapa con el pin. La trama IS sera colocada inmediatamente despues del ultimo caracter del pin. Establece posTramaIS para quitar el aviso");
            configuracion.setPosTramaIs(posicion_ultimo_caracter_pin + 1);
        }

        // Crear nueva array de bytes con el tamaño de la trama IS + el offset de postramaIS
        byte[] bytesDatosTramaIS = new byte[bytesDatos_temp.length + (configuracion.getPosTramaIs())];
        System.arraycopy(bytesDatos_temp, 0, bytesDatosTramaIS, (configuracion.getPosTramaIs()), bytesDatos_temp.length);

        // Copiamos la array de bytes del pin en la array original donde está ya la trama_is con su offset establecido
        System.arraycopy(configuracion.getPin().getBytes(), 0, bytesDatosTramaIS, configuracion.getPospin(), configuracion.getPin().getBytes().length);

        return bytesDatosTramaIS;
    }

    public static String extraerPin(byte[] bytesTrama) {
        byte[] pinArray;
        try {
            int pinOffset = configuracion.getPospin();
            byte[] pinBytes = configuracion.getPin().getBytes();
            int pinLength = pinBytes.length;
            // Extraer el pin de la trama
            pinArray = Arrays.copyOfRange(bytesTrama, pinOffset, pinOffset + pinLength);
        } catch (Exception e) {
            return "";
        }
        return new String(pinArray);

    }



    /**
     * Saca los bytes del campo datos del paquete ignorando el pin devolviendo la trama IS en bytes
     * @param bytesDatosPaquete
     * @return
     */
    public static byte[] extraerBytesTramaIS(byte[] bytesDatosPaquete) {

        if (configuracion.getPosTramaIs() <= configuracion.getPospin()) {
            configuracion.setPosTramaIs(configuracion.getPospin() + configuracion.getPin().getBytes().length + 1);
        }
        int longitudTramaIS = bytesDatosPaquete.length - configuracion.getPosTramaIs();

        byte[] bytesTramaIS = new byte[longitudTramaIS];
        System.arraycopy(bytesDatosPaquete, configuracion.getPosTramaIs(), bytesTramaIS, 0, longitudTramaIS);

        return bytesTramaIS;

    }
}
