package com.interconexionsistemas.practica2.Implementaciones.practica3.fase2;

import com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Emisor;
import com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.PacketHelper;
import jpcap.packet.Packet;

import static com.interconexionsistemas.practica2.Main.syso;

public class EnviarPaquetes {

    public static void enviarTramaIs(byte[] bytesTramaIs){

//        byte[] bytes = new byte[5 + texto.getBytes().length];
//        bytes[0] = Caracteres.SYN.value(); // SYN
//        bytes[1] = Caracteres.value(); // control
//        bytes[2] = (byte) numero_trama;
//        bytes[3] = Caracteres.R.value(); // direccion
//        bytes[4] = (byte) texto.getBytes().length; // longitud 34
//        System.arraycopy(texto.getBytes(), 0, bytes, 5, texto.getBytes().length); // texto
            byte[] bytesDatos = PacketHelper.tramaIS_a_bytesDatosPaquete(bytesTramaIs);
            Packet paquete = PacketHelper.buildPacket(bytesDatos);
            Emisor.enviarPaquete(paquete);
            syso.println("[TRACE] La trama numero " + TramaHelper.getNumTrama(bytesTramaIs) + " ha sido enviada");
    }

}
