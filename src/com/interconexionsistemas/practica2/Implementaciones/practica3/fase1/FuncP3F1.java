package com.interconexionsistemas.practica2.Implementaciones.practica3.fase1;

import com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Trama.TramaDatos;
import com.interconexionsistemas.practica2.Modelos.Errores.ErrorTarjetaNoExiste;
import jpcap.JpcapSender;
import jpcap.packet.EthernetPacket;
import jpcap.packet.Packet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import static com.interconexionsistemas.practica2.Main.*;
import static com.interconexionsistemas.practica2.Utils.getMacComoString;

public class FuncP3F1 {
    public static final byte[] MAC_BROADCAST = { (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff };
    static ArrayList<String> leer() {
        String filePath = configuracion.getFichero_fuente();
        int blockSize = 10; // reemplazar con el tamaño de bloque que deseas leer
        ArrayList<String> resultado = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            char[] buffer = new char[blockSize];
            int charsRead;

            while ((charsRead = reader.read(buffer, 0, blockSize)) != -1) {
                resultado.add(new String(buffer, 0, charsRead));
//                enviarTexto(new String(buffer, 0, charsRead));
//                syso.println(new String(buffer, 0, charsRead));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    private static void enviarTexto(String texto,int numero_trama) {
//
//        int contador_tramas= 0;
//
//
//
//        // Anadimos el offset de pospin y el pin
//        System.arraycopy(bytesDatos_temp, 0, bytesTramaISMovidos, (configuracion.getPosTramaIs()-1), bytesDatos_temp.length);
//
//        // append first 20 bytes with empty values in bytesDatos
//        byte[] bytesDatos = new byte[(configuracion.getPospin()-1) + bytesDatos_temp.length];
//
//        // Copy the contents of bytesDatos into paddedBytes starting from the 36th position
//        System.arraycopy(bytesDatos_temp, 0, bytesDatos, (configuracion.getPospin()-1), bytesDatos_temp.length);
//
//        byte[] mac_origen;
//        try {
//            mac_origen = controladorTarjeta.getTarjeta().mac_address;
//        } catch (ErrorTarjetaNoExiste e) {
//            // No hacemos nada, es imposible. si no existen tarjetas, el programa sale antes con codigo 1
//            return;
//        }
//        // Creamos el paquete
//        Packet paquete = new Packet();
//        // Creamos la cabecera Ethernet
//        EthernetPacket EthP = new EthernetPacket();
//        // Rellenamos los campos de la cabecera Ethernet
//        EthP.frametype = (short) bytesDatos.length;
//        // La direccion MAC de destino es la direccion MAC de broadcast
//        EthP.dst_mac = MAC_BROADCAST;
//        // La direccion MAC de origen es la direccion MAC de la tarjeta de red
//        EthP.src_mac = mac_origen;
//
//        paquete.datalink = EthP;
//        paquete.data = bytesDatos;
//
//        JpcapSender emisor;
//        // Enviamos el paquete
//        emisor = controladorTarjeta.getEmisor();
//        emisor.sendPacket(paquete);
//        syso.println("Paquete enviado correctamente a la direccion MAC " + getMacComoString(MAC_BROADCAST) + "\n Informacion del paquete: \n" + mostrarCampoDatos(paquete.data)+ "\nInformacion del paquete como string: \n"+ new String(paquete.data));
    }
//

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
        assert posicion_ultimo_caracter_pin <= configuracion.getPosTramaIs() : "El valor posttrama_is solapa con el pin. Introduce un postrama mayor";

        // Crear nueva array de bytes con el tamaño de la trama IS + el offset de postramaIS
        byte[] bytesTramaISMovidos = new byte[bytesDatos_temp.length + (configuracion.getPosTramaIs()-1)];
        System.arraycopy(bytesDatos_temp, 0, bytesTramaISMovidos, (configuracion.getPosTramaIs()-1), bytesDatos_temp.length);

        // crear nueva array con el tamaño del pin y el offset de pospin
        byte[] bytesPin = new byte[(configuracion.getPospin()-1) + configuracion.getPin().getBytes().length];
        // llenamos la nueva array con el pin
        System.arraycopy(configuracion.getPin().getBytes(), 0, bytesPin, 0, configuracion.getPin().getBytes().length);

        // Copiamos la array de bytes del pin en la array original donde está ya la trama_is con su offset establecido
        System.arraycopy(bytesPin, 0, bytesTramaISMovidos, 0, bytesPin.length);

        return bytesTramaISMovidos;
    }
}
