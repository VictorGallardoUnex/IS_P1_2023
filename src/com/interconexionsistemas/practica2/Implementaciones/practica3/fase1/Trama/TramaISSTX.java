package com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Trama;

import com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Caracteres;

public class TramaISSTX extends TramaIS {

    public TramaISSTX(byte[] bytes) {
        super(bytes);
    }
    public TramaISSTX(String texto, int numero_trama) {
        this.caracter_control = Caracteres.STX;
        this.longitud = texto.length();
        this.texto = texto;
        this.numero_trama = numero_trama;
    }


}
