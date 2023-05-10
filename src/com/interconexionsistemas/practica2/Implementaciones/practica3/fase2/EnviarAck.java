package com.interconexionsistemas.practica2.Implementaciones.practica3.fase2;

import jpcap.packet.Packet;

public class EnviarAck {

    public static void enviarAck(int num_trama) {

        byte[] tramaIS = new byte[5];
        tramaIS[0] = Caracteres.SYN.value(); // SYN
        tramaIS[1] = Caracteres.ACK.value();
        tramaIS[2] = (byte) num_trama;
        tramaIS[3] = Caracteres.R.value(); // direccion

        Packet paquete = PacketHelper.buildPacket(tramaIS);
        EnviarPaquetes.enviarPaquete(paquete);

    }
}
