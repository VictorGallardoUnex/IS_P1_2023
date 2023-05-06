package com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Trama;

import com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Caracteres;

public class TramaDatos extends Trama{
    public TramaDatos(String texto, int numero_trama) {
        this.numero_trama = numero_trama;
        this.texto = texto;
    }

    @Override
    public byte[] toBytes() {
        // todo bce
        byte[] bytes = new byte[5 + texto.getBytes().length];
        bytes[0] = Caracteres.SYN.value(); // SYN
        bytes[1] = Caracteres.STX.value(); // control
        bytes[2] = (byte) numero_trama;
        bytes[3] = Caracteres.R.value(); // direccion
        bytes[4] = (byte) texto.getBytes().length; // longitud 34
        System.arraycopy(texto.getBytes(), 0, bytes, 5, texto.getBytes().length); // datos
        return bytes;
    }

    public static TramaDatos fromBytes(byte[] bytes) {

        String texto = new String(bytes, 3, bytes[4]);
        int numero_trama = bytes[2];
        return new TramaDatos(texto, numero_trama);
    }
}
