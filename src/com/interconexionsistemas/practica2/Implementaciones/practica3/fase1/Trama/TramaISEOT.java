package com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Trama;

import com.interconexionsistemas.practica2.Implementaciones.practica3.fase2.Caracteres;

public class TramaISEOT extends TramaIS {
    public TramaISEOT(byte[] bytes) {
        super(bytes);
    }
    public TramaISEOT(int numero_trama) {
        super(numero_trama);
        caracter_control = Caracteres.EOT;
    }
        @Override
    public byte[] toBytes() {
        byte[] bytes = new byte[4];
        bytes[0] = caracter_syn.value(); // SYN
        bytes[1] = caracter_control.value(); // control
        bytes[2] = (byte) numero_trama;
        bytes[3] = (byte) direccion; // direccion
        return bytes;
    }

}
