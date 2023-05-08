package com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Trama;

import com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Caracteres;

public class TramaISENQ extends TramaIS {
    public TramaISENQ(byte[] bytes) {
        super(bytes);
    }
    public TramaISENQ(int numero_trama) {
        super(numero_trama);
        caracter_control = Caracteres.ENQ;
    }


    @Override
    public byte[] toBytes() {
        byte[] bytes = new byte[4];
        bytes[0] = caracter_syn.value(); // SYN
        bytes[1] = caracter_control.value(); // control
        bytes[2] = (byte) numero_trama;
        bytes[3] = (byte) direccion; // direccion
//        bytes[4] = (byte) longitud; // longitud 34
        return bytes;
    }
}
