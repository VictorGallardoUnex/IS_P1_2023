package com.interconexionsistemas.practica2.Modelos;

import com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Caracteres;
import com.interconexionsistemas.practica2.Modelos.Errores.ErrorTarjetaNoExiste;
import jpcap.JpcapSender;
import jpcap.packet.EthernetPacket;
import jpcap.packet.Packet;

import static com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Caracteres.*;
import static com.interconexionsistemas.practica2.Main.*;
import static com.interconexionsistemas.practica2.Utils.getMacComoString;
public class TramaIS {

    int numero_trama;

    static byte[] getTramaEstablecimiento(){
        return new byte[]{
                SYN.value(), // SYN
                ENQ.value(), // Caracter de control
                0x00, // Numero de Trama
                R.value(), // R
                (byte) -1 // numero de trama a contestar
        };
    }
    static byte[] getTramaAck(){
        return new byte[]{
                SYN.value(), // SYN
                ACK.value(), // Caracter de control
                0x00, // Numero de Trama
                R.value(), // R
                0 // numero de trama a contestar
        };
    }
    static boolean isTramaAck(byte[] trama){
        return trama[1] == ACK.value();
    }
    static boolean isTramaEstablecimiento(byte[] trama){
        return trama[1] == ENQ.value();
    }

    static byte[] toTrama(byte[] datos){
        byte[] trama = new byte[datos.length + 5];
        trama[0] = STX.value();
        trama[1] = (byte) datos.length;
        System.arraycopy(datos, 0, trama, 2, datos.length); // Copiamos los datos
        trama[trama.length - 3] = ETX.value(); // ETX
        trama[trama.length - 2] = 0x00; // CRC
        trama[trama.length - 1] = EOT.value(); // EOT
        return trama;
    }
}
