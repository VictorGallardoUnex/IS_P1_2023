package com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Trama;

import com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Caracteres;
import com.interconexionsistemas.practica2.Singletons.Configuracion;


public class TramaAck extends Trama {
    public TramaAck(int numero_trama) {

        this.numero_trama = numero_trama;
    }
    public TramaAck() {
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

    public static TramaAck fromBytes(byte[] bytes) {
        Configuracion config = Configuracion.getInstance();
        int offset = config.getPosTramaIs()-1;


        TramaAck instance = new TramaAck(bytes[2+offset]);
        instance.numero_trama = bytes[2+offset];
        return instance;
    }
}
