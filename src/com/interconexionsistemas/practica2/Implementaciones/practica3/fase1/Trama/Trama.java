package com.interconexionsistemas.practica2.Implementaciones.practica3.fase1.Trama;

public abstract class Trama {
    String texto;
    int numero_trama;
    Trama(){}




    public abstract byte[] toBytes();
    public abstract void fromBytes(byte[] bytes);
}
