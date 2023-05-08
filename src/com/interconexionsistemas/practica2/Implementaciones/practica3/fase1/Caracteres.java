package com.interconexionsistemas.practica2.Implementaciones.practica3.fase1;

public enum Caracteres {
    ENQ(25),
    ACK(21),
    NACK(22),
    SYN(23),
    STX(24),
    ETB(25),
    ETX(26),
    EOT(27),
    R(82);
    public byte value() {
        return (byte) caracter;
    }

    private final int caracter;
    Caracteres(int caracter) {
        this.caracter = caracter;
    }
    // assign ENQ character 25
     public static Caracteres fromByte(byte b) {
        for (Caracteres c : Caracteres.values()) {
            if (c.value() == b) {
                return c;
            }
        }
        throw new IllegalArgumentException("Invalid byte value for Caracteres enum: " + b);
    }




}
