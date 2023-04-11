package com.interconexionsistemas.practica2.Implementaciones.Practica2;

import com.interconexionsistemas.practica2.Modelos.Errores.ErrorTarjetaNoExiste;
import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.packet.EthernetPacket;
import jpcap.packet.Packet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.interconexionsistemas.practica2.Main.*;
import static com.interconexionsistemas.practica2.Implementaciones.Practica2.UtilsP2.*;
import static com.interconexionsistemas.practica2.Utils.getMacComoString;

public class FuncionesPractica2 {

    // Practica 2 IS 2023
    /**
     * Recibe tramas de la red y las muestra por pantalla
     * @param modo puede ser "todo", "longitud" o "tipo"
     */
    static public void recibirTramas(String modo) throws IOException {
        modo = modo.toLowerCase();
        if (!(modo.equals("todo") || modo.equals("longitud") || modo.equals("tipo"))) {
            syso.println("Parametro incorrecto para modo recibir tramas: " + modo);
            return;
        }

        JpcapCaptor captor;
        captor = controladorTarjeta.getReceptor();
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader entradaTeclado = new BufferedReader(reader);

        boolean fin = false;
        do {
            Packet paquete = captor.getPacket();
            // si no hay paquete, continuamos
            if(paquete==null) continue;
            // si no es el tipo que buscamos, continuamos
            boolean is_longitud_or_tipo = convertirBytesAShort(paquete.header[12], paquete.header[13]) < 1500;
            if(!(is_longitud_or_tipo && modo.equals("longitud") || !is_longitud_or_tipo && modo.equals("tipo") || modo.equals("todo"))) {
                continue;
            }

            if (is_longitud_or_tipo && configuracion.hasPin() && !configuracion.getPin().equals(extraerPin(paquete))) {
                continue;
            }
            syso.println("Es un paquete de tipo " + (is_longitud_or_tipo ? "longitud" : "tipo"));
            mostrarPaquete(paquete);
            syso.println("Pulse 'enter' para continuar.\nPresione 'f' y despues 'enter' para finalizar");
            if (entradaTeclado.read() == 102) {
                syso.println("Fin de la captura");
                fin = true;
            }
        } while (!fin);
    }

    static public void repetirEnvio(int numero) {
        if (numero < 1) {
            syso.println("El numero de veces a repetir el envio debe ser mayor que 0");
            return;
        }
        String texto_original = configuracion.getMensaje_a_enviar();
        String texto_enviado;
        if (configuracion.hasPin()) {
            texto_original = configuracion.getPin() + " " + configuracion.getMensaje_a_enviar();
        }
        for (int i = 0; i < numero; i++) {
            texto_enviado = texto_original + " " + (i + 1);
            enviarTexto(texto_enviado);
        }
        configuracion.setMensaje_a_enviar(null);
    }
    /**
     * Envia un texto a la direccion MAC de broadcast
     * @param texto texto a enviar
     */
    private static void enviarTexto(String texto) {
        byte[] bytesDatos = texto.getBytes();
        byte[] mac_origen;
        try {
             mac_origen = controladorTarjeta.getTarjeta().mac_address;
        } catch (ErrorTarjetaNoExiste e) {
            // No hacemos nada, es imposible. si no existen tarjetas, el programa sale antes con codigo 1
            return;
        }
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

        JpcapSender emisor;
        // Enviamos el paquete
        emisor = controladorTarjeta.getEmisor();
        emisor.sendPacket(paquete);
        syso.println("Paquete enviado correctamente a la direccion MAC " + getMacComoString(MAC_BROADCAST) + "\n Informacion del paquete: \n" + mostrarCampoDatos(paquete.data)+ "\nInformacion del paquete como string: \n"+ new String(paquete.data));
    }

}
