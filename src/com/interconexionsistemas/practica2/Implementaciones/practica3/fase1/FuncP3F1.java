package com.interconexionsistemas.practica2.Implementaciones.practica3.fase1;

import com.interconexionsistemas.practica2.Modelos.Errores.ErrorTarjetaNoExiste;
import jpcap.JpcapSender;
import jpcap.packet.EthernetPacket;
import jpcap.packet.Packet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static com.interconexionsistemas.practica2.Main.*;
import static com.interconexionsistemas.practica2.Utils.getMacComoString;

public class FuncP3F1 {
    public static final byte[] MAC_BROADCAST = { (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff };
    static void enviar(String mensaje) {
        String filePath = configuracion.getFichero_fuente();
        int blockSize = 10; // reemplazar con el tamaño de bloque que deseas leer

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            char[] buffer = new char[blockSize];
            int charsRead;

            while ((charsRead = reader.read(buffer, 0, blockSize)) != -1) {

                enviarTexto(new String(buffer, 0, charsRead));
//                syso.println(new String(buffer, 0, charsRead));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void enviarTexto(String texto) {
        byte[] bytesDatos_temp = texto.getBytes();

        // append first 20 bytes with empty values in bytesDatos
        byte[] bytesDatos = new byte[(configuracion.getPospin()-1) + bytesDatos_temp.length];

        // Copy the contents of bytesDatos into paddedBytes starting from the 36th position
        System.arraycopy(bytesDatos_temp, 0, bytesDatos, (configuracion.getPospin()-1), bytesDatos_temp.length);

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
