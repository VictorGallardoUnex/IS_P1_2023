package com.interconexionsistemas.practica2.Implementaciones.practica3.fase2;

public class TramaHelper {
    public static String getTexto(byte[] data) {
        if (data.length>5) {
            int longitud = data[4];
            return new String(data, 5, longitud);
        }
        return "";
    }
    public static int getNumTrama(byte[] data) {
            return data[2];
    }
    public static byte[] setNumTrama(byte[] data, int num_trama) {
            data[2] = (byte) num_trama;
            return data;
    }
    public static byte[] setTipoTrama(byte[] data, Caracteres tipo_trama) {
            data[1] = tipo_trama.value();
            return data;
    }
    public static Caracteres getTipoTrama(byte[] data) {
        return Caracteres.fromByte(data[1]);
    }
    public static byte getBCE(byte[] data){
        //Esperamos la información
        if (data.length>5) {
            int longitud = data[4] +5;
            return data[longitud];

        }
        return data[5];
    }

}
