package com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Trama;

import com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Caracteres;

public class TramaIS {
    Caracteres caracter_syn = Caracteres.SYN;
    Caracteres caracter_control;
    int numero_trama;
    int longitud;
    int direccion = (int) Caracteres.R.value();
    String texto;
    public TramaIS(){}
    public TramaIS(byte[] bytes){
        //caracter_syn = Caracteres.fromByte(bytes[0]);
        caracter_control = Caracteres.fromByte(bytes[1]);
        numero_trama = bytes[2];
        direccion = bytes[3];
        if (bytes.length>4) {
            longitud = bytes[4];
            texto = new String(bytes, 5, longitud);
        }
    }
    public TramaIS(int numero_trama) {
        this.numero_trama = numero_trama;
    }

    public void setCaracter_control(Caracteres caracter_control) {
        this.caracter_control = caracter_control;
    }

    public void setNumero_trama(int numero_trama) {
        this.numero_trama = numero_trama;
    }

    public int getNumero_trama() {
        return numero_trama;
    }

    public byte[] toBytes() {
        byte[] bytes = new byte[5 + texto.getBytes().length];
        bytes[0] = Caracteres.SYN.value(); // SYN
        bytes[1] = caracter_control.value(); // control
        bytes[2] = (byte) numero_trama;
        bytes[3] = Caracteres.R.value(); // direccion
        bytes[4] = (byte) texto.getBytes().length; // longitud 34
        System.arraycopy(texto.getBytes(), 0, bytes, 5, texto.getBytes().length); // texto
        return bytes;
    }

    public Caracteres getCaracter_control() {
        return caracter_control;
    }

    public String getTexto() {
        return texto;
    }
}
