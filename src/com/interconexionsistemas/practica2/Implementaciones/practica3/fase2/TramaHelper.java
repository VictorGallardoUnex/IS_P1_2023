package com.interconexionsistemas.practica2.Implementaciones.practica3.fase2;

public class TramaHelper {
    public static String getTexto(byte[] data) {
        if (data.length>4) {
            int longitud = data[4];
            return new String(data, 5, longitud);
        }
        return "";
    }
    public static int getNumTrama(byte[] data) {
        if (data.length>4) {
            return data[4];
        }
        return 0;
    }
    public static byte[] setNumTrama(byte[] data, int num_trama) {
            data[2] = (byte) num_trama;
            return data;
    }

    public static Caracteres getTipoTrama(byte[] data) {
        return Caracteres.fromByte(data[1]);
    }

}
