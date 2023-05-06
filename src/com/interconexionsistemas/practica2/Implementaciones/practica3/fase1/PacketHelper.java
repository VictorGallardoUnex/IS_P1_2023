package com.interconexionsistemas.practica2.Implementaciones.practica3.fase1;

import com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Trama.TramaDatos;
import com.interconexionsistemas.practica2.Modelos.Errores.ErrorJpcap;
import com.interconexionsistemas.practica2.Modelos.Errores.ErrorTarjetaNoExiste;
import com.interconexionsistemas.practica2.Singletons.Controladores.ControladorTarjeta;
import jpcap.JpcapSender;
import jpcap.packet.EthernetPacket;
import jpcap.packet.Packet;

import java.util.Arrays;

import static com.interconexionsistemas.practica2.Main.configuracion;
import static com.interconexionsistemas.practica2.Main.syso;
import static com.interconexionsistemas.practica2.Utils.getMacComoString;

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
     * @param texto El texto o datos que se va a enviar
     * @param contador_tramas El numero de trama que se va a enviar
     * @return
     */
    public static byte[] formatear_trama(String texto, int contador_tramas) {
        byte[] bytesDatos_temp = new TramaDatos(texto,contador_tramas).toBytes();
        // Nos aseguramos que el posttrama_is es menor que el final de pin
        int posicion_ultimo_caracter_pin = configuracion.getPospin() + configuracion.getPin().getBytes().length;
        if (posicion_ultimo_caracter_pin >= configuracion.getPosTramaIs()) {
            syso.println("[AVISO] El valor posttrama_is solapa con el pin. La trama IS sera colocada inmediatamente despues");
            configuracion.setPosTramaIs(posicion_ultimo_caracter_pin + 1);
        }

        // Crear nueva array de bytes con el tamaño de la trama IS + el offset de postramaIS
        byte[] bytesTramaISMovidos = new byte[bytesDatos_temp.length + (configuracion.getPosTramaIs())];
        System.arraycopy(bytesDatos_temp, 0, bytesTramaISMovidos, (configuracion.getPosTramaIs()), bytesDatos_temp.length);

        // Copiamos la array de bytes del pin en la array original donde está ya la trama_is con su offset establecido
        System.arraycopy(configuracion.getPin().getBytes(), 0, bytesTramaISMovidos, configuracion.getPospin(), configuracion.getPin().getBytes().length);

        return bytesTramaISMovidos;
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
     * Ignora la posicion del pin y solo se basa en la posicion de la trama IS
     * @param bytesTrama
     * @return
     */
    public static TramaDatos extraerTexto(byte[] bytesTrama) {

        if (configuracion.getPosTramaIs() <= configuracion.getPospin()) {
            configuracion.setPosTramaIs(configuracion.getPospin() + configuracion.getPin().getBytes().length + 1);
        }
        int longitudTramaIS = bytesTrama.length - configuracion.getPosTramaIs();

        byte[] bytesTramaIS = new byte[longitudTramaIS];
        System.arraycopy(bytesTrama, configuracion.getPosTramaIs(), bytesTramaIS, 0, longitudTramaIS);

        TramaDatos td = TramaDatos.fromBytes(bytesTramaIS);

        // Devolver el texto y el número de trama como una cadena de caracteres
        return td;
    }

}
