package com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Trama;

public abstract class Trama {
    String texto;
    int numero_trama;
    Trama(){}

    public int getNumero_trama() {
        return numero_trama;
    }

    public void setNumero_trama(int numero_trama) {
        this.numero_trama = numero_trama;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public abstract byte[] toBytes();
    public Trama makeFromBytes(byte[] bytes) {
        return null;
    }
}
