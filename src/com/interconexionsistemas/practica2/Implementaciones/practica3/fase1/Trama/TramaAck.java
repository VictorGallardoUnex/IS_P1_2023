package com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Trama;

import com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Caracteres;

public class TramaAck extends Trama {
    TramaAck(int numero_trama) {
        this.numero_trama = numero_trama;
    }


    @Override
    public byte[] toBytes() {
        byte[] bytes = new byte[4];
        bytes[0] = Caracteres.SYN.value();
        bytes[1] = Caracteres.ACK.value();
        bytes[2] = (byte) numero_trama;
        bytes[3] = Caracteres.R.value();
        return bytes;
    }

    @Override
    public void fromBytes(byte[] bytes) {
        numero_trama = bytes[2];
    }
}
