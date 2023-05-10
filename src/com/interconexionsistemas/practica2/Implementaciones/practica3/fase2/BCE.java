package com.interconexionsistemas.practica2.Implementaciones.practica3.fase2;

public class BCE {
    public static byte calcularBCE(byte[] data) {
    byte bce = 0; // Inicializa el valor BCE

    for (byte b : data) { // Recorre cada byte en el array de datos
        bce ^= b; // Aplica la operación XOR al valor BCE y el byte actual
    }

    return bce; // Devuelve el valor final de BCE
}
}
